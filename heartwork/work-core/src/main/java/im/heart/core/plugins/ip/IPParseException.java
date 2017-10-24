package im.heart.core.plugins.ip;

/**
 * 
 * @author lkg
 * @desc  自定义IP 解析异常
 */
public class IPParseException extends RuntimeException {


	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5301265969225673368L;

	public IPParseException(String msg) {
		super(msg);
	}

	public IPParseException(String msg, Throwable cause) {
		super(msg, cause);
	}
}