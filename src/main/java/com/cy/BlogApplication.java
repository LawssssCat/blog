package com.cy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

	
	public static void main(String[] args) {
		StopWatch clock = new StopWatch() ;
		clock.start("启动SpringBoot项目");
		SpringApplication.run(BlogApplication.class, args);
		clock.stop();
		log.info("=================================== 启动完毕,用时:{} seconds ==================================",clock.getTotalTimeSeconds());
	}
	
	@Autowired
	private ApplicationContext applicationContext  ; 

	/*
	 * implements CommandLineRunner
	 * 项目启动完成后 执行
	 * 
	 */
	@Override
	public void run(String... args) throws Exception {
		String[] names = applicationContext.getBeanDefinitionNames();
		int index = 1 ; 
		for (String name : names) {
			log.info("{}: name=[{}] || class=[{}]", index++ , name ,applicationContext.getBean(name).getClass());
		}
		log.info("容器注入javaBean:{}个.",applicationContext.getBeanDefinitionCount());
	}

}
