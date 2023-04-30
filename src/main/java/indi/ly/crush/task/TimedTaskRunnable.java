package indi.ly.crush.task;

import indi.ly.crush.aware.SpringApplicationContextHolder;
import indi.ly.crush.model.TaskBeanInformation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <h2>定时任务运行</h2>
 * <p>
 *     每一个定时任务对应一个子线程.
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 */
public class TimedTaskRunnable
		implements Runnable {
	static final Log LOG = LogFactory.getLog(TimedTaskRunnable.class);
	final TaskBeanInformation taskBeanInformation;
	/**
	 * <p>
	 *     一个托管于 Spring IoC 容器的 Bean, 该 Bean 可以认为是执行定时任务的执行者.
	 * </p>
	 */
	Object taskBean;
	/**
	 * <p>
	 *     要执行的任务, 也就是 {@link #taskBean} 要调用的方法.
	 * </p>
	 */
	Method taskMethod;
	
	public TimedTaskRunnable(TaskBeanInformation taskBeanInformation) {
		this.taskBeanInformation = Objects.requireNonNull(taskBeanInformation);
		this.taskBean = this.findTaskBean();
		this.taskMethod = this.findTaskMethod();
	}
	
	@Override
	public void run(){
		LOG.info("The timed task starts execution and the relevant parameters are: " + this.taskBeanInformation);
		long startTime = System.nanoTime();
		String taskParameters = this.taskBeanInformation.getTaskParameters();
		try {
			if (StringUtils.hasText(taskParameters)) {
				ReflectionUtils.invokeMethod(this.taskMethod,this.taskBean, taskParameters);
			} else {
				ReflectionUtils.invokeMethod(this.taskMethod,this.taskBean);
			}
		} catch (Exception e) {
			LOG.info("Timed task execution exceptions, the relevant parameters are: " + this.taskBeanInformation, e);
		}
		long cost = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - startTime);
		LOG.info("Timed task execution, time consuming '%d', the relevant parameters are: %s".formatted(cost, this.taskBeanInformation));
	}
	
	private Object findTaskBean() {
		String executorName = this.taskBeanInformation.getExecutorName();
		try {
			// 当定义两个同名不同类型的 Bean 时, Spring 默认会抛出异常.
			return SpringApplicationContextHolder.getBean(executorName);
		} catch (NoSuchBeanDefinitionException e) {
			LOG.error("Unable to find task performer with name '%s'".formatted(executorName));
			throw new RuntimeException(e);
		}
	}
	
	private Method findTaskMethod() {
		Class<?> beanClass = this.taskBean.getClass();
		String taskParameters = this.taskBeanInformation.getTaskParameters();
		String taskName = taskBeanInformation.getTaskName();
		
		Method taskMethod = StringUtils.hasText(taskParameters)
					? ReflectionUtils.findMethod(beanClass, taskName, String.class)
					: ReflectionUtils.findMethod(beanClass, taskName);
		
		if (taskMethod == null) {
			String canonicalName = beanClass.getCanonicalName();
			throw new RuntimeException("The task(method) with the name '%s' cannot be found from '%s'(method)".formatted(canonicalName, taskName));
		}
		
		ReflectionUtils.makeAccessible(taskMethod);
		return taskMethod;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TimedTaskRunnable that = (TimedTaskRunnable) o;
		return Objects.equals(this.taskBeanInformation, that.taskBeanInformation);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.taskBeanInformation);
	}
}
