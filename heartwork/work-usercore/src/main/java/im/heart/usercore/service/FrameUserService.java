package im.heart.usercore.service;

import java.math.BigInteger;
import java.util.Set;

import im.heart.core.service.CommonService;
import im.heart.core.service.ServiceException;
import im.heart.usercore.entity.FrameUser;
import im.heart.usercore.exception.IncorrectCredentialsException;

/**
 * 
 * @author gg
 * @desc : 角色接口
 */
public interface   FrameUserService extends CommonService<FrameUser, BigInteger>{
	
	public static final String BEAN_NAME = "frameUserService";
	
	public void updateUserheadPortrait(BigInteger userId,String headPortrait);
	/**
	 * @desc：创建用户
	 * @param frameUser
	 * @return
	 * @throws ServiceException
	 */
	public FrameUser save(FrameUser frameUser)  throws ServiceException ;
	
//	/**
//	 * @desc：激活用户
//	 * @param userId
//	 * @return
//	 */
//	public FrameUser activateUser(BigInteger userId);
//	
//	/**
//	 * @desc：禁用用户
//	 * @param userId
//	 * @return
//	 */
//	public FrameUser disabledUser(BigInteger userId);
	
	/**
	 * 
	 * @desc：激活用户邮箱
	 * @param userEmail
	 * @return
	 * @throws ServiceException
	 */
	public FrameUser activateUserEmail(String userEmail)  throws ServiceException ;
	
	
	/**
	 * 
	 * @desc：根据用户名称查询用户信息
	 * @param userName
	 * @return
	 */
	public FrameUser findByUserName(String userName);
	/**
	 * 
	 * @desc：根据电话号码查找
	 * @param userPhone
	 * @return
	 */
	public FrameUser findByUserPhone(String userPhone);
	/**
	 * 
	 * @desc：根据邮箱查找
	 * @param userEmail
	 * @return
	 */
	public FrameUser findByUserEmail(String userEmail);
	
	/**
	 * 
	 * @desc：自动选择账号登录，可以使用邮箱，账号，或者注册手机号
	 * @param account
	 * @return
	 */
	public FrameUser findFrameUser(String account);
	
	 /**
	  * 
	  * @desc：修改密码
	  * @param userId
	  * @param newPwd
	  */
	 public FrameUser changePassword(BigInteger userId,String oldPwd ,String newPwd) throws IncorrectCredentialsException;
	/**
	 * 
	 * @desc：重置密码
	 * @param userId
	 * @param newPwd
	 */
	 public FrameUser resetPassword(BigInteger userId, String newPwd) throws IncorrectCredentialsException;
	 
	 /**
	  * 
	  * @desc：根据用户Id 查询用户角色权限
	  * @param userId
	  * @return
	  */
	 public Set<String> findRoleCodesByUserId(BigInteger userId);
	 /**
	  * @desc：只更新用户的默认机构信息
	  * @param userId
	  * @param defaultorgId
	  */
	 public void updateUserDefaultOrg(BigInteger userId,BigInteger defaultorgId);
	 public boolean existsUserName(String userName);
	 public boolean existsUserPhone(String userPhone);
	 public boolean existsUserEmail(String userEmail);
}
