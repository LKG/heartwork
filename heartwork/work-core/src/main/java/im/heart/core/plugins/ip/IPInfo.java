package im.heart.core.plugins.ip;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author lkg
 * @desc IP model 类
 */
public class IPInfo {
	private String country;
	private String country_id;
	private String area;
	private String area_id;
	private String region;
	private String region_id;
	private String county;
	private String county_id;
	private String isp;
	private String isp_id;
	private String ip;

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry_id() {
		return this.country_id;
	}

	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getArea_id() {
		return this.area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegion_id() {
		return this.region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCounty_id() {
		return this.county_id;
	}

	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}

	public String getIsp() {
		return this.isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	public String getIsp_id() {
		return this.isp_id;
	}

	public void setIsp_id(String isp_id) {
		this.isp_id = isp_id;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}