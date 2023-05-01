package indi.ly.crush.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
* <h2>定时任务</h2>
*
* @since 1.0
* @author 云上的云
*/
@Table(
		name = "system_task",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {
						"executorName", "taskName", "taskParameters", "cronExpression"
				})
		}
)
@Entity
@EntityListeners(value = AuditingEntityListener.class)
public class TimedTask
		implements Serializable {
	@Serial
	private static final long serialVersionUID = -6849794470754667710L;
	/**
	 * <p>
	 *     定时任务唯一标识符.
	 * </p>
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * <p>
	 *     定时任务执行者(<em>Spring Bean</em>)名称.
	 * </p>
	 */
	@Column(nullable = false, length = 15)
	private String executorName;
	/**
	 * <p>
	 *     定时任务(<em>{@link #executorName} 方法</em>)名称.
	 * </p>
	 */
	@Column(nullable = false, length = 25)
	private String taskName;
	/**
	 * <p>
	 *    定时任务(<em>{@link #executorName} 方法</em>)参数.
	 * </p>
	 */
	@Column(nullable = false, length = 50)
	private String taskParameters;
	/**
	 * <p>
	 *     Cron 表达式字符串.
	 * </p>
	 */
	@Column(nullable = false, length = 15)
	private String cronExpression;
	/**
	 * <p>
	 *     定时任务状态.
	 * </p>
	 */
	@Enumerated(value = EnumType.ORDINAL)
	private TimedTaskStatusEnum taskStatus;
	/**
	 * <p>
	 *     定时任务备注.
	 * </p>
	 */
	@Column(length = 100)
	private String remark;
	/**
	 * <p>
	 *     定时任务首次创建时间.
	 * </p>
	 */
	@Column(nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@CreatedDate
	private LocalDateTime gmtCreate;
	/**
	 * <p>
	 *     定时任务修改时间.
	 * </p>
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@LastModifiedDate
	private LocalDateTime gmtModified;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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
	
	public String getCronExpression() {
		return this.cronExpression;
	}
	
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
	public TimedTaskStatusEnum getTaskStatus() {
		return this.taskStatus;
	}
	
	public void setTaskStatus(TimedTaskStatusEnum taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public LocalDateTime getGmtCreate() {
		return this.gmtCreate;
	}
	
	public void setGmtCreate(LocalDateTime gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
	public LocalDateTime getGmtModified() {
		return this.gmtModified;
	}
	
	public void setGmtModified(LocalDateTime gmtModified) {
		this.gmtModified = gmtModified;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		
		TimedTask timedTask = (TimedTask) o;
		// Determine if the timed tasks are the same based on the values of the following four parameters.
		return Objects.equals(this.executorName, timedTask.executorName) &&
				Objects.equals(this.taskName, timedTask.taskName) &&
				Objects.equals(this.taskParameters, timedTask.taskParameters) &&
				Objects.equals(this.cronExpression, timedTask.cronExpression);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.executorName, this.taskName, this.taskParameters, this.cronExpression);
	}
	
	@Override
	public String toString() {
		return "TimedTask{" + "id=" + this.id + ", executorName='" + this.executorName + '\'' + ", taskName='" + this.taskName + '\''
				+ ", taskParameters='" + this.taskParameters + '\'' + ", cronExpression='" + this.cronExpression + '\''
				+ ", taskStatus=" + this.taskStatus + ", remark='" + this.remark + '\'' + ", gmtCreate=" + this.gmtCreate
				+ ", gmtModified=" + this.gmtModified + '}';
	}
}
