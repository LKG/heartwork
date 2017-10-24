package im.heart.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author gg
 * @desc commons.lang.StringUtils 工具扩展类
 */
public class StringUtilsEx extends StringUtils {

	protected static final Logger logger = LoggerFactory.getLogger(StringUtilsEx.class);

	public static final String DEFAULT_REPLACE = "***";
	/**
	 * 过滤特殊字符 .不移除
	 */
	public final static String regEx = "[`~!@#$%^&*()+=|{}':;'-,\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
	//"^*\\d+$"
	/**
	 * 
	 * @Desc：根据map key ,value 替换字符串 默认不忽略大小写
	 * @param template
	 * @param tokens
	 * @return
	 */
	public static String  replace(String template,Map<String,String> tokens){
		return replace(template,tokens,false,true);//默认不忽略大小写 ,默认左右加空格
	}
	
	
	/**
	 * 
	 * @Desc：根据map key ,value 替换字符串 
	 * @param template
	 * @param tokens
	 * @param ignoreCase 匹配key 是否忽略大小写
	 * @return
	 */
	public static String  replace(String template,Map<String,String> tokens,boolean isIgnoreCase,boolean isIgnoreSpace){
		
		if(isBlank(template)){
			return null;
		}
		if(tokens==null||tokens.isEmpty()){
			return template;
		}
		StringBuffer sb = new StringBuffer();
		String separator="\\s|\\s";
		if(isIgnoreSpace){
			separator="|";
		}
		String wordReg = StringUtils.join(tokens.keySet(), separator);//用(?i)来忽略大小写  
		if(isIgnoreCase){
			wordReg="(?i)"+wordReg;
		}
		if(!isIgnoreSpace){
			wordReg+="\\s";
		}
		
		Pattern pattern = Pattern.compile(wordReg);
		Matcher matcher = pattern.matcher(template);
		while(matcher.find()) {
			String key=matcher.group().trim();
			if(isIgnoreSpace){
				key=key.trim();
			}
			if(isIgnoreCase){
				key=key.toLowerCase();
			}
		    matcher.appendReplacement(sb, tokens.get(key));
		}
		matcher.appendTail(sb);   
		return sb.toString();
	}
	
	/**
	 * 将一个InputStream流转换成字符串
	 * 
	 * @param is
	 * @return
	 */
	public static String toConvertString(InputStream is) {
		StringBuffer res = new StringBuffer();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader read = new BufferedReader(isr);
		try {
			String line;
			line = read.readLine();
			while (line != null) {
				res.append(line);
				line = read.readLine();
			}
		} catch (IOException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		} finally {
			IOUtils.closeQuietly(isr);
			IOUtils.closeQuietly(read);
			IOUtils.closeQuietly(is);
		}
		return res.toString();
	}
	/**
	 * 
	 * @Desc：过滤字符串
	 * @param filterStr
	 * @return
	 */
	public static String filterString(String   filterStr){   
          return   filterString(filterStr,regEx);     
	}
	/**
	 * 
	 * @Desc：
	 * @param str
	 * @return
	 */
	public static BigDecimal convertToBigDecimal(String str) {
		if (StringUtilsEx.isBlank(str)) {
			return new BigDecimal("0");
		}
		return new BigDecimal(str.trim());
	}
	/**
	 * 
	 * @Desc：按照指定正则过滤
	 * @param filterStr
	 * @param regEx
	 * @return
	 */
	public static String filterString(String   filterStr,String regEx){
		  Pattern   pattern   =   Pattern.compile(regEx);     
          Matcher   matcher   =   pattern.matcher(filterStr);     
          return   matcher.replaceAll("").trim();     
	  }
	/**
	 * 
	 * @Desc：过滤出数值型字符串
	 * @param str
	 * @return
	 */
	public static String groupNumerialStr(String str) {
		String patternString = "^*\\d+\\.?\\d*|\\.\\d+$";
		return groupString(str, patternString);
	}
	/**
	 * 
	 * @Desc：根据正则提取数据
	 * @param filterStr
	 * @param regEx
	 * @return
	 */
	  public static String groupString(String   filterStr,String regEx){
			return groupString(filterStr,regEx,0);    
	  }
	  /**
	   * 
	   * @Desc：
	   * @param filterStr
	   * @param regEx
	   * @param group
	   * @return
	   */
	  public static String groupString(String   filterStr,String regEx,int group){
		  Pattern pattern = Pattern.compile(regEx);
			Matcher matcher = pattern.matcher(filterStr);
			while (matcher.find()) {
				return matcher.group(group);
			}
			return null;    
	  }
	/**
	 * 
	 * @Desc：判断字符串是否为空串
	 * @param chkstr
	 * @return
	 */
	public static boolean isNotEmpty(String chkstr) {
		if (chkstr != null && chkstr.trim().length() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、 换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input)){
			return true;
		}
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}
	public static String maskingStrBetween(int beginIndex,int endIndex,String content,String seq) {
		 String temp=substring(content, beginIndex, endIndex);
	     return content.replace(temp,seq);  
	}
	public static String maskingStrBe(int beginIndex,int endIndex,String content) {
	     return maskingStrBetween(beginIndex,endIndex,content,DEFAULT_REPLACE);
	}
	
	/**
	 * 10进制转换16进制
	 * 
	 * @param length
	 * @return
	 */
	public static String changebytelength(int length) {
		String tentosix = Integer.toHexString(length);
		if (tentosix.length() > 7){
			return tentosix;
		}else{
			return someZero(8 - tentosix.length()) + tentosix;
		}
	}
	/**
	 * 补充位数
	 * 
	 * @param key
	 * @return
	 */
	public static String someZero(int key) {
		String str = "";
		for (int i = 0; i < key; i++) {
			str += "0";
		}
		return str;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static String getUUID2() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
