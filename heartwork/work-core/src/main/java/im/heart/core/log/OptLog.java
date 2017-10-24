package im.heart.core.log;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface OptLog {
	/**

	 * 类型
	 * @return
	 */
	public String type();
	
	/**
	 * 操作说明
	 * @return
	 */
	public String detail();
}
