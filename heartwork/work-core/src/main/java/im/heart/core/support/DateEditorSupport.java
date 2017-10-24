package im.heart.core.support;


import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author gg
 * @desc ：将传递进来的 参数 进行处理日期格式类型转换 可以自行添加支持的日期，从左到右匹配格式 精确最大的请写在最前面
 */
public class DateEditorSupport extends PropertyEditorSupport {
	
	protected static final Logger logger = LoggerFactory.getLogger(DateEditorSupport.class);
	@Override
	public void setAsText(String value) {
		if(StringUtils.isNotBlank(value)){
			try {
				 Date val = DateUtils.parseDateStrictly(value, 
						 "yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm","yyyy-MM-dd HH","yyyy-MM-dd","yyyy-MM","yyyy");
				setValue(val);
			} catch (ParseException e) {
				setValue(null);
				logger.error(e.getMessage(), e);
			}
		}
	}
}
