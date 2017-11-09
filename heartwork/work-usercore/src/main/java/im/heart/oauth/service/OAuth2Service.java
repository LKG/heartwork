package im.heart.oauth.service;

public interface OAuth2Service {
	
	public static final String BEAN_NAME = "oAuth2Service";
	
	/**
	 * 
	 * @功能说明： 添加 auth code
	 * @param authCode
	 * @param uid
	 */
	public void addAuthCode(String authCode, String uid);

	/**
	 * 
	 * @功能说明：添加accesstoken
	 * @param accessToken
	 * @param username
	 */
	public void addAccessToken(String accessToken, String uid); 

	/**
	 * 
	 * @功能说明： 验证auth code是否有效
	 * @param authCode
	 * @return
	 */
	public boolean checkAuthCode(String authCode); //

	/**
	 * 
	 * @功能说明：验证access token是否有效
	 * @param accessToken
	 * @return
	 */
	public boolean checkAccessToken(String accessToken); // 

	/**
	 * 
	 * @功能说明： 根据auth code获取用户名
	 * @param authCode
	 * @return
	 */
	public String getUsernameByAuthCode(String authCode);//

	/**
	 * 
	 * @功能说明：根据access token获取用户名
	 * @param accessToken
	 * @return
	 */
	public String getUsernameByAccessToken(String accessToken);// 
																
	/**
	 *  
	 * @功能说明：token 过期时间// auth code / access token 过期时间
	 * @return
	 */
	public long getExpireIn();
	/**
	 * 
	 * @功能说明：检查客户端id是否存在
	 * @param clientId
	 * @return
	 */
	public boolean checkClientId(String clientId);
	
	/**
	 * 
	 * @功能说明：检查客户端id及uri 是否正确
	 * @param clientId
	 * @param Uri
	 * @return
	 */
	public boolean checkClientIdAndUri(String clientId,String uri);
	

	/**
	 * 
	 * @功能说明：检查客户端安全KEY是否存在
	 * @param clientSecret
	 * @return
	 */
	public boolean checkClientSecret(String clientSecret);
	
	public boolean checkClientIdAndSecret(String clientId,String clientSecret);
}
