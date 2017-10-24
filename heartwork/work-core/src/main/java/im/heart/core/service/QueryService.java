package im.heart.core.service;

/**
 * 
 * @author gg
 * @desc 查询接口
 * @param <T>
 * @param <ID>
 */
public interface QueryService<T ,ID> extends BaseService<T,ID>  {
	/**
	 * 
	 * @desc：根据Id查询
	 * @param id
	 * @return
	 */
	public 	T findOne(ID id);
}
