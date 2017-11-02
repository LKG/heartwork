package im.heart.admin.rpt.web;

import java.beans.PropertyEditorSupport;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import im.heart.core.CommonConst;
import im.heart.core.CommonConst.RequestResult;
import im.heart.core.plugins.persistence.DynamicPageRequest;
import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.core.utils.StringUtilsEx;
import im.heart.rpt.entity.RptConfig;
import im.heart.rpt.service.RptConfigService;
import im.heart.rpt.validator.RptConfigValidator;
import im.heart.rpt.vo.RptConfigVO;
import im.heart.core.web.AbstractController;
/**
 * 
 * @author gg
 * @desc 报表配置控制器
 */
@Controller
@RequestMapping("/admin")
public class RptConfigController extends AbstractController {
	protected static final String apiVer = "/rptConfig";
	protected static final String VIEW_LIST="admin/rpt/rptConfig_list";
	protected static final String VIEW_CREATE="admin/rpt/rptConfig_create";
	protected static final String VIEW_DETAILS="admin/rpt/rptConfig_details";
	
	@Autowired
	private RptConfigService rptConfigService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(RptConfig.RptCriType.class,  new PropertyEditorSupport(){
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				//TODO:
			}
		});
		binder.validate(new RptConfigValidator());
	}

	@RequiresPermissions("rptConfig:view")
	@RequestMapping(apiVer+"s")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			@RequestParam(value = "page", required = false, defaultValue = CommonConst.Page.DEFAULT_PAGE+"") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = CommonConst.Page.DEFAULT_SIZE+"") Integer size,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "order", required = false,defaultValue = CommonConst.Page.DEFAULT_ORDER) String order,
			@RequestParam(value = "access_token", required = false) String token,
			ModelMap model) {
		Specification<RptConfig> spec=DynamicSpecifications.bySearchFilter(request, RptConfig.class);
		PageRequest pageRequest=DynamicPageRequest.buildPageRequest(page,size,sort,order,RptConfig.class);
		
		Page<RptConfig> pag = this.rptConfigService.findAll(spec, pageRequest);
		if(pag!=null&&pag.hasContent()){
			List<RptConfigVO> vos = new ArrayList<RptConfigVO>();
			for(RptConfig rptConfig:pag.getContent()){
				vos.add(new RptConfigVO(rptConfig));
			}
			Page<RptConfigVO> pagvos =new PageImpl<RptConfigVO>(vos,pageRequest,pag.getTotalElements());
			super.success(model,pagvos);
			return new ModelAndView(VIEW_LIST);
		}
		super.success(model,pag);
		return new ModelAndView(VIEW_LIST);
	}
	
	/**
	 * 
	 * @功能说明：根据Id 查询项目信息
	 * @param jsoncallback
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("rptConfig:view")
	@RequestMapping(value = apiVer+"/{id}",method = {RequestMethod.GET,RequestMethod.POST} )
	protected ModelAndView findById(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			@PathVariable BigInteger id,
			HttpServletRequest request,
			@RequestParam(value = "access_token", required = false) String token,
			ModelMap model) {
		RptConfig po = this.rptConfigService.findOne(id);
		super.success(model, po);
		return new ModelAndView(VIEW_DETAILS);
	}
	
	@RequestMapping(value = apiVer + "/success", method = RequestMethod.GET)
	protected ModelAndView success(
			@ModelAttribute(RequestResult.RESULT) RptConfig rptConfig,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		super.success(model,rptConfig);
		return new ModelAndView(VIEW_SUCCESS);
	}
	/**
	 * @功能说明：新增
	 * @param rptConfig
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("rptConfig:create")
	@RequestMapping(value = apiVer + "/add", method = {RequestMethod.GET,RequestMethod.POST})
	protected ModelAndView add(
			@ModelAttribute(RequestResult.RESULT) RptConfig rptConfig,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		super.success(model,rptConfig);
		return new ModelAndView(VIEW_CREATE);
	}
	/**
	 * 
	 * @功能说明：
	 * @param rptConfig
	 * @param format
	 * @param result
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws ServiceException
	 */
	@RequiresPermissions("rptConfig:create")
	@RequestMapping(value = apiVer + "/subGeneral", method = RequestMethod.POST)
	public ModelAndView create(
			@Valid @ModelAttribute(RequestResult.RESULT) RptConfig rptConfig,
			@RequestParam(value = "format", required = false) String format,
			BindingResult result, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		
		RptConfig entity=this.rptConfigService.save(rptConfig);
		redirectAttributes.addFlashAttribute(RequestResult.RESULT, entity);
		if(StringUtilsEx.isBlank(format)){
			format="jhtml";
		}
		return new ModelAndView(redirectToUrl(apiVer + "/success."+format));
	}
}
