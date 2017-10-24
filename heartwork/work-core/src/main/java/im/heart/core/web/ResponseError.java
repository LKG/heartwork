package im.heart.core.web;


import im.heart.core.web.enums.WebError;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author gg
 * @desc 返回错误 model
 */
public class ResponseError{
	@JSONField(name="error")
	private  String name;
	
	@JSONField(name="error_code")
	private  String code;
	@JSONField(name="error_description")
	private  String description;
	
	@JSONField(name="error_url")
	private  String errorUrl;
	
	public ResponseError(WebError webError) {
		this.code =webError.getCode();
		this.name =webError.getName();
		this.description =webError.getDescription();
    }

    public ResponseError(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public ResponseError(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getErrorUrl() {
		return errorUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}

}
