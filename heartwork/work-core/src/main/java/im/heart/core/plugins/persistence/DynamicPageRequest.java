package im.heart.core.plugins.persistence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * 
 * @author gg
 * @desc 处理分页请求
 */
public class DynamicPageRequest {
	protected static final Logger logger = LoggerFactory.getLogger(DynamicPageRequest.class);
	/**
	 * 
	 * @Desc：过滤无效排序条件
	 * @param entityClazz
	 * @param sortFieldNames
	 * @return
	 */
	private static <T> List<String>  getSortFilterList(final Class<T> entityClazz,String[] sortFieldNames){
		List<String> list = new  ArrayList<String>();
		Map<String, String> fieldMap=new HashMap<String, String>();
		Field[] fields = entityClazz.getDeclaredFields();
		for(Field field:fields){
			fieldMap.put(field.getName(), field.getName());
		}
		for(String fieldName:sortFieldNames){
			if(fieldMap.containsKey(fieldName)){
				list.add(fieldName);
				continue;
			}
			logger.debug(fieldName + " is not a valid sort filter name");
		}
		return list;
	}

	
	/**
	 * 
	 * @Desc：创建分页请求
	 * @param pageNumber
	 * @param pagzSize
	 * @param sortField
	 * @param order
	 * @return
	 */
	public static <T> PageRequest buildPageRequest(int pageNumber, int pagzSize,String sortField,String order,final Class<T> entityClazz) {
		String[] sortFieldNames = StringUtils.split(sortField, ",");
		if(sortFieldNames!=null){
			if(sortFieldNames.length>20){//排序条件过多
				throw new IllegalArgumentException(sortFieldNames + " is too more... ");
			}
			List<String> list = getSortFilterList(entityClazz,sortFieldNames);
			if(list!=null&!list.isEmpty()){
				Direction direction=Direction.fromStringOrNull(order);
				if(direction==null){
					direction=Sort.DEFAULT_DIRECTION;
				}
				return new PageRequest(pageNumber - 1, pagzSize, new Sort(direction,list));
			}
		}
		return new PageRequest(pageNumber - 1, pagzSize);
	}
}