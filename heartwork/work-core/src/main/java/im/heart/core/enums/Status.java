package im.heart.core.enums;

/**
 * 
 * @author gg
 * @desc 常用状态
 */
public enum Status {

	pending(-1, "pending", "未激活"),
	disabled(0, "disabled", "禁用"),
	enabled(1,	"enabled", "正常");
	public String code;
	public int intValue;
	public final String info;

	Status(int intValue, String code, String info) {
		this.code = code;
		this.intValue = intValue;
		this.info = info;
	}
	
	public static Status findByIntValue(int intValue) {
		for (Status status : Status.values()) {
			if (status.intValue == intValue) {
				return status;
			}
		}
		return Status.enabled;
	}
}
