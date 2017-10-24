package im.heart.core.service;

/**
 * 
 * @author gg
 * @Desc : 自定义异常，用于事务回滚
 */
public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5315036605976200504L;
	private String code;
	private String message;

	public ServiceException() {

	}

	/**
	 * 根据自定义信息构建异常对象
	 * 
	 * @param message
	 *            自定义的异常信息
	 */
	public ServiceException(String message) {
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
	public ServiceException(String code, String message) {
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

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
