package im.heart.core.support;
import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author gg
 * @desc 将传递进来的 参数 进行BigDecimal类型转换
 */
public class BigDecimalEditorSupport extends PropertyEditorSupport {
	protected static final Logger logger = LoggerFactory.getLogger(BigDecimalEditorSupport.class);
	@Override
	public void setAsText(String value) {
		if (StringUtils.isNotBlank(value)) {
			try {
				setValue(new BigDecimal(value));
			} catch (Exception e) {
				setValue(null);
				logger.error(e.getMessage(), e);
			}
		}
	}
}
