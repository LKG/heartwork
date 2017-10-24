package im.heart.admin.frame.web;


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
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import im.heart.core.utils.StringUtilsEx;
import im.heart.core.utils.ValidatorUtils;
import im.heart.core.web.AbstractController;
import im.heart.core.CommonConst;
import im.heart.core.CommonConst.RequestResult;
import im.heart.core.plugins.persistence.DynamicPageRequest;
import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.frame.entity.FrameArea;
import im.heart.frame.service.FrameAreaService;

/**
 * 
 * @author gg
 * @desc 区域管理表控制器
 */
@Controller
@RequestMapping("/admin")
public class AdminAreaController extends AbstractController {
	
	protected static final String apiVer = "/area";
	protected static final String VIEW_LIST="admin/frame/area_list";
	protected static final String VIEW_TREE="admin/frame/area_tree";
	protected static final String VIEW_DETAILS="admin/frame/area_details";
	protected static final String VIEW_CREATE="admin/frame/area_create";
	@Autowired
	private FrameAreaService frameAreaService;
	@InitBinder  
    public void initBinder(DataBinder binder) {  
      // binder.setValidator(new FrameAreaValidator());  
    }  
	@RequestMapping(value = apiVer+"/tree")
	public ModelAndView treeView(ModelMap model){
		super.success(model);
		return new ModelAndView(VIEW_TREE);
	}
	@RequestMapping(value = apiVer + "/checkCode")
	protected ModelAndView checkCode(
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value = "code", required = false) String code,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		if (StringUtilsEx.isBlank(code)||!ValidatorUtils.isNumber(code)) {
			super.fail(model,false);
			return new ModelAndView(RESULT_PAGE);
		}
		boolean isExit = this.frameAreaService.exists(new BigInteger(code));
		if (isExit) {
			super.fail(model,false);
			return new ModelAndView(RESULT_PAGE);
		}
		super.success(model,true);
		return new ModelAndView(RESULT_PAGE);
	}
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
	@RequiresPermissions("area:view")
	@RequestMapping(value = apiVer+"s" )
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value = "page", required = false, defaultValue = CommonConst.Page.DEFAULT_PAGE+"") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = CommonConst.Page.DEFAULT_SIZE+"") Integer size,
			@RequestParam(value = "sort", required = false ,defaultValue="code") String sort,
			@RequestParam(value = "order", required = false,defaultValue = CommonConst.Page.DEFAULT_ORDER) String order,
			@RequestParam(value = CommonConst.RequestResult.ACCESS_TOKEN, required = false) String token,
			ModelMap model) {
		Specification<FrameArea> spec=DynamicSpecifications.bySearchFilter(request, FrameArea.class);
		PageRequest pageRequest=DynamicPageRequest.buildPageRequest(page,size,sort,order,FrameArea.class);
		Page<FrameArea> pag = this.frameAreaService.findAll(spec, pageRequest);
		super.success(model,pag);
		return new ModelAndView(VIEW_LIST);
	}
	/**
	 * @Desc：根据Id 查询记录
	 * @param jsoncallback
	 * @param id
	 * @param token
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("area:view")
	@RequestMapping(value = apiVer+"/{id}")
	protected ModelAndView findById(
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@PathVariable BigInteger id,
			@RequestParam(value = CommonConst.RequestResult.ACCESS_TOKEN, required = false) String token,
			HttpServletRequest request,
			ModelMap model) {
		FrameArea po = this.frameAreaService.findOne(id);
		if(!po.isRoot()){
			FrameArea parentArea =this.frameAreaService.findOne(po.getParentId());
			po.setParentName(parentArea.getName());
		}
		super.success(model, po);
		return new ModelAndView(VIEW_DETAILS);
	}
	
	/**
	 * @Desc：修改记录
	 * @param frameArea
	 * @param result
	 * @param format
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("area:create")
	@RequestMapping(value = apiVer+"/save",method = RequestMethod.POST)
	protected ModelAndView saveOrUpdate(
			@Valid @ModelAttribute(RequestResult.RESULT) FrameArea frameArea,
			BindingResult result, 
			@RequestParam(value = "format", required = false) String format,
			HttpServletRequest request,
			ModelMap model) {
		this.frameAreaService.save(frameArea);
		super.success(model);
		return new ModelAndView(VIEW_SUCCESS);
	}
	
	@RequiresPermissions("area:create")
	@RequestMapping(value={apiVer+"/{parentId}/add"})
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@PathVariable() BigInteger parentId,
			ModelMap model){
		FrameArea po=new FrameArea();
		if(parentId!=null&&parentId.intValue()!=0){
			FrameArea parentArea= this.frameAreaService.findOne(parentId);
			if(parentArea!=null){
				po.setLevel(parentArea.getLevel()+1);
				po.setParentId(parentArea.getCode());
				po.setParentName(parentArea.getName());
			}
		}
		super.success(model, po);
		return new ModelAndView(VIEW_CREATE);
	}
	@RequiresPermissions("area:delete")
	@RequestMapping(value = apiVer+"/{ids}/delete",method = RequestMethod.POST)
	protected ModelAndView batchDelete(
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable BigInteger[] ids,
			HttpServletRequest request,
			ModelMap model) {
		for(BigInteger id:ids){
			this.frameAreaService.delete(id);
		}
		super.success(model);
		return new ModelAndView(VIEW_SUCCESS);
	}
	
}
