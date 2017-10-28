package im.heart.log.vo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;

import im.heart.core.plugins.ip.IPInfo;
import im.heart.log.entity.FrameLogLogin;


public class FrameUserLoginLogVO extends FrameLogLogin {

	private static final long serialVersionUID = 4682427064421749044L;

	private IPInfo ipInfo;// 根据IP 信息

	public FrameUserLoginLogVO(FrameLogLogin po) {
		BeanUtils.copyProperties(po, this);
		if (StringUtils.isNotBlank(po.getUserIpInfo())) {
			ipInfo = JSON.parseObject(po.getUserIpInfo(), IPInfo.class);
		}
	}

	public IPInfo getIpInfo() {
		return ipInfo;
	}

	public void setIpInfo(IPInfo ipInfo) {
		this.ipInfo = ipInfo;
	}

}
