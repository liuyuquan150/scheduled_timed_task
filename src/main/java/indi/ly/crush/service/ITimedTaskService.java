package indi.ly.crush.service;

import indi.ly.crush.model.TimedTask;

import java.util.List;

/**
 * <h2>系统定时任务服务</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public interface ITimedTaskService {
	/**
	 * <p>
	 *     添加定时任务.
	 * </p>
	 *
	 * @param timedTask 待添加的定时任务.
	 */
	void addTimedTask(TimedTask timedTask);
	/**
	 * <p>
	 *     获取所有定时任务(<em>无论状态是运行还是停止</em>).
	 * </p>
	 *
	 * @return 所有定时任务.
	 */
	List<TimedTask> getAllTimedTasks();
	/**
	 * <p>
	 *     获取所有可运行的定时任务.
	 * </p>
	 *
	 * @return 所有可运行的定时任务.
	 */
	List<TimedTask> getAllRunnableTimedTasks();
	/**
	 * <p>
	 *     修改定时任务.
	 * </p>
	 *
	 * @param timedTask 修改信息.
	 */
	void updateTimedTask(TimedTask timedTask);
	/**
	 * <p>
	 *     根据唯一标识符删除对应的定时任务.
	 * </p>
	 *
	 * @param id 定时任务的唯一标识符.
	 */
	void deleteTimedTaskById(Integer id);
}
