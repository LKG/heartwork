package im.heart.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;


public class BaseUtils {
	
	protected static final Logger logger = LoggerFactory.getLogger(BaseUtils.class);
	/**
	 * 
	 * @Desc：获取客户IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = overshot(request);
		return ip;
	}
	/**
	 * @Desc：获取本机IP地址
	 * @return
	 */
	public static String getServerIp(){
		String serverIp="";
		try {  
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();  
	        InetAddress ip = null;  
	        while (netInterfaces.hasMoreElements()) {  
	            NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();  
	            Enumeration<InetAddress>  addresses = ni.getInetAddresses();
	            while (addresses.hasMoreElements()) {  
	            	ip = (InetAddress) addresses.nextElement();
	            	if (ip != null && ip instanceof Inet4Address){
	            		serverIp = ip.getHostAddress();  
	            		return serverIp;
	            	}
	            }
	        }  
	    } catch (SocketException e) {  
	    	logger.error(e.getStackTrace()[0].getMethodName(), e);
	    }  
	     
	     return serverIp;  
	}
	/**
	 * 
	 * @Desc：设置客户端缓存过期时间 的Header.
	 * @param response
	 * @param expiresSeconds
	 */
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		// Http 1.0 header, set a fix expires date.
		response.setDateHeader(HttpHeaders.EXPIRES, System.currentTimeMillis() + (expiresSeconds * 1000));
		// Http 1.1 header, set a time after now.
		response.setHeader(HttpHeaders.CACHE_CONTROL, "private, max-age=" + expiresSeconds);
	}
	/**
	 * 
	 * @Desc：设置禁止客户端缓存的Header.
	 * @param response
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader(HttpHeaders.EXPIRES, 1L);
		response.addHeader(HttpHeaders.PRAGMA, "no-cache");
		// Http 1.1 header
		response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, max-age=0");
	}

	/**
	 * 
	 * @Desc：设置LastModified Header.
	 * @param response
	 * @param lastModifiedDate
	 */
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
		response.setDateHeader(HttpHeaders.LAST_MODIFIED, lastModifiedDate);
	}
	
	/**
	 * 
	 * @Desc： 设置Etag Header.
	 * @param response
	 * @param etag
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader(HttpHeaders.ETAG, etag);
	}
	/**
	 * 
	 * @Desc： 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.如果无修改, checkIfModify返回false ,设置304 not modify status. 用来判定重复提交
	 * @param request
	 * @param response
	 * @param lastModified  内容的最后修改时间.
	 * @return
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
			long lastModified) {
		long ifModifiedSince = request.getDateHeader(HttpHeaders.IF_MODIFIED_SINCE);
		if ((ifModifiedSince != -1) && (lastModified < (ifModifiedSince + 1000))) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @Desc： 根据浏览器 If-None-Match Header, 计算Etag是否已无效.如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status. 用来判定重复提交
	 * @param request
	 * @param response
	 * @param etag  内容的ETag.
	 * @return
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		String headerValue = request.getHeader(HttpHeaders.IF_NONE_MATCH);
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader(HttpHeaders.ETAG, etag);
				return false;
			}
		}
		return true;
	}
	/**
	 * 
	 * @Desc：设置让浏览器弹出下载对话框的Header,不同浏览器使用不同的编码方式.
	 * @param request
	 * @param response
	 * @param fileName 下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
		final String contentDisposition = "Content-Disposition";
		try {
			String agent = request.getHeader("User-Agent");
			String encodedfileName = null;
	        if (null != agent) {  
	        	agent = agent.toLowerCase();  
	            if (agent.contains("firefox") || agent.contains("chrome") || agent.contains("safari")) {  
	    			encodedfileName = "filename=\"" + new String(fileName.getBytes(), "ISO8859-1") + "\"";
	            } else if (agent.contains("msie")) {  
	            	encodedfileName = "filename=\"" + URLEncoder.encode(fileName,"UTF-8") + "\"";
	            } else if (agent.contains("opera")) {  
	            	encodedfileName = "filename*=UTF-8\"" + fileName + "\"";
	            } else {
	            	encodedfileName = "filename=\"" + URLEncoder.encode(fileName,"UTF-8") + "\"";
	            }
	        }
	        response.setHeader(contentDisposition, "attachment; " + encodedfileName);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		}
	}
	/**
	 * 
	 * @Desc：检查是否支持gzip
	 * @param request
	 * @return
	 */
	public static boolean checkAccetptGzip(HttpServletRequest request) {
		// Http1.1 header
		String acceptEncoding = request.getHeader("Accept-Encoding");
		return StringUtils.contains(acceptEncoding, "gzip");
	}
	/**
	 * 
	 * @Desc：设置gzip 压缩请求头
	 * @param response
	 */
	public static void setGzipHeader(HttpServletResponse response) {
		response.setHeader("Content-Encoding", "gzip");
		response.setHeader("Vary", "Accept-Encoding");
	}
	
	/**
	 * 
	 * @Desc：判断是否为ajax 请求
	 * @param request
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request){
		String requestType = request.getHeader("X-Requested-With");
		if("XMLHttpRequest".equalsIgnoreCase(requestType)){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @Desc：转换客户真实IP地址
	 * @param request
	 * @return
	 */
	private static String overshot(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-forwarded-for");
		
		if (StringUtils.isBlank(ipAddress)|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipAddress)|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
        if (StringUtils.isBlank(ipAddress)|| "unknown".equalsIgnoreCase(ipAddress)) {  
        	ipAddress = request.getHeader("HTTP_CLIENT_IP");  
        }
        if (StringUtils.isBlank(ipAddress)|| "unknown".equalsIgnoreCase(ipAddress)) {  
        	ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }
		if (StringUtils.isBlank(ipAddress)|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("X-real-ip");// 自定义的变量名
		}
		if (StringUtils.isBlank(ipAddress)|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if ("127.0.0.1".equals(ipAddress)||"0:0:0:0:0:0:0:1".equals(ipAddress)) {
				// 根据网卡取本机配置的IP
				ipAddress = getServerIp();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
			// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}	

}