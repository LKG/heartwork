package im.heart.core.forbidden;

import im.heart.core.utils.StringUtilsEx;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 
 * @author: gg
 * @desc :
 */
public class ForbiddenValidator implements 	ConstraintValidator<Forbidden, String> {
	private String[] forbiddenWords = { "admin" };

	@Override
	public void initialize(Forbidden constraintAnnotation) {
		// TODO Auto-generated method stub
		// 初始化，得到注解数据
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtilsEx.isBlank(value)) {
			return true;
		}
		for (String word : forbiddenWords) {
			if (value.contains(word)) {
				return false;// 验证失败
			}
		}
		return true;
	}

}
