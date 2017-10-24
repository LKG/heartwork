package im.heart.security;


import java.math.BigInteger;

import org.apache.shiro.authc.UsernamePasswordToken;
/**
 * 
 * @author gg
 * @desc 扩展密码验证token 添加验证码校验
 */
public class AccountToken extends UsernamePasswordToken {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6052412220330012704L;
	private BigInteger userId;
	private String tokenType;//token 类型 暂未使用
	//验证码字符串  
    private String validateCode; 
    
	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public AccountToken(String username, String digestpassword,
			boolean rememberMe, String host) {
		super(username, digestpassword, rememberMe, host);
	}
	public AccountToken(String username, String digestpassword,
			boolean rememberMe, String host, String validateCode) {
		super(username, digestpassword, rememberMe, host);
		this.validateCode=validateCode;
	}
	
	public AccountToken(String username, String digestpassword,
			boolean rememberMe, String host, String validateCode,BigInteger userId,String tokenType) {
		super(username, digestpassword, rememberMe, host);
		this.validateCode=validateCode;
		this.tokenType=tokenType;
		this.userId=userId;	
		
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
}
