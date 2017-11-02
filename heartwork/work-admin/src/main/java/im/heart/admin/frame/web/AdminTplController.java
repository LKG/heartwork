package im.heart.admin.frame.web;

import java.math.BigInteger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import im.heart.core.CommonConst;
import im.heart.core.CommonConst.RequestResult;
import im.heart.core.plugins.persistence.DynamicPageRequest;
import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.core.web.AbstractController;
import im.heart.frame.entity.FrameTpl;
import im.heart.frame.service.FrameTplService;
import im.heart.frame.service.TemplateService;


/**
 * 
 * @author gg
 * @Desc : 模板
 */
@Controller
@RequestMapping("/admin")
public class AdminTplController extends AbstractController {
	
	protected static final String apiVer = "/tpl";
	protected static final String VIEW_LIST="admin/frame/tpl_list";
	protected static final String VIEW_CREATE="admin/frame/tpl_create";
	protected static final String VIEW_DETAILS="admin/frame/tpl_details";

	@Autowired
	private FrameTplService frameTplService;
	@Autowired
	private TemplateService<FrameTpl, BigInteger> templateService;
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	/**
	 * 
	 * @Desc：查询所有
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @param page
	 * @param size
	 * @param sort
	 * @param order
	 * @param token
	 * @param model
	 * @return
	 */
	@RequiresPermissions("tpl:view")
	@RequestMapping(apiVer+"s")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value = "page", required = false, defaultValue = CommonConst.Page.DEFAULT_PAGE+"") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = CommonConst.Page.DEFAULT_SIZE+"") Integer size,
			@RequestParam(value = "sort", required = false ,defaultValue = "createTime") String sort,
			@RequestParam(value = "order", required = false,defaultValue = CommonConst.Page.DEFAULT_ORDER) String order,
			@RequestParam(value = CommonConst.RequestResult.ACCESS_TOKEN, required = false) String token,
			ModelMap model) {
		Specification<FrameTpl> spec=DynamicSpecifications.bySearchFilter(request, FrameTpl.class);
		PageRequest pageRequest=DynamicPageRequest.buildPageRequest(page,size,sort,order,FrameTpl.class);
		Page<FrameTpl> pag = this.frameTplService.findAll(spec, pageRequest);
		super.success(model,pag);
		return new ModelAndView(VIEW_LIST);
	}
	@RequiresPermissions("tpl:view")
	@RequestMapping(value = apiVer+"/{id}")
	protected ModelAndView findById(
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value = CommonConst.RequestResult.ACCESS_TOKEN, required = false) String token,
			@PathVariable BigInteger id,
			HttpServletRequest request,
			ModelMap model) {
		FrameTpl po = this.frameTplService.findOne(id);
		model.addAttribute("content", this.templateService.read(id));
		super.success(model, po);
		return new ModelAndView(VIEW_DETAILS);
	}
	@RequestMapping(value = apiVer + "/checkCode")
	protected ModelAndView checkCode(
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value = "tplCode", required = false) String tplCode,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		if (StringUtils.isBlank(tplCode)) {
			super.fail(model, false);
			return new ModelAndView(RESULT_PAGE);
		}
		boolean exists = this.frameTplService.exists(tplCode);
		if (exists) {// 已存在
			super.fail(model,false);
			return new ModelAndView(RESULT_PAGE);
		}
		super.success(model, true);
		return new ModelAndView(RESULT_PAGE);
	}
	
	@RequiresPermissions("tpl:create")
	@RequestMapping(value = apiVer+"/add")
	public ModelAndView createFrom(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute(RequestResult.RESULT) FrameTpl frameTpl,
			@RequestParam(value = CommonConst.RequestResult.ACCESS_TOKEN, required = false) String token,
			ModelMap model) {
		super.success(model, frameTpl);
		return new ModelAndView(VIEW_CREATE);
	}
	@RequiresPermissions("tpl:create")
	@RequestMapping(value = apiVer+"/save",method = RequestMethod.POST)
	protected ModelAndView saveOrUpdate(
			@Valid @ModelAttribute(RequestResult.RESULT) FrameTpl frameTpl,
			BindingResult result, 
			@RequestParam(value = "format", required = false) String format,
			HttpServletRequest request,
			ModelMap model) {
		this.frameTplService.save(frameTpl);
		this.freeMarkerConfigurer.getConfiguration().clearTemplateCache();
		super.success(model);
		return new ModelAndView(VIEW_SUCCESS);
	}

	@RequiresPermissions("tpl:delete")
	@RequestMapping(value = apiVer+"/{ids}/delete",method = RequestMethod.POST)
	protected ModelAndView batchDelete(
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable BigInteger[] ids,
			HttpServletRequest request,
			ModelMap model) {
		for(BigInteger id:ids){
			this.frameTplService.delete(id);
		}
		super.success(model);
		return new ModelAndView(VIEW_SUCCESS);
	}
}
