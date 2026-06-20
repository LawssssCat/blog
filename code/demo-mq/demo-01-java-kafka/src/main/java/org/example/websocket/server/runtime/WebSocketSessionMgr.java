package org.example.websocket.server.runtime;

import jakarta.annotation.PostConstruct;
import jakarta.websocket.CloseReason;
import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.example.websocket.server.model.dto.ClientDTO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

@Component
@Slf4j
public class WebSocketSessionMgr {
    private static final Map<String, ClientDTO> CLIENTS = new ConcurrentHashMap<>();

    private static WebSocketSessionMgr instance;

    // 心跳配置常量
    private static final long PING_INTERVAL_SEC = 10;   // 每 10 秒发送一次 Ping
    private static final long MAX_IDLE_TIME_MS = 25000;  // 最大允许空闲时间（25秒），超过此时间未收到 Pong 则视为掉线
    private final ScheduledExecutorService heartbeatScheduler = Executors.newSingleThreadScheduledExecutor();

    private WebSocketSessionMgr() {
        // 服务端启动时，自动开启后台心跳与超时检测死循环
        startHeartbeatScheduler();
    }

    @PostConstruct
    public void postConstruct() {
        if (instance != null) {
            log.warn("duplicate instance");
        }
        instance = this;
    }

    public static WebSocketSessionMgr getInstance() {
        return instance;
    }

    public void travelClients(BiConsumer<String, ClientDTO> func) {
        for (Map.Entry<String, ClientDTO> entry : CLIENTS.entrySet()) {
            func.accept(entry.getKey(), entry.getValue());
        }
    }

    public void sendMsg(String clientId, String msg) throws IOException {
        ClientDTO clientDTO = CLIENTS.get(clientId);
        if (clientDTO == null) {
            throw new IllegalArgumentException("can't find client. " + clientId);
        }
        Session session = clientDTO.getSession();
        if (session == null || !session.isOpen()) {
            throw new IllegalArgumentException("session is closed. " + clientId);
        }
        synchronized (session) { // fix：并发写会出现“The remote endpoint was in state [TEXT_FULL_WRITING] which is an invalid state for called method”异常
            // RemoteEndpoint.Async remote = session.getAsyncRemote();
            RemoteEndpoint.Basic remote = session.getBasicRemote();
            remote.sendText(msg);
        }
    }

    public void add(String clientId, Session session) {
        CLIENTS.compute(clientId, (key, value) -> {
            if (value != null) {
                try {
                    closeSession(value.getSession(), null);
                } catch (IOException e) {
                    log.error("fail close old session", e);
                }
            }
            ClientDTO dto = new ClientDTO();
            dto.setClientId(clientId);
            dto.setSession(session);
            dto.setLastActiveTimestamp(Instant.now().toEpochMilli());
            return dto;
        });
    }

    public void remove(String clientId) {
        remove(clientId, null);
    }

    public void remove(String clientId, CloseReason closeReason) {
        CLIENTS.compute(clientId, (key, value) -> {
            if (value != null) {
                try {
                    closeSession(value.getSession(), closeReason);
                } catch (IOException e) {
                    log.error("fail close session", e);
                }
            }
            return null;
        });
    }

    private void closeSession(Session session, CloseReason closeReason) throws IOException {
        if (session != null && session.isOpen()) {
            log.info("close session: {} {}", session.getId(), closeReason);
            if (closeReason == null) {
                session.close();
            } else {
                session.close(closeReason);
            }
        }
    }

    /**
     * 🎯 当 Endpoint 收到客户端的 Pong 帧时，调用此方法刷新活跃时间
     */
    public void refreshActiveTime(String topic) {
        CLIENTS.compute(topic, (key, value) -> {
            if (value != null) {
                value.setLastActiveTimestamp(Instant.now().toEpochMilli());
            }
            return value;
        });
    }

    /**
     * 心跳调度器核心逻辑
     */
    private void startHeartbeatScheduler() {
        heartbeatScheduler.scheduleAtFixedRate(() -> {
            long now = Instant.now().toEpochMilli();

            for (Map.Entry<String, ClientDTO> entry : CLIENTS.entrySet()) {
                String topic = entry.getKey();
                ClientDTO clientDTO = entry.getValue();
                String clientId = clientDTO.getClientId();
                Session session = clientDTO.getSession();

                if (!session.isOpen()) {
                    continue;
                }

                Long lastActive = clientDTO.getLastActiveTimestamp();

                // 1. 🚨 僵尸连接剔除逻辑
                if (lastActive != null && (now - lastActive > MAX_IDLE_TIME_MS)) {
                    log.warn("⚠️ [心跳检测] 客户端超时未回应 Pong，正在强制关闭连接：id={}，topic={}，已空闲 {} 毫秒",
                            clientId, topic, (now - lastActive));
                    try {
                        // GOING_AWAY (1001) 表示端点正在离开（如服务器关闭或断网超时）
                        closeSession(session, new CloseReason(CloseReason.CloseCodes.GOING_AWAY, "Heartbeat Timeout"));
                    } catch (IOException e) {
                        log.error("强制关闭超时会话异常: {}", e.getMessage());
                    }
                    continue;
                }

                // 2. 💓 主动发送标准 Ping 帧
                try {
                    log.info("💓 [服务端] 正在发送标准 Ping 心跳帧 -> id={}，topic={}", clientId, topic);
                    // 发送符合 RFC 6455 规范的底层 Ping 控制帧（内容为空字节数组）
                    session.getBasicRemote().sendPing(ByteBuffer.wrap(new byte[0]));
                } catch (IOException e) {
                    log.error("向会话 id={} 发送 Ping 失败: {}", clientId, e.getMessage());
                }
            }
        }, PING_INTERVAL_SEC, PING_INTERVAL_SEC, TimeUnit.SECONDS);
    }
}
