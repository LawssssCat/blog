package org.example;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

/**
 * mojo: maven old java abject，每个 mojo 类都是 maven plugin 的一个执行目标（goal）。
 * @goal comment
 */
public class CommentMojo extends AbstractMojo {
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Log log = getLog();
        log.info("执行了 goal:comment 的 execute 方法");
    }
}
