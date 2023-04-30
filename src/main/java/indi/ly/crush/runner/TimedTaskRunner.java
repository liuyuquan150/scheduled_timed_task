package indi.ly.crush.runner;

import indi.ly.crush.manager.TimedTaskManager;
import indi.ly.crush.model.TaskBeanInformation;
import indi.ly.crush.service.ITimedTaskService;
import indi.ly.crush.task.TimedTaskRunnable;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <h2>定时任务运行器</h2>
 * <p>
 *     服务一启动, 便执行所有可运行的系统定时任务.
 * </p>
 *
 * @author 云上的云
 * @see CommandLineRunner
 * @see PostConstruct
 * @since 1.0
 */
@Component
public class TimedTaskRunner
		implements ApplicationRunner, Ordered {
	final TimedTaskManager timedTaskManager;
	final ITimedTaskService timedTaskServiceImpl;
	
	public TimedTaskRunner(TimedTaskManager timedTaskManager, ITimedTaskService timedTaskServiceImpl) {
		this.timedTaskManager = timedTaskManager;
		this.timedTaskServiceImpl = timedTaskServiceImpl;
	}
	
	@Override
	public void run(ApplicationArguments args) {
		this.timedTaskServiceImpl.getAllRunnableTimedTasks().forEach(runnableTimedTask -> {
			TaskBeanInformation taskBeanInformation = TaskBeanInformation.of(runnableTimedTask);
			TimedTaskRunnable timedTaskRunnable = new TimedTaskRunnable(taskBeanInformation);
			this.timedTaskManager.run(timedTaskRunnable, runnableTimedTask.getCronExpression());
		});
	}
	
	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 100;
	}
}
