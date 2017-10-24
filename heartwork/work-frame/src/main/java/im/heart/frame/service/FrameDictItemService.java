package im.heart.frame.service;

import java.math.BigInteger;
import java.util.List;

import im.heart.core.service.CommonService;
import im.heart.frame.entity.FrameDictItem;

/**
 * 
 * @author gg
 * @desc  数据字典子表对外接口
 */
public interface FrameDictItemService extends CommonService<FrameDictItem, BigInteger>{
	public static final String BEAN_NAME = "frameDictItemService";

	public List<FrameDictItem> findByDictCode(String dictCode);
	
	public List<FrameDictItem> findByDictId(BigInteger dictId);

	public void delete(BigInteger id);
}
