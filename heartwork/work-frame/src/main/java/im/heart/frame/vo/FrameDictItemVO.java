package im.heart.frame.vo;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.annotation.JSONField;
import im.heart.frame.entity.FrameDictItem;

public class FrameDictItemVO extends FrameDictItem{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 8201192106902318147L;
	@JSONField(serialize=false)
	private String itemDesc;
	public FrameDictItemVO(FrameDictItem po) {
		BeanUtils.copyProperties(po, this);
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

}
