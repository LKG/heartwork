package im.heart.core.model;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 
 * @author gg
 * @desc : 对外接口统一参数 model
 */
public class RequestParas {
	@JSONField(name="CP_PRT")
	private String cpPrt; // 产品号
	@JSONField(name="CP_UID")
	private String cpUid; // 系统唯一用户ID，标记用户身份
	@JSONField(name="CP_PLTFM")
	private String cpPltfm; // 软件平台(标准字符串，参考接入产品列表，全部采用小写)
	@JSONField(name="CP_TOUCH")
	private String cpTouch; // 是否触摸(1触摸0非触摸)
	@JSONField(name="CP_CITYID")
	private String cpCityId; // 城市编号(公司统一城市ID)
	@JSONField(name="CP_IMEI")
	private String cpImei; // 用户手机IMEI(最长不超过64位)
	@JSONField(name="CP_CH")
	private String cpCh; // 渠道号
	@JSONField(name="CP_MODEL")
	private String cpModel; // 手机机型(不要包含空格，如：nokia_n97、iphone4、htc_g7)
	@JSONField(name="CP_RATIO")
	private String cpRatio; // 手机屏幕分辨率，宽度乘以高度，例240*320
	@JSONField(name="CP_TPL")
	private String cpTpl; // 模板名
	@JSONField(name="CP_VER")
	private String cpVer; // 软件版本(标准的三位版本号如：2.2.2、2.3.0)
	@JSONField(name="CP_RESVER")
	private String cpResver; // 资源版本号
	@JSONField(name="CP_PHONENUM")
	private String cpPhoneNum; // 用户电话号码
	@JSONField(name="CP_PUBRESPATH")
	private String cpPubResPath; // 公共资源路径
	@JSONField(name="CP_LON")
	private String cpLon; // 经度
	@JSONField(name="CP_LAT")
	private String cpLat; // 纬度
	private List<RequestBody> actions; // 处理请求体

	
	public String getCpPrt() {
		return cpPrt;
	}
	public void setCpPrt(String cpPrt) {
		this.cpPrt = cpPrt;
	}
	
	public String getCpUid() {
		return cpUid;
	}
	public void setCpUid(String cpUid) {
		this.cpUid = cpUid;
	}

	public String getCpPltfm() {
		return cpPltfm;
	}

	public void setCpPltfm(String cpPltfm) {
		this.cpPltfm = cpPltfm;
	}

	public String getCpTouch() {
		return cpTouch;
	}

	public void setCpTouch(String cpTouch) {
		this.cpTouch = cpTouch;
	}

	public String getCpCityId() {
		return cpCityId;
	}
	public void setCpCityId(String cpCityId) {
		this.cpCityId = cpCityId;
	}
	public String getCpImei() {
		return cpImei;
	}
	
	public void setCpImei(String cpImei) {
		this.cpImei = cpImei;
	}

	public String getCpCh() {
		return cpCh;
	}

	public void setCpCh(String cpCh) {
		this.cpCh = cpCh;
	}
	
	public String getCpModel() {
		return cpModel;
	}
	public void setCpModel(String cpModel) {
		this.cpModel = cpModel;
	}

	public String getCpRatio() {
		return cpRatio;
	}
	public void setCpRatio(String cpRatio) {
		this.cpRatio = cpRatio;
	}

	public String getCpTpl() {
		return cpTpl;
	}
	public void setCpTpl(String cpTpl) {
		this.cpTpl = cpTpl;
	}

	public String getCpVer() {
		return cpVer;
	}
	
	public void setCpVer(String cpVer) {
		this.cpVer = cpVer;
	}
	public String getCpResver() {
		return cpResver;
	}
	public void setCpResver(String cpResver) {
		this.cpResver = cpResver;
	}
	public String getCpPhoneNum() {
		return cpPhoneNum;
	}
	public void setCpPhoneNum(String cpPhoneNum) {
		this.cpPhoneNum = cpPhoneNum;
	}
	public String getCpPubResPath() {
		return cpPubResPath;
	}
	
	public void setCpPubResPath(String cpPubResPath) {
		this.cpPubResPath = cpPubResPath;
	}
	public String getCpLon() {
		return cpLon;
	}
	public void setCpLon(String cpLon) {
		this.cpLon = cpLon;
	}
	public String getCpLat() {
		return cpLat;
	}
	public void setCpLat(String cpLat) {
		this.cpLat = cpLat;
	}
	public List<RequestBody> getActions() {
		return actions;
	}

	public void setActions(List<RequestBody> actions) {
		this.actions = actions;
	}
}
