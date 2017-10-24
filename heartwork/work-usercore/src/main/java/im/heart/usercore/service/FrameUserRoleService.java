package im.heart.usercore.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import im.heart.core.service.CommonService;
import im.heart.core.service.ServiceException;
import im.heart.usercore.entity.FrameUserRole;
/**
 * 
 * @author gg
 * @desc : 用户角色接口
 */
public interface   FrameUserRoleService extends CommonService<FrameUserRole, BigInteger>{
	
	public static final String BEAN_NAME = "frameUserRoleService";
	
	
	/**
	 * 
	 * @desc：批量保存
	 * @param entities
	 * @return
	 */
	public List<FrameUserRole>  save(Iterable<FrameUserRole> entities);
	/**
	 * 
	 * @desc：
	 * @param entitie
	 * @return
	 */
	public FrameUserRole save(FrameUserRole entitie);
	
	public void saveUserRole(BigInteger userId,String ... roleCodes) throws ServiceException;
	
	public Map<String, BigInteger> findRoleCodeMapByUserId(BigInteger userId);
}
