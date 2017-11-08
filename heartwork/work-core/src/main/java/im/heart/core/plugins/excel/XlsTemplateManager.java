package im.heart.core.plugins.excel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.ResourceUtils;

public class XlsTemplateManager {
	private int maxSheet = 200; // 总大小
	private String path;
	private String suffix = ".xls";
	protected static final String DEFAULT_PARANAME = "lists";
	private String paraName=DEFAULT_PARANAME;
	
	public Workbook genWorkbook(String templateName,List<?> dataList) throws Exception {
		return genWorkbook(templateName ,paraName,dataList);
	}
	
	public Workbook genWorkbook(String templateName, String paraName,
			List<?> dataList) throws Exception {
		try {
			Map<String, List<?>> beans = new HashMap<String, List<?>>();
			beans.put(paraName, dataList);

			XLSTransformer transformer = new XLSTransformer();
			String templatePath = path + "/" + templateName + suffix;
			File templateFile = ResourceUtils.getFile(templatePath);
			InputStream isPara = new BufferedInputStream(new FileInputStream(templateFile));
			Workbook workbook = transformer.transformXLS(
					isPara, beans);
			return workbook;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Workbook genWorkbook(String templateName, String paraName,
			Map<?, ?> beans) throws Exception {
		try {
			String templatePath = path + "/" + templateName + suffix;
			File templateFile = ResourceUtils.getFile(templatePath);
			InputStream isPara = new BufferedInputStream(new FileInputStream(templateFile));
			XLSTransformer transformer = new XLSTransformer();
			List<String> sheetNames = new ArrayList<String>();
			List<Object> dataList = new ArrayList<Object>();
			Set<?> set = beans.keySet();
			Iterator<?> it = set.iterator();
			while (it.hasNext()) {
				String sheetName = (String) it.next();
				int length = sheetNames.size();
				if (length >= maxSheet) {
					sheetName = sheetName + ",More than " + maxSheet + " sheet";
					sheetNames.add(sheetName);
					dataList.add(beans.get(sheetName));
					break;
				}
				sheetNames.add(sheetName);
				dataList.add(beans.get(sheetName));
			}
			Workbook workbook =transformer.transformMultipleSheetsList(isPara, dataList, sheetNames,
							paraName, new HashMap<String, Object>(), 0);
			return workbook;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Workbook genWorkbook(String templateName, String paraName,
			String sheetName, Object obj) throws Exception {
		try {
			Map<String, Object> beans = new HashMap<String, Object>();
			beans.put(sheetName, obj);
			return genWorkbook(templateName, paraName, beans);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public int getMaxSheet() {
		return maxSheet;
	}

	public void setMaxSheet(int maxSheet) {
		this.maxSheet = maxSheet;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

}
