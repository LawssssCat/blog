package org.example.framework.config;

import java.io.IOException;
import java.util.*;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.common.utils.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Mybatis支持*匹配扫描包
 */
@Slf4j
@Configuration
// 指定要扫描的Mapper类的包的路径
@MapperScan("org.example.**.mapper")
public class MyBatisConfig {
    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    @javax.annotation.Resource
    private Environment environment;

    private String resolveTypeAliasesPackage(String typeAliasesPackage) {
        try {
            Set<String> allResult = new HashSet<>();
            // 解析路径
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver); // TODO 作用
            for (String aliasesPackage : typeAliasesPackage.split(",")) {
                List<String> result = new ArrayList<String>();
                aliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                    + ClassUtils.convertClassNameToResourcePath(aliasesPackage.trim()) + "/" + DEFAULT_RESOURCE_PATTERN;
                Resource[] resources = resolver.getResources(aliasesPackage);
                if (resources != null && resources.length > 0) {
                    for (Resource resource : resources) {
                        if (resource.isReadable()) {
                            MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                            try {
                                String className = metadataReader.getClassMetadata().getClassName();
                                String packageName = Class.forName(className).getPackage().getName();
                                result.add(packageName);
                            } catch (ClassNotFoundException e) {
                                log.error("fail to resource read metadata. " + resource, e);
                            }
                        }
                    }
                }
                if (result.size() > 0) {
                    allResult.addAll(result);
                }
            }
            // 处理解析结果
            if (allResult.size() > 0) {
                typeAliasesPackage = String.join(",", allResult.toArray(new String[0]));
            } else {
                throw new RuntimeException("mybatis typeAliasesPackage 路径扫描错误,参数typeAliasesPackage:" + typeAliasesPackage + "未找到任何包");
            }
        } catch (Exception e) {
            log.error("fail to resolve mybatis.typeAliasesPackage", e);
        }
        return typeAliasesPackage;
    }

    private Resource[] resolveMapperLocations(String[] mapperLocations) {
        // 解析路径
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<>();
        if (mapperLocations != null) {
            for (String mapperLocation : mapperLocations) {
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        String typeAliasesPackage = environment.getProperty("mybatis.typeAliasesPackage");
        String mapperLocations = environment.getProperty("mybatis.mapperLocations");
        String configLocation = environment.getProperty("mybatis.configLocation");
        // VFS.addImplClass(SpringBootVFS.class); // TODO 作用

        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage(resolveTypeAliasesPackage(typeAliasesPackage));
        sessionFactory.setMapperLocations(resolveMapperLocations(StringUtils.split(mapperLocations, ",")));
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
        return sessionFactory.getObject();
    }
}
