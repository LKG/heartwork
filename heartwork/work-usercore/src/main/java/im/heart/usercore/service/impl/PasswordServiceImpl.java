package im.heart.usercore.service.impl;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import im.heart.usercore.entity.FrameUser;
import im.heart.usercore.service.PasswordService;

/**
 * 
 * @author gg
 * @desc 密码加密帮助类
 */
@Service(value = PasswordService.BEAN_NAME)
@PropertySource(value = "classpath:/application-shiro.properties")
public class PasswordServiceImpl implements PasswordService {
	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

	@Value("${shiro.password.algorithmName}")
	private String algorithmName = "md5";
	@Value("${shiro.password.hashIterations}")
	private int hashIterations = 2;

	public void setRandomNumberGenerator(
			RandomNumberGenerator randomNumberGenerator) {
		this.randomNumberGenerator = randomNumberGenerator;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public void setHashIterations(int hashIterations) {
		this.hashIterations = hashIterations;
	}

	public void encryptPassword(FrameUser user, String newPassword) {
		if (user != null && user.getPassWord() != null) {
			user.setSaltKey(this.randomNumberGenerator.nextBytes().toHex());
			String newSaltPassword = new SimpleHash(algorithmName, newPassword,
					ByteSource.Util.bytes(user.getCredentialsSalt()),
					hashIterations).toHex();
			user.setPassWord(newSaltPassword);
		}
	}

	@Override
	public boolean passwordsMatch(FrameUser frameUser, String newPassword) {
		String slafPassword = new SimpleHash(algorithmName, newPassword,
				ByteSource.Util.bytes(frameUser.getCredentialsSalt()),
				hashIterations).toHex();
		if (frameUser.getPassWord() != null
				&& frameUser.getPassWord().equals(slafPassword)) {
			return true;
		}
		return false;
	}

}
