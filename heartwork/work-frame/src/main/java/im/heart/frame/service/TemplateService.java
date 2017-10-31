package im.heart.frame.service;



import java.io.Serializable;


public interface TemplateService<T, ID extends Serializable> {



	/**
	 * 读取模板文件内容
	 * 
	 * @param id     ID
	 * @return 模板文件内容
	 */
	String read(ID id);

	/**
	 * 读取模板文件内容
	 * 
	 * @param template    模板
	 * @return 模板文件内容
	 */
	String read(T template);

	/**
	 * 写入模板文件内容
	 * @param id  Id
	 * @param content 模板文件内容
	 */
	void write(ID id, String content);

	/**
	 * 写入模板文件内容
	 * 
	 * @param template   模板
	 * @param content   模板文件内容
	 */
	void write(T template, String content);

}
