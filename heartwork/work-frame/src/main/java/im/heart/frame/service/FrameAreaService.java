package im.heart.frame.service;

import im.heart.core.plugins.persistence.SearchFilter;
import im.heart.core.service.CommonService;
import im.heart.frame.entity.FrameArea;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;


/**
 * 
 * @author gg
 * @desc 区域表对外Service 接口
 */
public interface FrameAreaService extends CommonService<FrameArea, BigInteger>{
	public static final String BEAN_NAME = "frameAreaService";
	
	
	public boolean exists(BigInteger id);
	/**
	 * 
	 * @Desc：批量保存
	 * @param entities
	 * @return
	 */
	public List<FrameArea>  save(Iterable<FrameArea> entities);
	
	public FrameArea save(FrameArea frameArea);
	
	public FrameArea findByName(String areaName);
	
	public List<FrameArea> findAreasByName(String areaName);
	public Page<FrameArea> findBySearchFilters(Collection<SearchFilter> filters, PageRequest pageRequest);
	public Page<FrameArea> findBySpecification(Specification<FrameArea> spec, PageRequest pageRequest);
	public void delete(BigInteger id);
}
