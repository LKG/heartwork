package im.heart.admin.cms.web;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import im.heart.cms.entity.Ad;
import im.heart.cms.service.AdService;
import im.heart.core.CommonConst;
import im.heart.core.CommonConst.RequestResult;
import im.heart.core.log.OptLog;
import im.heart.core.plugins.persistence.DynamicPageRequest;
import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.core.web.AbstractController;
import im.heart.core.web.ResponseError;
import im.heart.core.web.enums.WebError;
import im.heart.security.utils.SecurityUtilsHelper;
import im.heart.usercore.vo.FrameUserVO;

@Controller
@RequestMapping("/admin")
public class AdmsController extends AbstractController { 
	protected static final String apiVer = "/adm";
	protected static final String VIEW_LIST="admin/cms/ad_list";
	protected static final String VIEW_DETAILS="admin/cms/ad_details";
	protected static final String VIEW_CREATE="admin/cms/ad_create";
	@Autowired
	private AdService adService;
	
	@RequestMapping(value = apiVer+"/{id}")
	protected ModelAndView findById(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			@PathVariable BigInteger id,
			@RequestParam(value = "access_token", required = false) String token,
			HttpServletRequest request,
			ModelMap model) {
		Ad po = this.adService.findOne(id);
		super.success(model, po);
		return new ModelAndView(VIEW_DETAILS);
	}
	@RequestMapping(apiVer+"s")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			@RequestParam(value = "page", required = false, defaultValue = CommonConst.Page.DEFAULT_PAGE+"") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = CommonConst.Page.DEFAULT_SIZE+"") Integer size,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "order", required = false,defaultValue = CommonConst.Page.DEFAULT_ORDER) String order,
			@RequestParam(value = "access_token", required = false) String token,
			ModelMap model) {
		Specification<Ad> spec=DynamicSpecifications.bySearchFilter(request, Ad.class);
		PageRequest pageRequest=DynamicPageRequest.buildPageRequest(page,size,sort,order,Ad.class);
		Page<Ad> pag = this.adService.findAll(spec, pageRequest);
		super.success(model,pag);
		return new ModelAndView(VIEW_LIST);
	}

	@OptLog(detail="文章新增",type="adm")
	@RequiresPermissions("adm:create")
	@RequestMapping(value = apiVer+"/save",method = RequestMethod.POST)
	protected ModelAndView saveOrUpdate(
			@Valid @ModelAttribute(RequestResult.RESULT) Ad ad,
			BindingResult result, 
			@RequestParam(value = "format", required = false) String format,
			HttpServletRequest request,
			ModelMap model) {
		FrameUserVO userVo = SecurityUtilsHelper.getCurrentUser();
		if (userVo == null) {
			super.fail(model, new ResponseError(WebError.auth_credentials_expired));
			return new ModelAndView(RESULT_PAGE);
		}
		this.adService.save(ad);
		super.success(model);
		return new ModelAndView(VIEW_SUCCESS);
	}
	
	@RequiresPermissions("adm:create")
	@RequestMapping(value={apiVer+"/add"})
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value = "access_token", required = false) String token,
			ModelMap model){
		super.success(model);
		return new ModelAndView(VIEW_CREATE);
	}
}
