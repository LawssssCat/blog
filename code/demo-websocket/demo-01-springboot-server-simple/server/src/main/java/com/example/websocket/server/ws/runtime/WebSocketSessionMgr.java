package com.example.websocket.server.ws.runtime;

import jakarta.websocket.CloseReason;
import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

@Component
@Slf4j
public class WebSocketSessionMgr {
    private static final Map<String, ClientDTO> CLIENTS = new ConcurrentHashMap<>();

    private static final WebSocketSessionMgr INSTANCE = new WebSocketSessionMgr();

    public static WebSocketSessionMgr getInstance() {
        return INSTANCE;
    }

    @Data
    public class ClientDTO {
        private String clientId;

        private Session session;
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
                    closeSession(value, null);
                } catch (IOException e) {
                    log.error("fail close old session", e);
                }
            }
            ClientDTO dto = new ClientDTO();
            dto.setClientId(clientId);
            dto.setSession(session);
            return dto;
        });
    }

    public void remove(String clientId) {
        remove(clientId, null);
    }

    public void remove(String clientId, CloseReason closeReason) {
        CLIENTS.compute(clientId, (key, value) -> {
            try {
                closeSession(value, closeReason);
            } catch (IOException e) {
                log.error("fail close session", e);
            }
            return null;
        });
    }

    private void closeSession(ClientDTO clientDTO, CloseReason closeReason) throws IOException {
        Session session = clientDTO.getSession();
        if (session != null && session.isOpen()) {
            log.info("close session: {} {}", clientDTO.getClientId(), closeReason);
            if (closeReason == null) {
                session.close();
            } else {
                session.close(closeReason);
            }
        }
    }
}
