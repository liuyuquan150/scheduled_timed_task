package indi.ly.crush.service.impl;

import indi.ly.crush.aware.SpringApplicationContextHolder;
import indi.ly.crush.manager.TimedTaskManager;
import indi.ly.crush.model.TaskBeanInformation;
import indi.ly.crush.model.TimedTask;
import indi.ly.crush.model.TimedTaskStatusEnum;
import indi.ly.crush.repository.ITimedTaskRepository;
import indi.ly.crush.service.ITimedTaskService;
import indi.ly.crush.task.TimedTaskRunnable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h2>系统定时任务服务实现</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
@Service
public class TimedTaskServiceImpl
		implements ITimedTaskService {
	final TimedTaskManager timedTaskManager;
	final ITimedTaskRepository timedTaskRepository;
	
	public TimedTaskServiceImpl(
			TimedTaskManager timedTaskManager,
			ITimedTaskRepository timedTaskRepository
	) {
		this.timedTaskManager = timedTaskManager;
		this.timedTaskRepository = timedTaskRepository;
	}
	
	@Override
	public void addTimedTask(TimedTask timedTask) {
		boolean needAdd = this.timedTaskRepository.findAll()
									.stream()
									.noneMatch(task -> task.equals(timedTask));
		
		if (!needAdd) {
			throw new RuntimeException("Timed tasks already exist, please do not add them again!");
		}
		
		if (SpringApplicationContextHolder.containsBean(timedTask.getExecutorName())) {
			TimedTask task = TimedTaskServiceImpl.this.timedTaskRepository.save(timedTask);
			if (task.getTaskStatus() == TimedTaskStatusEnum.RUN) {
				TimedTaskRunnable runnable = new TimedTaskRunnable(TaskBeanInformation.of(task));
				TimedTaskServiceImpl.this.timedTaskManager.run(runnable, task.getCronExpression());
			}
		}
		
		throw new RuntimeException("Please add a valid timed task.");
	}
	
	@Override
	public List<TimedTask> getAllTimedTasks() {
		return this.timedTaskRepository.findAll();
	}
	
	@Override
	public List<TimedTask> getAllRunnableTimedTasks() {
		return this.timedTaskRepository.findTimedTasksByTaskStatus(TimedTaskStatusEnum.RUN);
	}
	
	@Override
	public void updateTimedTask(TimedTask timedTask) {
		// NOTE saveAndFlush 操作不能与 getById 在同一个作用域内.
		TimedTask task = this.timedTaskRepository.getById(timedTask.getId());
		TimedTaskRunnable runnable;
		// 只对任务进行了 RUN 或 STOP 操作.
		if (task.equals(timedTask)) {
			task = this.timedTaskRepository.saveAndFlush(timedTask);
			runnable = new TimedTaskRunnable(TaskBeanInformation.of(task));
			if (task.getTaskStatus() == TimedTaskStatusEnum.STOP) {
				this.timedTaskManager.stop(runnable);
			}
			
		} else {
			// 如果将一个任务修改为另一个任务, 那么被修改前的任务是否已经在运行了呢? 如果是, 需要进行一个 STOP.
			runnable = new TimedTaskRunnable(TaskBeanInformation.of(task));
			if (task.getTaskStatus() == TimedTaskStatusEnum.RUN) {
				this.timedTaskManager.stop(runnable);
			}
			task = this.timedTaskRepository.saveAndFlush(timedTask);
			runnable = new TimedTaskRunnable(TaskBeanInformation.of(task));
		}
		
		// 被修改后, 该任务是否需要进行?
		if (task.getTaskStatus() == TimedTaskStatusEnum.RUN) {
			this.timedTaskManager.run(runnable, task.getCronExpression());
		}
	}
	
	@Override
	public void deleteTimedTaskById(Integer id) {
		this.timedTaskRepository.findById(id).ifPresentOrElse(timedTask -> {
			TimedTaskRunnable runnable = new TimedTaskRunnable(TaskBeanInformation.of(timedTask));
			this.timedTaskManager.stop(runnable);
			this.timedTaskRepository.delete(timedTask);
		}, () -> {
			throw new RuntimeException("Deleting the timed task with number '%d' failed because it does not exist".formatted(id));
		});
	}
}
