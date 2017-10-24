package im.heart.admin.common.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import im.heart.core.web.AbstractController;
@Controller
@RequestMapping("/admin")
public class AdminIndexController extends AbstractController {
	protected static final String apiVer = "/index";
	protected static final String VIEW="admin/index";
	
	@RequiresRoles(value="admin")
	@RequestMapping(value={"","/",apiVer,apiVer+"/"})
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response,
			ModelMap model){
		super.success(model);
		return new ModelAndView(VIEW);
	}
	@RequestMapping("/device")
    public ModelAndView detectDevice(Device device,ModelMap model) {
        String deviceType = "unknown";
        if (device.isNormal()) {
            deviceType = "normal";
        } else if (device.isMobile()) {
            deviceType = "mobile";
        } else if (device.isTablet()) {
            deviceType = "tablet";
        }
    	super.success(model,deviceType);
		return new ModelAndView(VIEW);
    }
}
