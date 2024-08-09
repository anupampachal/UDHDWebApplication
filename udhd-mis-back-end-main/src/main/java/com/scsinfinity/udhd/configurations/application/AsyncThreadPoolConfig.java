package com.scsinfinity.udhd.configurations.application;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

@Configuration
public class AsyncThreadPoolConfig {

	@Value("${scs.setting.thread-pool.core-pool-size}")
	private String corePoolSize;

	@Value("${scs.setting.thread-pool.max-pool-size}")
	private String maxPoolSize;

	@Value("${scs.setting.thread-pool.keep-alive-in-sec}")
	private String keepAliveInSec;

	@Value("${scs.setting.thread-pool.queue-capacity}")
	private String queueCapacity;

	@Value("${scs.setting.thread-pool.prefix}")
	private String prefix;

	@Value("${scs.setting.thread-pool.wait-for-task-completion-on-shutdown}")
	private String waitForTaskCompletionOnShutdown;

	@Value("${scs.setting.thread-pool.await-termination}")
	private String awaitTermination;

	@Bean
	public ThreadPoolTaskExecutor threadPoolInfo() {
		ThreadPoolTaskExecutor threadPoolInfo = new ThreadPoolTaskExecutor();
		threadPoolInfo.setCorePoolSize(Integer.parseInt(corePoolSize));
		threadPoolInfo.setMaxPoolSize(Integer.parseInt(maxPoolSize));
		threadPoolInfo.setKeepAliveSeconds(Integer.parseInt(keepAliveInSec));
		threadPoolInfo.setQueueCapacity(Integer.parseInt(queueCapacity));
		threadPoolInfo.setThreadNamePrefix(prefix);
		threadPoolInfo.setWaitForTasksToCompleteOnShutdown(Boolean.parseBoolean(waitForTaskCompletionOnShutdown));
		threadPoolInfo.setAwaitTerminationSeconds(Integer.parseInt(awaitTermination));
		ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
		threadPoolInfo.setRejectedExecutionHandler(callerRunsPolicy);
		threadPoolInfo.initialize();
		return threadPoolInfo;
	}

//propagates the security context to the async thread	
	@Bean
	public DelegatingSecurityContextAsyncTaskExecutor taskExecutor(ThreadPoolTaskExecutor delegate) {
		return new DelegatingSecurityContextAsyncTaskExecutor(delegate);
	}

}
