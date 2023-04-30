package indi.ly.crush.manager;

import org.springframework.beans.factory.DisposableBean;

/**
 * <h2>定时任务基础管理器</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public abstract class AbstractTimedTaskManager
		implements TimedTaskManager, DisposableBean {}
