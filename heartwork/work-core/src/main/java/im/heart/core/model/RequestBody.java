
package im.heart.core.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author gg
 * @desc : 客户端请求内容boby model 
 */
public class RequestBody {
	private String action;//请求地址
	private String requuid;//请求uuid
	Map<String, String>  paras = new HashMap<String, String>();
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map<String, String> getParas() {
		return paras;
	}

	public void setParas(Map<String, String> paras) {
		this.paras = paras;
	}
	public void addParas(String key, String value) {
		this.paras.put(key, value);
	}
	public String getRequuid() {
		return requuid;
	}

	public void setRequuid(String requuid) {
		this.requuid = requuid;
	}

}
