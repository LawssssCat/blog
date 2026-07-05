package com.example.websocket.server;

import com.example.websocket.server.utils.SpringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletContext;
import jakarta.websocket.server.ServerContainer;
import jakarta.websocket.server.ServerEndpointConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

@SpringBootApplication
@Slf4j
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Component
    @Slf4j
    public static class SpringContextPrinter implements ApplicationListener<ApplicationReadyEvent> {
        @Resource
        private RequestMappingHandlerMapping handlerMapping;

        @Resource
        private WebProperties webProperties;

        @Resource
        private ServletContext servletContext;

        @SneakyThrows
        @Override
        public void onApplicationEvent(ApplicationReadyEvent event) {
            log.info("┌────────────────────────────────────────────────────────────────────────────────────────┐");
            log.info("│                    WEB 服务器可访问接口、公共页面与静态资源路由总览                           │");
            log.info("├────────────────────────────────────────────────────────────────────────────────────────┤");
            // 扫描并打印所有 @Controller / @RestController 动态接口
            printSpringEndpoints();
            log.info("├────────────────────────────────────────────────────────────────────────────────────────┤");
            // 扫描并打印所有可访问的公共静态文件（HTML、JSP等）
            printStaticLocations();
            log.info("└────────────────────────────────────────────────────────────────────────────────────────┘");

            log.info("┌──────────────────────────────────────────────────────────────┐");
            log.info("│       Tomcat 容器内部最终生效的 WebSocket 路由总览             │");
            log.info("├──────────────────────────────────────────────────────────────┤");
            // 扫描并打印所有可打印的端口
            pringWebsocketEndpoint();
            log.info("└──────────────────────────────────────────────────────────────┘");
        }

        /**
         * 提取并打印所有的 Spring Controller 接口
         */
        private void printSpringEndpoints() {
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = this.handlerMapping.getHandlerMethods();
            handlerMethods.forEach((mappingInfo, handlerMethod) -> {
                // 提取 HTTP 请求方法（GET, POST 等）
                Set<RequestMethod> methods = mappingInfo.getMethodsCondition().getMethods();
                String httpMethods = methods.isEmpty() ? "ALL" : methods.toString();

                // 提取 URL 直接路径（适配 Spring Boot 3.x 核心机制）
                Set<String> patterns = mappingInfo.getDirectPaths();
                if (patterns.isEmpty() && mappingInfo.getPatternsCondition() != null) {
                    patterns = mappingInfo.getPatternsCondition().getPatterns();
                }

                String className = handlerMethod.getBeanType().getSimpleName();
                String methodName = handlerMethod.getMethod().getName();

                for (String url : patterns) {
                    log.info("│ => [接口路由]  方法: {} | 路径: {} | 目标: {}#{}()", httpMethods, url, className, methodName);
                }
            });
        }

        /**
         * 递归扫描并打印公共静态页面和文件
         */
        private void printStaticLocations() throws IOException {
            // 获取 Spring Boot 默认配置的静态资源目录列表（通常包含 classpath:/static/ 等）
            String[] staticLocations = webProperties.getResources().getStaticLocations();
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            for (String location : staticLocations) {
                org.springframework.core.io.Resource[] subResources;
                org.springframework.core.io.Resource resource;
                try {
                    subResources = resolver.getResources(location + "**/*.*");
                    resource = resolver.getResource(location);
                } catch (IOException e) {
                    log.info("| => [公共页面]  路径: {} | 不存在", location);
                    continue;
                }
                for (org.springframework.core.io.Resource subResource : subResources) {
                    // 递归搜索该目录下所有带有后缀的文件
                    log.info("│ => [公共页面]  路径: {} | {}", location, subResource.getURI().toString().substring(resource.getURI().toString().length()));
                }
            }
        }

        private void pringWebsocketEndpoint() throws NoSuchFieldException, IllegalAccessException {
            log.info("| servletContext = {}", servletContext);
            ServerContainer serverContainer = (ServerContainer) servletContext.getAttribute(ServerContainer.class.getName());
            log.info("| serverContainer = {}", serverContainer);
            log.info("| 精准匹配路由 (configExactMatchMap)");
            // 找到 Tomcat 底层的具体实现类类名 (通常是 org.apache.tomcat.websocket.server.WsServerContainer)
            Class<? extends ServerContainer> containerClass = serverContainer.getClass();
            Field exactMatchField = containerClass.getDeclaredField("configExactMatchMap");
            exactMatchField.setAccessible(true);
            Map<String, ?> exactMatchMap = (Map<String, ?>) exactMatchField.get(serverContainer);
            if (exactMatchMap != null && !exactMatchMap.isEmpty()) {
                for (Map.Entry<String, ?> entry : exactMatchMap.entrySet()) {
                    String path = entry.getKey();
                    Object exactPathMatchObj = entry.getValue(); // 内部类 ExactPathMatch
                    String handlerClassName = getClassNameFromMatchObject(exactPathMatchObj);
                    log.info("│ => [精准路由]  路径: {} | 处理类: {}", path, handlerClassName);
                }
            }
            log.info("| 动态模板路由 (configTemplateMatchMap)");
            Field templateMatchField = containerClass.getDeclaredField("configTemplateMatchMap");
            templateMatchField.setAccessible(true);
            Map<Integer, ConcurrentSkipListMap<String, ?>> templateMatchMap = (Map<Integer, ConcurrentSkipListMap<String, ?>>) templateMatchField.get(serverContainer);
            if (templateMatchMap != null && !templateMatchMap.isEmpty()) {
                for (ConcurrentSkipListMap<String, ?> subMap : templateMatchMap.values()) {
                    if (subMap != null && !subMap.isEmpty()) {
                        for (Map.Entry<String, ?> entry : subMap.entrySet()) {
                            String normalizedPath = entry.getKey(); // 归一化的模板路径
                            Object templatePathMatchObj = entry.getValue(); // 内部类 TemplatePathMatch
                            String handlerClassName = getClassNameFromMatchObject(templatePathMatchObj);
                            log.info("│ => [动态路由]  路径: {} | 处理类: {}", normalizedPath, handlerClassName);
                        }
                    }
                }
            }
        }

        /**
         * 辅助工具：由于 Tomcat 内部的 ExactPathMatch 和 TemplatePathMatch 是私有/包级私有的类，
         * 且两个类中都有 getConfig() 方法。这里通过反射统一获取对应的 EndpointClass 名字。
         */
        private String getClassNameFromMatchObject(Object matchObj) {
            try {
                Method getConfigMethod = matchObj.getClass().getDeclaredMethod("getConfig");
                getConfigMethod.setAccessible(true);
                ServerEndpointConfig config = (ServerEndpointConfig) getConfigMethod.invoke(matchObj);

                if (config != null && config.getEndpointClass() != null) {
                    return config.getEndpointClass().getName();
                }
            } catch (Exception e) {
                // 如果方法获取失败，尝试反射内部的 config 属性作为兜底
                try {
                    Field configField = matchObj.getClass().getDeclaredField("config");
                    configField.setAccessible(true);
                    ServerEndpointConfig config = (ServerEndpointConfig) configField.get(matchObj);
                    if (config != null && config.getEndpointClass() != null) {
                        return config.getEndpointClass().getName();
                    }
                } catch (Exception ignored) {}
            }
            return "UNKNOWN_HANDLER";
        }
    }
}
