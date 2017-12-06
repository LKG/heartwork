package im.heart.core.enums;

public enum DeviceType {

	computer("computer"," Standard desktop or laptop computer"),

	mobile("mobile","Mobile phone or similar small mobile device"),
	
	tablet("Tablet","Small tablet type computer"),

	console("game_console","Game console like the Wii or Playstation"),

	dmr("dmr","Digital media receiver like the Google TV"),

	wearable("wearable","Miniature device like a smart watch or interactive glasses"),

	unknown("unknown","unknown");

	private String name;

	private String desc;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private DeviceType(String name) {
		this.name = name;
	}
	private DeviceType(String name,String desc) {
		this(name);
		this.desc = desc;
	}
}
