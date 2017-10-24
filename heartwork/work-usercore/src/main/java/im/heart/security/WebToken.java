package im.heart.security;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author gg
 * @desc  登录成功返回model
 */
public class WebToken {
	
	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}
	public WebToken() {
	}
	public WebToken(String token,long expires) {
		this.token = token;
		this.expires = expires;
	}
	@JSONField(name="refresh_token")
	private String refreshToken;

	@JSONField(name="access_token")
	private String token;
	
	@JSONField(name="expires_in")
	private long expires;

	@JSONField(name="scope")
	private String scope;
	
	@JSONField(name="uid")
	private String uid;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
