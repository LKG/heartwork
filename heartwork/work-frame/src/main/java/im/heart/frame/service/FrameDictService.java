package im.heart.frame.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import im.heart.core.service.ServiceException;
import im.heart.core.service.CommonService;
import im.heart.frame.entity.FrameDict;
import im.heart.frame.entity.FrameDictItem;

/**
 * 
 * @author gg
 * @desc 字典对外Service 接口
 */
public interface FrameDictService extends CommonService<FrameDict, BigInteger>{
	public static final String BEAN_NAME = "frameDictService";

	/**
	 * @desc：判断是否定义对应字典
	 * @param id
	 * @return
	 */
	public boolean exists(String dictCode);
	
	public List<FrameDictItem> findItemsByCode(String dictCode,String itemCode);
	
	/**
	 * 
	 * @Desc：根据字典类型和字典代号查询数据
	 * @param solidCode
	 * @param itemCode
	 * @return
	 */
	public FrameDictItem findItemByCode(String dictCode,String itemCode);
	
	public Page<FrameDictItem> findItemsByCode(String dictCode, Pageable pageable);
	
	public FrameDict save(FrameDict frameDict);
	public void save(FrameDict frameDict, Iterable<FrameDictItem> dictItems)   throws ServiceException ;

	public void delete(BigInteger id);
	
}
