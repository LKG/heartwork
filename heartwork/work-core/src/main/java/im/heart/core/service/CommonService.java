package im.heart.core.service;

/**
 * 
 * @author gg
 * @desc 公用数据接口，提供id查询，分页查询 数据个数统计，数据列表返回
 * @param <T>
 * @param <ID>
 */
public interface CommonService<T ,ID> extends QueryService<T,ID>,QuerySpecificationService<T,ID>,CrudService<T,ID>{
	
	
}
