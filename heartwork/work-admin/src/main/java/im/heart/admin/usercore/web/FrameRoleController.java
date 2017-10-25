package im.heart.admin.usercore.web;
import java.math.BigInteger;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import im.heart.core.CommonConst;
import im.heart.core.CommonConst.RequestResult;
import im.heart.core.enums.Status;
import im.heart.core.plugins.persistence.DynamicPageRequest;
import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.core.plugins.persistence.SearchFilter;
import im.heart.core.plugins.persistence.SearchFilter.Operator;
import im.heart.core.web.AbstractController;
import im.heart.usercore.entity.FrameRole;
import im.heart.usercore.entity.FrameUser;
import im.heart.usercore.service.FrameRoleService;
import im.heart.usercore.service.FrameUserService;

@Controller
@RequestMapping("/admin")
public class FrameRoleController extends AbstractController {
	protected static final String apiVer = "/role";
	protected static final String VIEW_LIST="admin/usercore/role_list";
	protected static final String VIEW_CREATE="admin/usercore/role_create";
	protected static final String VIEW_DETAILS="admin/usercore/role_details";
	protected static final String VIEW_AUTH_DETAILS="admin/usercore/role_auth_details";
	
	protected static final String VIEW_USER_DETAILS="admin/usercore/role_user_details";
	@Autowired
	private FrameRoleService frameRoleService;
	@Autowired
	private FrameUserService frameUserService;
	
	
	
	@RequestMapping(value = apiVer + "/checkRoleCode")
	protected ModelAndView checkRoleCode(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			@RequestParam(value = "roleCode", required = false) String roleCode,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		if (StringUtils.isBlank(roleCode)){
			super.fail(model,false);
			return new ModelAndView(RESULT_PAGE);
		}
		boolean exists = this.frameRoleService.existsRoleCode(roleCode);
		if (exists) {// 角色名已存在
			super.fail(model,false);
			return new ModelAndView(RESULT_PAGE);
		}
		super.success(model, true);
		return new ModelAndView(RESULT_PAGE);
	}
	@RequiresPermissions("role:create")
	@RequestMapping(value={apiVer+"/add"})
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response,
			ModelMap model,FrameRole po){
		super.success(model, po);
		return new ModelAndView(VIEW_CREATE);
	}
	@RequestMapping(value = apiVer+"/save", method = RequestMethod.POST)
	protected ModelAndView save(
			@Validated() @ModelAttribute(RequestResult.RESULT) FrameRole frameRole,
			BindingResult result, 
			HttpServletRequest request,
			BigInteger[] resourceIds, 
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			RedirectAttributes redirectAttributes,ModelMap model){
		FrameRole newFrameRole = this.frameRoleService.save(frameRole);
		super.success(model,newFrameRole);
		return new ModelAndView(VIEW_SUCCESS);
	}
	
	@RequiresPermissions("role:create")
	@RequestMapping(value = apiVer+"{roleId}/save",method={RequestMethod.PUT,RequestMethod.POST})
	protected ModelAndView update(
			@PathVariable() BigInteger roleId,
			@Validated() @ModelAttribute(RequestResult.RESULT) FrameRole frameRole,
			@RequestParam(value = "format", required = false) String format,
			BindingResult result, HttpServletRequest request,
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			RedirectAttributes redirectAttributes,ModelMap model){
		FrameRole oldRole = this.frameRoleService.findOne(roleId);
		if(oldRole!=null){
			BeanUtils.copyProperties(frameRole, oldRole);
			FrameRole newframeRole = this.frameRoleService.save(oldRole);
			super.success(model,newframeRole);
		}
		return new ModelAndView(VIEW_SUCCESS);
	}
	/**
	 * 
	 * @Desc：查询角色列表
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
	@RequiresPermissions("role:view")
	@RequestMapping(value={apiVer,apiVer+"/",apiVer+"s"})
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value = "page", required = false, defaultValue = CommonConst.Page.DEFAULT_PAGE+"") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = CommonConst.Page.DEFAULT_SIZE+"") Integer size,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "order", required = false,defaultValue = CommonConst.Page.DEFAULT_ORDER) String order,
			@RequestParam(value = CommonConst.RequestResult.ACCESS_TOKEN, required = false) String token,
			ModelMap model) {
		Specification<FrameRole> spec=DynamicSpecifications.bySearchFilter(request, FrameRole.class);
		PageRequest pageRequest=DynamicPageRequest.buildPageRequest(page,size,sort,order,FrameRole.class);
		Page<FrameRole> pag = this.frameRoleService.findAll(spec, pageRequest);
		super.success(model,pag);
		return new ModelAndView(VIEW_LIST);
	}
	//TODO:
	@RequiresPermissions("role:view")
	@RequestMapping(value = apiVer+"/{roleId}/users")
	public ModelAndView userlist(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@PathVariable() BigInteger roleId,
			@RequestParam(value = "page", required = false, defaultValue = CommonConst.Page.DEFAULT_PAGE+"") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = CommonConst.Page.DEFAULT_SIZE+"") Integer size,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "order", required = false,defaultValue = CommonConst.Page.DEFAULT_ORDER) String order,
			@RequestParam(value = CommonConst.RequestResult.ACCESS_TOKEN, required = false) String token,
			ModelMap model) {
		final Collection<SearchFilter> filters= DynamicSpecifications.buildSearchFilters(request);
		filters.add(new SearchFilter("status",Operator.EQ,Status.enabled));
		Specification<FrameUser> spec=DynamicSpecifications.bySearchFilter(filters, FrameUser.class);
		PageRequest pageRequest=DynamicPageRequest.buildPageRequest(page,size,sort,order,FrameUser.class);
		Page<FrameUser> pag = this.frameUserService.findAll(spec, pageRequest);
	    FrameRole role = this.frameRoleService.findOne(roleId);
	    model.put("role", role);
		super.success(model,pag);
		return new ModelAndView(VIEW_USER_DETAILS);
	}
	
	@RequiresPermissions("role:view")
	@RequestMapping(value = apiVer+"/{roleId}")
	protected ModelAndView findByKey(
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable() BigInteger roleId,
			HttpServletRequest request,
			ModelMap model) {
		FrameRole po = this.frameRoleService.findOne(roleId);
		super.success(model, po);
		return new ModelAndView(VIEW_DETAILS);
	}
	@RequiresPermissions("role:delete")
	@RequestMapping(value = apiVer+"/{roleIds}/delete",method = RequestMethod.POST)
	protected ModelAndView batchDelete(
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable BigInteger[] roleIds,
			HttpServletRequest request,
			ModelMap model) {
		for(BigInteger id:roleIds){
			this.frameRoleService.delete(id);
		}
		super.success(model,true);
		return new ModelAndView(VIEW_SUCCESS);
	}
}
