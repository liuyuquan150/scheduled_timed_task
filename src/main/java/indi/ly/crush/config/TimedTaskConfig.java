package indi.ly.crush.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.TaskUtils;

/**
 * <h2>定时任务配置</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
@Configuration
public class TimedTaskConfig {
	
	@Bean(name = "ThreadPoolTaskScheduler")
	public TaskScheduler createThreadPoolTaskSchedulerBean() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(4);
		// 取消定时任务时, 则移除这个定时任务对应的线程.
		threadPoolTaskScheduler.setRemoveOnCancelPolicy(true);
		threadPoolTaskScheduler.setThreadNamePrefix("TaskTaskScheduler-");
		threadPoolTaskScheduler.setErrorHandler(TaskUtils.getDefaultErrorHandler(false));
		return threadPoolTaskScheduler;
	}
}
