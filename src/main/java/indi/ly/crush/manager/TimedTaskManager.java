package indi.ly.crush.manager;

/**
 * <h2>定时任务管理器</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public interface TimedTaskManager {
	/**
	 * <p>
	 *     在指定的某个未来(cronExpression)调度执行给定的定时任务.
	 * </p>
	 *
	 * @param task           待运行的定时任务.
	 * @param cronExpression Cron 表达式.
	 */
	void run(Runnable task, String cronExpression);
	/**
	 * <p>
	 *     停止一个定时任务的运行.
	 * </p>
	 *
	 * @param task 待停止运行的定时任务.
	 */
	void stop(Runnable task);
}
