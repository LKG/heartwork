package im.heart.core.plugins.excel;



public enum ExcelDataType {

    csv("CSV"),
    excel2003("Excel 2003"),
    excel2003_sheet("Excel 2003 one sheet per workbook"),
    excel2003_xml("Excel 2003 XML存储"),
    excel2003_usermodel("Excel 2003 usermodel模型"),
    excel2007("Excel 2007");

    private final String info;

    private ExcelDataType(final String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
