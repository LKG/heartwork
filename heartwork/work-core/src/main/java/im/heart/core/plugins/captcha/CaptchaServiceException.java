package im.heart.core.plugins.captcha;

public class CaptchaServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1412466935970502791L;
	private String code;
	private String message;

	public CaptchaServiceException() {

	}

	/**
	 * 根据自定义信息构建异常对象
	 * 
	 * @param message
	 *            自定义的异常信息
	 */
	public CaptchaServiceException(String message) {
		super(message);
		this.message = message;
	}

	/**
	 * 根据错误代码和自定义信息构建异常对象
	 * 
	 * @param code
	 *            错误代码
	 * @param message
	 *            自定义的异常信息
	 */
	public CaptchaServiceException(String code, String message) {
		super(code + "@" + message);
		this.code = code;
		this.message = message;
	}

	/**
	 * 获取异常状态码
	 * 
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 获取异常信息
	 */
	@Override
	public String getMessage() {
		return message;
	}

	public CaptchaServiceException(Throwable cause) {
		super(cause);
	}

	public CaptchaServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}