package im.heart.core.utils;

import java.util.HashSet;
import java.util.Set;
/**
 * 
 * @author gg
 * @desc : string 转换为boolan 摘自  org.springframework.core.convert.converter.Converter.StringToBooleanConverter
 */
public final class StringToBooleanUtils {
	private static final Set<String> trueValues = new HashSet<String>(4);

	private static final Set<String> falseValues = new HashSet<String>(4);

	static {
		trueValues.add("true");
		trueValues.add("on");
		trueValues.add("yes");
		trueValues.add("1");

		falseValues.add("false");
		falseValues.add("off");
		falseValues.add("no");
		falseValues.add("0");
	}

	public static Boolean convert(String source) {
		String value = source.trim();
		if ("".equals(value)) {
			return null;
		}
		value = value.toLowerCase();
		if (trueValues.contains(value)) {
			return Boolean.TRUE;
		} else if (falseValues.contains(value)) {
			return Boolean.FALSE;
		} else {
			throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
		}
	}
}
