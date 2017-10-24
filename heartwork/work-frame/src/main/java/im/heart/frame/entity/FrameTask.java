package im.heart.frame.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

import im.heart.core.entity.AbstractEntity;

/**
 * 
 * @author gg
 * @desc :模板类
 */
@Entity
@Table(name = "dic_frame_task")
@DynamicUpdate(true)
public class FrameTask implements AbstractEntity<BigInteger> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1984571345113327046L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TASK_ID", nullable = false,updatable = false, length=32)
	private BigInteger taskId;
	
	@NotBlank
	@Column(name = "TASK_NAME", nullable = false, length = 128)
	private String taskName;
	
	@NotBlank
	@Column(name = "TASK_CRON", nullable = false, length = 128)
	private String taskCron;
	
	@NotBlank
	@Column(name = "TASK_TYPE", nullable = false, length = 128)
	private String taskType;
	@NotBlank
	@Column(name = "TASK_DESC",length = 512)
	private String taskDesc;
	
	
	@NotBlank
	@Column(name = "TASK_GROUP", nullable = false, length = 128)
	private String taskGroup;
	
	@NotBlank
	@Column(name = "INVOKE_CHANNEL", nullable = false, length = 128)
	private String invokeChannel;
	
	@NotBlank
	@Column(name = "INVOKE_METHOD", nullable = false, length = 128)
	private String invokeMethod;
	
	@NotNull
	@Column(name = "IS_START",nullable = false)
	private Boolean start=Boolean.FALSE;
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss" )
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable=false, name = "CREATE_TIME" ,updatable = false)
	private Date createTime;
	@JSONField (format="yyyy-MM-dd HH:mm:ss" ,serialize=false)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable=false, name = "MODI_TIME")
	private Date modiTime;
	public BigInteger getTaskId() {
		return taskId;
	}
	public void setTaskId(BigInteger taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskCron() {
		return taskCron;
	}
	public void setTaskCron(String taskCron) {
		this.taskCron = taskCron;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getTaskGroup() {
		return taskGroup;
	}
	public void setTaskGroup(String taskGroup) {
		this.taskGroup = taskGroup;
	}
	public String getInvokeChannel() {
		return invokeChannel;
	}
	public void setInvokeChannel(String invokeChannel) {
		this.invokeChannel = invokeChannel;
	}
	public String getInvokeMethod() {
		return invokeMethod;
	}
	public void setInvokeMethod(String invokeMethod) {
		this.invokeMethod = invokeMethod;
	}
	public Boolean getStart() {
		return start;
	}
	public void setStart(Boolean start) {
		this.start = start;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModiTime() {
		return modiTime;
	}
	public void setModiTime(Date modiTime) {
		this.modiTime = modiTime;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	
}
