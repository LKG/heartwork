package im.heart.core.enums;

public enum FileHeader {
	avi("avi", "41564920"),
	bat("bat", "406563686f206f66660d"),
	jar("jar", "504B03040A0000000000"),
	zip("zip", "504B0304"), // zip头格式
	jpeg("jpeg", "FFD8FF"), // JPEG头格式
	png("png", "89504E47"), // PNG头格式
	bmp("bmp", "424D"),// BMP头格式
	gif("gif", "47494638"), // GIF头格式
	tif("tif", "49492A00"),// tif头格式
	dwg("dwg", "41433130"),// dwg头格式 CAD
	chm("chm", "49545346030000006000"),
	classz("class", "CAFEBABE0000002E0041"),
	dbx("dbx", "CFAD12FEC5FD746F"),// Outlook Express (dbx)
	rmvb("rmvb", "2E524D46000000120001"),// rtf头格式  // 日记本  
	rtf("rtf", "7B5C727466"),// rtf头格式  // 日记本  
	rar("rar", "52617221"), // rar头格式
	ram("ram", "2E7261FD"), // ram头格式 Real Audio (ram)  
	pdf("pdf", "255044462D312E"), // pdf头格式
	txt("txt", "75736167"), // txt头格式
	exe("exe", "4D5A9000"), // exe头格式
	qdf("qdf", "AC9EBD8F"), ////Quicken (qdf)  
	eml("eml", "44656C69766572792D646174653A"), // exe头格式
	dll("dll", "4D5A9000"), // dll头格式
	docx("docx", "504B0304"), // docx头格式
	doc("doc", "D0CF11E0"), // doc头格式 ("d0cf11e0a1b11ae10000", "doc"); //MS Excel 注意：word、msi 和 excel的文件头一样     
	xls("xls", "D0CF11E0"), // xls头格式
	gz("gz", "1F8B08"), // gz头格式
	mid("mid", "4D546864"), // mid头格式
	mdb("mdb", "5374616E64617264204A"), // mdb头格式 MS Access 
	mp3("mp3", "49443303000000002176"), // mdb头格式 MS Access 
	mp4("mp4", "00000020667479706d70"), // mdb头格式 MS Access 
	mov("mov", "6D6F6F76"), //Quicktime (mov)     
	ps("ps", "252150532D41646F6265"),
	pwl("pwl", "E3828596"),
	xml("xml", "3C3F786D6C"),
	html("html", "68746D6C3E"),
	pst("pst", "2142444E"),
	wav("wav", "57415645"),
	wpd("wpd", "FF575043"),
	wps("wps", "D0CF11E0A1B11AE10000"), ////WPS文字wps、表格et、演示dps都是一样的
	mxp("mxp", "04000000010000001300"),
	torrent("torrent", "6431303A637265617465"),
	vsd("vsd", "D0CF11E0A1B11AE10000"),
	psd("psd", "38425053"); // psd头格式

	private String code;
	private String value;

	private FileHeader(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}