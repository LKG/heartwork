package im.heart.core.model;
/**
 * 
 * @author gg
 * @desc :请求明细 model
 */
public class RequestDetail {
	private String compressmethod; // 压缩方法
	private String shopid;

	// ---------------------------------
	private String version; // 本地资源版本
	private String pid; // 应用的产品号

	private String lat; // 纬度
	private String lon; // 经度
	private String zoom;// 缩放级别

	private String id; // 要请求的资源的ID
	private String refClass; // 资源关联的类型

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRefClass() {
		return refClass;
	}

	public void setRefClass(String refClass) {
		this.refClass = refClass;
	}

	public String getCompressmethod() {
		return compressmethod;
	}

	public void setCompressmethod(String compressmethod) {
		this.compressmethod = compressmethod;
	}

	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getZoom() {
		return zoom;
	}

	public void setZoom(String zoom) {
		this.zoom = zoom;
	}

}
