package im.heart.admin.log.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import im.heart.core.CommonConst;
import im.heart.core.plugins.persistence.DynamicPageRequest;
import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.core.web.AbstractController;
import im.heart.log.entity.FrameLogLogin;
import im.heart.log.service.FrameLogLoginService;
@Controller
@RequestMapping("/admin")
public class FrameLogLoginController extends AbstractController {
	protected static final String apiVer = "/logLogin";
	protected static final String VIEW_LIST="admin/usercore/log_login_list";
	
	@Autowired
	private FrameLogLoginService frameLogLoginService;
	/**
	 * 
	 * @Desc：查询用户登录日志信息列表
	 * @param request
	 * @param response
	 * @param page
	 * @param size
	 * @param sort
	 * @param order
	 * @param token
	 * @param model
	 * @return
	 */
	@RequiresPermissions("logLogin:view")
	@RequestMapping(value={apiVer,apiVer+"/",apiVer+"s"})
	public ModelAndView lists(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value = "page", required = false, defaultValue = CommonConst.Page.DEFAULT_PAGE+"") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = CommonConst.Page.DEFAULT_SIZE+"") Integer size,
			@RequestParam(value = "sort", required = false,defaultValue="createTime") String sort,
			@RequestParam(value = "order", required = false,defaultValue = CommonConst.Page.ORDER_DESC) String order,
			@RequestParam(value = CommonConst.RequestResult.ACCESS_TOKEN, required = false) String token,
			ModelMap model) {
		Specification<FrameLogLogin> spec=DynamicSpecifications.bySearchFilter(request, FrameLogLogin.class);
		PageRequest pageRequest=DynamicPageRequest.buildPageRequest(page,size,sort,order,FrameLogLogin.class);
		Page<FrameLogLogin> pag = this.frameLogLoginService.findAll(spec, pageRequest);
		super.success(model,pag);
		return new ModelAndView(VIEW_LIST);
	}
}
