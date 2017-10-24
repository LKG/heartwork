package im.heart.core.entity;

import java.io.Serializable;

/**
 * 
 * @author gg
 * @desc 树形节点接口 标示需要实现树形结构
 * @param <ID>
 */
public interface TreeEntity<ID extends Serializable> extends AbstractEntity<ID> {

	/**
	 * 是否有孩子节点
	 *
	 * @return
	 */
	public boolean isHasChildren();

	/**
	 * 
	 * @desc：是否为根节点
	 * @return
	 */
	public boolean isRoot();
	
	
	/**
	 * 
	 * @desc：是否为叶子节点
	 * @return
	 */
    public boolean isLeaf();
}
