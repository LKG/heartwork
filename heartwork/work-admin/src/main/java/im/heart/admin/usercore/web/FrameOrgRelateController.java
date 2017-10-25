package im.heart.admin.usercore.web;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import im.heart.core.CommonConst;
import im.heart.core.CommonConst.RequestResult;
import im.heart.core.enums.Status;
import im.heart.core.plugins.persistence.DynamicPageRequest;
import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.core.plugins.persistence.SearchFilter;
import im.heart.core.plugins.persistence.SearchFilter.Operator;
import im.heart.core.web.AbstractController;
import im.heart.usercore.entity.FrameOrg;
import im.heart.usercore.entity.FrameOrgRelate;
import im.heart.usercore.model.CheckModel;
import im.heart.usercore.service.FrameOrgRelateService;
import im.heart.usercore.service.FrameOrgService;
import im.heart.usercore.vo.FrameOrgRelateVO;

/**
 * 
 * @author gg
 * @Desc : 机构关系表
 */
@Controller
@RequestMapping("/admin")
public class  FrameOrgRelateController extends AbstractController {
	protected static final String apiVer = "/org";
	protected static final String VIEW_RELATED="admin/usercore/org_related";//关联页面
	@Autowired
	private FrameOrgRelateService frameOrgRelateService;
	@Autowired
	private FrameOrgService frameOrgService;
	@RequestMapping(value = apiVer+"/relate/{ids}/broken",method = RequestMethod.POST)
	protected ModelAndView chainBroken(
			@RequestParam(value =RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable BigInteger[] ids,
			HttpServletRequest request,
			ModelMap model) {
		for(BigInteger id:ids){
			this.frameOrgRelateService.delete(id);
		}
		super.success(model);
		return new ModelAndView(VIEW_SUCCESS);
	}
	/**
	 * 
	 * @Desc：查询外修商，并包装
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
	@RequestMapping(value = apiVer+"/{orgId}/relate" )
	public ModelAndView repairs(HttpServletRequest request, HttpServletResponse response,
			@PathVariable BigInteger orgId,
			@RequestParam(value = RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value = CommonConst.RequestResult.ACCESS_TOKEN, required = false) String token,
			ModelMap model) {
		FrameOrg po = this.frameOrgService.findOne(orgId);
		super.success(model, po);
		return new ModelAndView(VIEW_RELATED);
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
	@RequestMapping(value = apiVer+"/{orgId}/relateds" )
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
			@PathVariable BigInteger orgId,
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value = "page", required = false, defaultValue = CommonConst.Page.DEFAULT_PAGE+"") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = CommonConst.Page.DEFAULT_SIZE+"") Integer size,
			@RequestParam(value = "sort", required = false ,defaultValue="code") String sort,
			@RequestParam(value = "order", required = false,defaultValue = CommonConst.Page.DEFAULT_ORDER) String order,
			@RequestParam(value = CommonConst.RequestResult.ACCESS_TOKEN, required = false) String token,
			ModelMap model) {
		final Collection<SearchFilter> filters= DynamicSpecifications.buildSearchFilters(request);
		filters.add(new SearchFilter("orgId",Operator.EQ,orgId));
		Specification<FrameOrgRelate> spec=DynamicSpecifications.bySearchFilter(filters, FrameOrgRelate.class);
		PageRequest pageRequest=DynamicPageRequest.buildPageRequest(page,size,sort,order,FrameOrgRelate.class);
		Page<FrameOrgRelate> pag = this.frameOrgRelateService.findAll(spec, pageRequest);
		super.success(model,pag);
		return new ModelAndView(VIEW_SUCCESS);
	}
	/**
	 * 
	 * @Desc：查询关联结构并包装
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
	@RequestMapping(value = apiVer+"/{orgId}/relate/orgs" )
	public ModelAndView relatedOrgs(HttpServletRequest request, HttpServletResponse response,
			@PathVariable BigInteger orgId,
			@RequestParam(value = RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value = "page", required = false, defaultValue = CommonConst.Page.DEFAULT_PAGE+"") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = CommonConst.Page.DEFAULT_SIZE+"") Integer size,
			@RequestParam(value = "sort", required = false ,defaultValue="code") String sort,
			@RequestParam(value = "order", required = false,defaultValue = CommonConst.Page.DEFAULT_ORDER) String order,
			@RequestParam(value = CommonConst.RequestResult.ACCESS_TOKEN, required = false) String token,
			ModelMap model) {
		List<FrameOrgRelate> orgCorrelations = this.frameOrgRelateService.findByOrgId(orgId);
		Map<BigInteger, Boolean> relates=Maps.newHashMap();
		if(orgCorrelations!=null){
			for(FrameOrgRelate orgCorrelation: orgCorrelations){
				relates.put(orgCorrelation.getRelateOrg().getId(), orgCorrelation.getIsDefault());
			}
		}
		final Collection<SearchFilter> filters= DynamicSpecifications.buildSearchFilters(request);
		filters.add(new SearchFilter("status",Operator.EQ,Status.enabled));//
		Specification<FrameOrg> spec=DynamicSpecifications.bySearchFilter(filters, FrameOrg.class);
		PageRequest pageRequest=DynamicPageRequest.buildPageRequest(page,size,sort,order,FrameOrg.class);
		Page<FrameOrg> pag = this.frameOrgService.findAll(spec, pageRequest);
		if(pag!=null&&pag.hasContent()){
			List<FrameOrgRelateVO> vos=Lists.newArrayList();
			for(FrameOrg org :pag.getContent()){
				FrameOrgRelateVO vo=new FrameOrgRelateVO(org);
				BigInteger id=org.getId();
				if(relates.containsKey(id)){
					Boolean isDefault = relates.get(id);
					vo.setIsDefault(isDefault);
					vo.setIsRelated(true);
				}
				vos.add(vo);
			}
			Page<FrameOrgRelateVO> pagvos =new PageImpl<FrameOrgRelateVO>(vos,pageRequest,pag.getTotalElements());
			super.success(model,pagvos);
			return new ModelAndView(VIEW_RELATED);
		}
		super.success(model,pag);
		return new ModelAndView(VIEW_SUCCESS);
	}
	@RequestMapping(value = apiVer+"/{orgId}/addRelate/{relateType}",method = RequestMethod.POST)
	protected ModelAndView addRule(
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable BigInteger orgId,
			@PathVariable String relateType,
			HttpServletRequest request,
			ModelMap model) {
		FrameOrg org= this.frameOrgService.findOne(orgId);
		String datas = request.getParameter("datas");//直接从请求中取值避免spring 转换
		List<FrameOrgRelate> entities=Lists.newArrayList();
		if(org!=null){
			List<CheckModel> checkBoxModels = JSON.parseArray(datas, CheckModel.class);
			for(CheckModel checkBox:checkBoxModels){
				BigInteger id = checkBox.getId();
				Boolean isDefault=checkBox.getIsDefault();
				FrameOrg frameOrg = this.frameOrgService.findOne(id);
				FrameOrgRelate entitie=new FrameOrgRelate();
				entitie.setIsDefault(isDefault);
				entitie.setOrgId(orgId);
				entitie.setRelateType(relateType);
				entitie.setRelateOrg(frameOrg);
				entities.add(entitie);
			}
			this.frameOrgRelateService.save(entities);
		}
		super.success(model);
		return new ModelAndView(VIEW_SUCCESS);
	}
	@RequestMapping(value = apiVer+"/relate/{ids}/offdefault",method = RequestMethod.POST)
	protected ModelAndView batchoffdefault(
			@RequestParam(value =RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable BigInteger[] ids,
			HttpServletRequest request,
			ModelMap model) {
		for(BigInteger id:ids){
			FrameOrgRelate entitie = this.frameOrgRelateService.findOne(id);
			if(entitie!=null){
				entitie.setIsDefault(false);
				this.frameOrgRelateService.save(entitie);
			}
		}
		super.success(model);
		return new ModelAndView(VIEW_SUCCESS);
	}
	@RequestMapping(value = apiVer+"/relate/{ids}/setdefault",method = RequestMethod.POST)
	protected ModelAndView batchsetdefault(
			@RequestParam(value =RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable BigInteger[] ids,
			HttpServletRequest request,
			ModelMap model) {
		for(BigInteger id:ids){
			FrameOrgRelate entitie = this.frameOrgRelateService.findOne(id);
			if(entitie!=null){
				entitie.setIsDefault(true);
				this.frameOrgRelateService.save(entitie);
			}
		}
		super.success(model);
		return new ModelAndView(VIEW_SUCCESS);
	}
}
