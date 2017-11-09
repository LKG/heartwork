package im.heart.oauth.service;

import im.heart.core.service.CommonService;
import im.heart.oauth.entity.FrameClient;

import java.math.BigInteger;


/**
 * 
 * @author gg
 * @desc oauth2客户端接口
 */
public interface FrameClientService  extends CommonService<FrameClient,BigInteger>{
	public static final String BEAN_NAME = "frameClientService";
	/**
	 * 
	 * @功能说明：根据ClientId 查询Client 信息
	 * @param clientId
	 * @return
	 */
	public FrameClient queryClientByClientId(String clientId);
	
	
	/**
	 * 
	 * @功能说明：据clientSecret 查询Client 信息
	 * @param clientSecret
	 * @return
	 */
	public FrameClient queryClientBySecret(String clientSecret);
	
	/**
	 * 
	 * @功能说明：检测客户端是否合法
	 * @param clientId
	 * @return
	 */
	public boolean checkClientId(String clientId);
	
	/**
	 * 
	 * @功能说明：检测客户端是否合法
	 * @param clientId
	 * @param uri
	 * @return
	 */
	public boolean checkClientIdAndUri(String clientId, String uri);
	/**
	 * 
	 * @功能说明：检测客户端是否合法
	 * @param clientId
	 * @param clientSecret
	 * @return
	 */
	public boolean checkClientIdAndSecret(String clientId, String clientSecret);
}
