package im.heart.frame.service;

import java.math.BigInteger;
import java.util.List;

import im.heart.core.service.CommonService;
import im.heart.frame.entity.FrameTask;

/**
 * 
 * @author gg
 * @desc 动态任务操作接口
 */
public interface   FrameTaskService extends CommonService<FrameTask, BigInteger>{
	
	public static final String BEAN_NAME = "frameTaskService";
	public List<FrameTask> findAll();
	public FrameTask update(FrameTask td);
	public void delete(BigInteger ...ids);
}
