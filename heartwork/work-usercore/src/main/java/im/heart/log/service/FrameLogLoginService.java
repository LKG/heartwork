package im.heart.log.service;

import java.math.BigInteger;

import im.heart.core.service.CommonService;
import im.heart.log.entity.FrameLogLogin;

public interface FrameLogLoginService extends CommonService<FrameLogLogin, BigInteger>{
	public static final String BEAN_NAME = "frameLogLoginService";

	public void loginlog(FrameLogLogin entity);
}
