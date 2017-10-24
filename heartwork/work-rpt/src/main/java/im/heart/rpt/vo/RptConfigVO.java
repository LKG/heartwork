package im.heart.rpt.vo;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.annotation.JSONField;

import im.heart.rpt.entity.RptConfig;

public class RptConfigVO extends RptConfig {
	/**
	 * 
	 */
	private static final long serialVersionUID = -478952244524291815L;

	public RptConfigVO(RptConfig po) {
		BeanUtils.copyProperties(po, this);
	}
	@JSONField(serialize = false)
	private String rptCriCont;

	public String getRptCriCont() {
		return rptCriCont;
	}

	public void setRptCriCont(String rptCriCont) {
		this.rptCriCont = rptCriCont;
	}

}
