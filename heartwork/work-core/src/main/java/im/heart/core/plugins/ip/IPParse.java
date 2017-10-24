package im.heart.core.plugins.ip;
/**
 * 
 * @author lkg
 * @desc iP 解析接口
 */
public interface IPParse {
	/**
	 * 
	 * @Desc： 根据Ip 获取IpInfo 对象
	 * @param ip
	 * @return
	 * @throws IPParseException
	 */
	public  IPInfo getIp(String ip) throws IPParseException;
	
	public  String getIpInfo(String ip) throws IPParseException;
}
