package im.heart.core.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;


public class BeanValidationUtils {
	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	/**
	 * @Desc 验证Entity
	 * @param obj
	 * @return
	 */
	public static <T> ValidationResult validateEntity(T obj) {
		ValidationResult result = new ValidationResult();
		Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
		if (set!=null&&!set.isEmpty()) {
			result.setHasErrors(true);
			Map<String, String> errorMsg = new HashMap<String, String>();
			for (ConstraintViolation<T> cv : set) {
				errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
			}
			result.setErrorMsg(errorMsg);
		}
		return result;
	}
	/**
	 * @Desc 验证属性
	 * @param obj
	 * @param propertyName
	 * @return
	 */
	public static <T> ValidationResult validateProperty(T obj,String propertyName) {
		ValidationResult result = new ValidationResult();
		Set<ConstraintViolation<T>> set = validator.validateProperty(obj,propertyName, Default.class);
		if (set!=null&&!set.isEmpty()) {
			result.setHasErrors(true);
			Map<String, String> errorMsg = new HashMap<String, String>();
			for (ConstraintViolation<T> cv : set) {
				errorMsg.put(propertyName, cv.getMessage());
			}
			result.setErrorMsg(errorMsg);
		}
		return result;
	}
}
