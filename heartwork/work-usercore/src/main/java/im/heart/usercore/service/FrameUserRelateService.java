package im.heart.usercore.service;

import java.math.BigInteger;
import java.util.List;

import im.heart.core.service.CommonService;
import im.heart.usercore.entity.FrameUserRelate;
/**
 * 
 * @author gg
 * @desc : 用户关系表    Service
 */
public interface   FrameUserRelateService extends CommonService<FrameUserRelate, BigInteger>{
	
	public static final String BEAN_NAME = "frameUserRelateService";
	/**
	 * 
	 * @desc：批量保存
	 * @param entities
	 * @return
	 */
	public List<FrameUserRelate>  save(Iterable<FrameUserRelate> entities);
	/**
	 * 
	 * @desc：
	 * @param entitie
	 * @return
	 */
	public FrameUserRelate save(FrameUserRelate entitie);
	/**
	 * @desc： 根据机构号查询关联信息
	 * @param orgId
	 * @return
	 */
	public List<FrameUserRelate> findByOrgId(BigInteger userId);
	
	public List<FrameUserRelate> findByOrgIdAndType(BigInteger userId,String relateType);
	
	public void delete(BigInteger id);
}
