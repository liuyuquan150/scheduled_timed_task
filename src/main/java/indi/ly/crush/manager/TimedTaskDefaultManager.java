package indi.ly.crush.manager;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * <h2>定时任务默认管理器</h2>
 * <p>
 *     该管理器不支持分布式环境. <br />
 *
 *     如果想要支持分布式环境,
 *     请通过一个跨 JVM 的中间件(<em>zookeeper 或者 redis</em>)作为信息中心来记录定时任务的各种运行情况.
 * </p>
 *
 * @author 云上的云
 * @see CronTask
 * @since 1.0
 */
@Component
public class TimedTaskDefaultManager
		extends AbstractTimedTaskManager {
	final TaskScheduler taskScheduler;
	final Map<Runnable, ScheduledFuture<?>> timedTaskMap = new ConcurrentHashMap<>(16);
	
	public TimedTaskDefaultManager(TaskScheduler taskScheduler) {
		this.taskScheduler = taskScheduler;
	}
	
	@Override
	public void run(Runnable task, String cronExpression) {
		Assert.notNull(task, "task is null.");
		if (this.timedTaskMap.containsKey(task)) {
			this.stop(task);
		}
		CronTrigger cronTrigger = new CronTrigger(cronExpression);
		// 将定时任务线程放在线程池中, 等待让触发器触发该定时任务线程.
		ScheduledFuture<?> future = this.taskScheduler.schedule(task, cronTrigger);
		this.timedTaskMap.put(task, future);
	}
	
	@Override
	public void stop(Runnable task) {
		Assert.notNull(task, "task is null.");
		ScheduledFuture<?> future = this.timedTaskMap.remove(task);
		if (future != null) {
			future.cancel(true);
		}
	}
	
	@Override
	public void destroy() {
		this.timedTaskMap.values().forEach(future -> future.cancel(true));
		this.timedTaskMap.clear();
	}
}
