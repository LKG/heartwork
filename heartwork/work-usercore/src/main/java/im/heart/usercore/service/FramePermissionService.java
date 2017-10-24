package im.heart.usercore.service;

import java.math.BigInteger;
import java.util.List;

import im.heart.core.service.CommonService;
import im.heart.usercore.entity.FramePermission;

/**
 * 
 * @author gg
 * @desc FramePermission接口
 */
public interface   FramePermissionService extends CommonService<FramePermission, BigInteger>{
	
	public static final String BEAN_NAME = "framePermissionService";
	public List<FramePermission> findAll(Iterable<BigInteger> ids);
}
