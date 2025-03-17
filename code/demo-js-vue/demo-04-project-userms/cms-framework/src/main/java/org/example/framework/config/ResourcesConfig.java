package org.example.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 通用配置
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {
    // cors 设置在 @link FilterConfig

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /** TODO 本地文件上传路径 */

        /** TODO swagger配置 */
    }

}
