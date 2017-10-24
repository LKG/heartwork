package im.heart.usercore.service;

import java.math.BigInteger;
import java.util.List;

import im.heart.core.service.CommonService;
import im.heart.usercore.entity.FrameUserOrg;
/**
 * 
 * @author gg
 * @desc : 用户机构关联接口
 */
public interface   FrameUserOrgService extends CommonService<FrameUserOrg, BigInteger>{
	
	public static final String BEAN_NAME = "frameUserOrgService";
	
	/**
	 * 
	 * @desc：批量保存
	 * @param entities
	 * @return
	 */
	public List<FrameUserOrg>  save(Iterable<FrameUserOrg> entities);
	/**
	 * 
	 * @desc：
	 * @param entitie
	 * @return
	 */
	public FrameUserOrg save(FrameUserOrg entitie);
	public void delete(BigInteger id);
	
	public void updateUserDefaultOrg(BigInteger userId, BigInteger relateId, BigInteger defaultOrgId);
	
	public void setDefaultOrg(BigInteger userId, BigInteger relateId, BigInteger defaultOrgId);
	
	public boolean existsUserOrg(BigInteger userId);
	
	public List<FrameUserOrg> findByUserId(BigInteger userId);
	
	public List<FrameUserOrg> findByOrgId(BigInteger orgId);
}
