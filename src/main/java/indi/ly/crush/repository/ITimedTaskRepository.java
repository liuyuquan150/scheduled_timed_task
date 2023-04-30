package indi.ly.crush.repository;

import indi.ly.crush.model.TimedTask;
import indi.ly.crush.model.TimedTaskStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <h2>系统定时任务资料库</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public interface ITimedTaskRepository
		extends JpaRepository<TimedTask, Integer> {
	/**
	 * <p>
	 *     根据定时任务状态来检索符合条件的所有定时任务.
	 * </p>
	 *
	 * @param taskStatus 定时任务状态.
	 * @return 符合条件的所有定时任务.
	 */
	List<TimedTask> findTimedTasksByTaskStatus(TimedTaskStatusEnum taskStatus);
}
