package indi.ly.crush.model;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <h2>定时任务状态</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
@JsonFormat(shape = JsonFormat.Shape.NUMBER)
public enum TimedTaskStatusEnum {
	/**
	 * <p>
	 *     定时任务停止运行.
	 * </p>
	 */
	STOP("停止"),
	/**
	 * <p>
	 *     定时任务正在运行.
	 * </p>
	 */
	RUN("运行")
	;
	
	/**
	 * <p>
	 *     任务状态描述.
	 * </p>
	 */
	private final String description;
	
	TimedTaskStatusEnum(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
}
