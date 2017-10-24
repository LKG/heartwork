package im.heart.usercore.service;

import im.heart.usercore.entity.FrameUser;



public interface PasswordService {
	public static final String BEAN_NAME = "passwordService";
	/**
	 * 
	 * @desc：密码加密
	 * @param frameUser
	 */
	public void encryptPassword(FrameUser frameUser, String newPassword);
	/**
	 * 
	 * @desc：判断密码是否一致
	 * @param frameUser
	 * @param newPassword
	 * @return
	 */
	public boolean passwordsMatch(FrameUser frameUser, String newPassword);
	
}
