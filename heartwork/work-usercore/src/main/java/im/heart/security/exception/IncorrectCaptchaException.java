package im.heart.security.exception;

import org.apache.shiro.authc.AuthenticationException;
/**
 * 
 * @author gg
 * @desc 自定义异常验证码错误
 */
public class IncorrectCaptchaException extends AuthenticationException{


	/**
	 * 
	 */
	private static final long serialVersionUID = 518970916674176207L;

	public IncorrectCaptchaException() {
        super();
    }

    public IncorrectCaptchaException(String message) {
        super(message);
    }

    public IncorrectCaptchaException(Throwable cause) {
        super(cause);
    }

    public IncorrectCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

}
