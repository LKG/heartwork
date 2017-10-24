package im.heart.usercore.service;

import java.math.BigInteger;
import java.util.List;

import im.heart.core.service.CommonService;
import im.heart.usercore.entity.FrameOrgRelate;
/**
 * 
 * @author gg
 * @desc 机构关联表，设置机构非直属关联关系   Service
 */
public interface   FrameOrgRelateService extends CommonService<FrameOrgRelate, BigInteger>{
	
	public static final String BEAN_NAME = "frameOrgRelateService";
	/**
	 * 
	 * @Desc：批量保存
	 * @param entities
	 * @return
	 */
	public List<FrameOrgRelate>  save(Iterable<FrameOrgRelate> entities);
	/**
	 * 
	 * @Desc：
	 * @param entitie
	 * @return
	 */
	public FrameOrgRelate save(FrameOrgRelate entitie);
	/**
	 * @Desc： 根据机构号查询关联信息
	 * @param orgId
	 * @return
	 */
	public List<FrameOrgRelate> findByOrgId(BigInteger orgId);
	
	public List<FrameOrgRelate> findByOrgIdAndType(BigInteger orgId,String relateType);
	
	public void delete(BigInteger id);
}
