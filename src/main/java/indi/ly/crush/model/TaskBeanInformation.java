package indi.ly.crush.model;

import java.util.Objects;

/**
 * <h2>任务 Bean 信息</h2>
 * <p>
 *     待执行任务(<em>去执行某个方法</em>)的 Bean 的相关信息.
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 */
public class TaskBeanInformation {
	/**
	 * <p>
	 *     执行者的名称(<em>要执行任务的 Bean 的名称</em>).
	 * </p>
	 */
	private String executorName;
	/**
	 * <p>
	 *     待执行任务(<em>方法</em>)的名称.
	 * </p>
	 */
	private String taskName;
	/**
	 * <p>
	 *    任务(<em>方法</em>)参数.
	 * </p>
	 */
	private String taskParameters;
	
	private TaskBeanInformation(String executorName, String taskName, String taskParameters) {
		this.executorName = executorName;
		this.taskName = taskName;
		this.taskParameters = taskParameters;
	}
	
	public static TaskBeanInformation of(TimedTask timedTask) {
		return new TaskBeanInformation(
				timedTask.getExecutorName(),
				timedTask.getTaskName(),
				timedTask.getTaskParameters()
		);
	}
	
	public String getExecutorName() {
		return this.executorName;
	}
	
	public void setExecutorName(String executorName) {
		this.executorName = executorName;
	}
	
	public String getTaskName() {
		return this.taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getTaskParameters() {
		return this.taskParameters;
	}
	
	public void setTaskParameters(String taskParameters) {
		this.taskParameters = taskParameters;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TaskBeanInformation that = (TaskBeanInformation) o;
		return Objects.equals(this.executorName, that.executorName)
				&& Objects.equals(this.taskName, that.taskName)
				&& Objects.equals(this.taskParameters, that.taskParameters);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.executorName, this.taskName, this.taskParameters);
	}
	
	@Override
	public String toString() {
		return "TaskBeanInformation{" + "executorName='" + this.executorName + '\'' + ", taskName='" + this.taskName + '\''
				+ ", taskParameters='" + this.taskParameters + '\'' + '}';
	}
}
