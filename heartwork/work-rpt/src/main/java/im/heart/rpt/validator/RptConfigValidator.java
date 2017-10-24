package im.heart.rpt.validator;

import im.heart.rpt.entity.RptConfig;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
/**
 * 
 * @author: gg
 * @desc :数据校验
 */
@Component
public class RptConfigValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return RptConfig.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub

	}

}
