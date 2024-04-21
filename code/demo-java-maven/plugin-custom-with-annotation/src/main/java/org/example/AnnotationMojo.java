package org.example;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "annotation", defaultPhase = LifecyclePhase.COMPILE)
public class AnnotationMojo extends AbstractMojo {
    /**
     * 注解支持 maven 表达式写法 ${}，用于注入项目配置信息
     */
    @Parameter(defaultValue = "${project}")
    private MavenProject mavenProject;
    @Parameter(required = true)
    private String name;
    @Parameter
    private int age;
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Log log = getLog();
        log.info("执行了 goal:annotation 的 execute 方法");
        log.info(String.format("project.build.finalName = %s", mavenProject.getBuild().getFinalName()));
        log.info(String.format("project.packing = %s", mavenProject.getPackaging()));
        log.info(String.format("name=%s, age=%d", name, age));
    }
}
