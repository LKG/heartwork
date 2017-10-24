package im.heart.frame.vo;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.annotation.JSONField;

import im.heart.core.enums.Status;
import im.heart.frame.entity.FrameArea;

public class FrameAreaVO extends FrameArea  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7312464156457586626L;
	public FrameAreaVO(FrameArea po){
		BeanUtils.copyProperties(po, this);
	}
	@JSONField (serialize=false)
	private Status status;
}
