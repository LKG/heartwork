package im.heart.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author gg
 * @desc 验证工具类
 */
public class ValidatorUtils implements Regular{
	protected static final Logger logger = LoggerFactory.getLogger(ValidatorUtils.class);
	/**
	 * 
	 * @Desc： 判断是不是一个合法的电子邮件地址
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (StringUtilsEx.isBlank(email)){
			return false;
		}
		return emailer.matcher(email).matches();
	}
	public static boolean isNumber(String numberstr) {
		if (StringUtilsEx.isBlank(numberstr)){
			return false;
		}
		return number.matcher(numberstr).matches();
	}
	/**
	 * 
	 * @Desc：判断是否包含中文
	 * @param str
	 * @return
	 */
	public static boolean isContainsChinese(String str) {
		if (StringUtilsEx.isBlank(str)){
			return false;
		}
		return chineser.matcher(str).find();
	}
	/**
	 * 
	 * @Desc：判断是否为日期格式
	 * @param str
	 * @return
	 */
	public static boolean isDatetime(String str) {
		if (StringUtilsEx.isBlank(str)){
			return false;
		}
		return datetimeer.matcher(str).matches();
	}
	
	/**
	 * 
	 * @Desc：判断是否为电话号码
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		if (StringUtilsEx.isBlank(phone)){
			return false;
		}
		return phoner.matcher(phone).matches();
	}
	/**
	 * @Desc：判断是否为IP地址
	 * @param ip
	 * @return
	 */
	 public static Boolean isIpAddress(String ip){
		 if (StringUtilsEx.isBlank(ip)){
				return false;
		 }
          return iper.matcher(ip).matches();
	 }
	 /**
	  * 
	  * @Desc：判断是否为url
	  * @param url
	  * @return
	  */
	 public static Boolean isUrl(String url){
		 if (StringUtilsEx.isBlank(url)){
				return false;
		 }
          return iper.matcher(url).matches();
	 }
	 
	 /**
	  * 
	  * @Desc：判断是否为身份证号码
	  * @param idCard
	  * @return
	  */
	 public static Boolean isIdCard(String idCard){
		 if (StringUtilsEx.isBlank(idCard)){
				return false;
		 }
          return idCarder.matcher(idCard).matches();
	 }
	
}
