package im.heart.admin.frame.web;

import java.math.BigInteger;
import java.util.Collection;

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

import im.heart.core.CommonConst;
import im.heart.core.CommonConst.RequestResult;
import im.heart.core.plugins.persistence.DynamicPageRequest;
import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.core.plugins.persistence.SearchFilter;
import im.heart.core.plugins.persistence.SearchFilter.Operator;
import im.heart.core.web.AbstractController;
import im.heart.frame.entity.FrameDict;
import im.heart.frame.entity.FrameDictItem;
import im.heart.frame.service.FrameDictItemService;
import im.heart.frame.service.FrameDictService;

/**
 * 
 * @author gg
 * @desc : 数据字典子表
 */
@Controller
@RequestMapping("/admin")
public class AdminDictItemController extends AbstractController {
	
	protected static final String apiVer = "/dict";
	protected static final String VIEW_LIST="admin/frame/dict_item_list";
	protected static final String VIEW_CREATE="admin/frame/dict_item_create";
	protected static final String VIEW_DETAILS="admin/frame/dict_item_details";

	@Autowired
	private FrameDictService frameDictService;
	@Autowired
	private FrameDictItemService frameDictItemService;

	@RequiresPermissions("dict:create")
	@RequestMapping(value={apiVer+"/{dictId}/item/add"})
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@PathVariable() BigInteger dictId,
			ModelMap model){
		FrameDict po = this.frameDictService.findOne(dictId);
		super.success(model,po);
		return new ModelAndView(VIEW_CREATE);
	}
	@RequiresPermissions("dict:create")
	@RequestMapping(value={apiVer+"/item/save"},method = RequestMethod.POST)
	protected ModelAndView save(
			@Valid @ModelAttribute(RequestResult.RESULT) FrameDictItem frameDictItem,
			BindingResult result, 
			@RequestParam(value = "format", required = false) String format,
			HttpServletRequest request,
			ModelMap model) {
		this.frameDictItemService.save(frameDictItem);
		super.success(model);
		return new ModelAndView(VIEW_SUCCESS);
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
	@RequestMapping(apiVer+"/{dictId}/items")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@PathVariable BigInteger dictId,
			@RequestParam(value = "page", required = false, defaultValue = CommonConst.Page.DEFAULT_PAGE+"") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = CommonConst.Page.DEFAULT_SIZE+"") Integer size,
			@RequestParam(value = "sort", required = false ) String sort,
			@RequestParam(value = "order", required = false,defaultValue = CommonConst.Page.DEFAULT_ORDER) String order,
			@RequestParam(value = CommonConst.RequestResult.ACCESS_TOKEN, required = false) String token,
			ModelMap model) {
		final Collection<SearchFilter> filters= DynamicSpecifications.buildSearchFilters(request);
		filters.add(new SearchFilter("dictId",Operator.EQ,dictId));//查询关联明细
		Specification<FrameDictItem> spec=DynamicSpecifications.bySearchFilter(filters, FrameDictItem.class);
		PageRequest pageRequest=DynamicPageRequest.buildPageRequest(page,size,sort,order,FrameDictItem.class);
		Page<FrameDictItem> pag = this.frameDictItemService.findAll(spec, pageRequest);
		super.success(model,pag);
		return new ModelAndView(VIEW_LIST);
	}
	@RequiresPermissions("dict:delete")
	@RequestMapping(value = apiVer+"/item/{ids}/delete",method = RequestMethod.POST)
	protected ModelAndView batchDelete(
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable BigInteger[] ids,
			HttpServletRequest request,
			ModelMap model) {
		for(BigInteger id:ids){
			this.frameDictItemService.delete(id);
		}
		super.success(model);
		return new ModelAndView(VIEW_SUCCESS);
	}
}
