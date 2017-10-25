package im.heart.admin.usercore.web;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import im.heart.core.enums.Status;
import im.heart.core.plugins.persistence.DynamicPageRequest;
import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.core.plugins.persistence.SearchFilter;
import im.heart.core.plugins.persistence.SearchFilter.Operator;
import im.heart.core.web.AbstractController;
import im.heart.usercore.entity.FrameOrg;
import im.heart.usercore.entity.FrameUser;
import im.heart.usercore.entity.FrameUserOrg;
import im.heart.usercore.model.CheckModel;
import im.heart.usercore.service.FrameOrgService;
import im.heart.usercore.service.FrameUserOrgService;
import im.heart.usercore.service.FrameUserService;
import im.heart.usercore.vo.FrameOrgVO;
import im.heart.usercore.vo.FrameUserVO;



@Controller
@RequestMapping("/admin")
public class  FrameUserOrgController extends AbstractController {
	
	
	protected static final String apiVer = "/user";
	protected static final String VIEW_RELATED="admin/usercore/user_org_related";//关联页面
	@Autowired
	private FrameUserOrgService frameUserOrgService;
	@Autowired
	private FrameUserService frameUserService;
	@Autowired
	private FrameOrgService frameOrgService;
	/**
	 * @Desc 机构关联
	 * @param jsoncallback
	 * @param userId
	 * @param token
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user:audit")
	@RequestMapping(value = apiVer+"/{userId}/relate/org")
	protected ModelAndView relateOrg(
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@PathVariable BigInteger userId,
			@RequestParam(value = CommonConst.RequestResult.ACCESS_TOKEN, required = false) String token,
			HttpServletRequest request,
			ModelMap model) {
		FrameUser po = this.frameUserService.findOne(userId);
		super.success(model, new FrameUserVO(po));
		return new ModelAndView(VIEW_RELATED);
	}
	
	
	
	@RequiresPermissions("user:audit")
	@RequestMapping(value = apiVer+"/setdefaultOrg/{relateId}",method = RequestMethod.POST)
	protected ModelAndView setUserdefault(
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable BigInteger relateId,
			HttpServletRequest request,
			ModelMap model) {
		FrameUserOrg userOrg= this.frameUserOrgService.findOne(relateId);
		if(userOrg!=null){
			this.frameUserOrgService.setDefaultOrg(userOrg.getUserId(),relateId,userOrg.getRelateOrg().getId());
		}
		super.success(model,true);
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
	@RequiresPermissions("user:audit")
	@RequestMapping(value = apiVer+"/{userId}/related/orgs" )
	public ModelAndView relatedOrgs(HttpServletRequest request, HttpServletResponse response,
			@PathVariable BigInteger userId,
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value = "page", required = false, defaultValue = CommonConst.Page.DEFAULT_PAGE+"") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = CommonConst.Page.DEFAULT_SIZE+"") Integer size,
			@RequestParam(value = "sort", required = false ,defaultValue="isDefault") String sort,
			@RequestParam(value = "order", required = false,defaultValue = CommonConst.Page.ORDER_DESC) String order,
			@RequestParam(value = CommonConst.RequestResult.ACCESS_TOKEN, required = false) String token,
			ModelMap model) {
		final Collection<SearchFilter> filters= DynamicSpecifications.buildSearchFilters(request);
		filters.add(new SearchFilter("userId",Operator.EQ,userId));//查询用户已关联机构信息
		Specification<FrameUserOrg> spec=DynamicSpecifications.bySearchFilter(filters, FrameUserOrg.class);
		PageRequest pageRequest=DynamicPageRequest.buildPageRequest(page,size,sort,order,FrameUserOrg.class);
		Page<FrameUserOrg> pag = this.frameUserOrgService.findAll(spec, pageRequest);
		super.success(model,pag);
		return new ModelAndView(VIEW_RELATED);
	}

	@RequiresPermissions("user:audit")
	@RequestMapping(value = apiVer+"/{userId}/addOrg",method = RequestMethod.POST)
	protected ModelAndView addRule(
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable BigInteger userId,
			HttpServletRequest request,
			ModelMap model) {
		FrameUser user= this.frameUserService.findOne(userId);
		String datas = request.getParameter("datas");//直接从请求中取值避免spring 转换
		List<FrameUserOrg> entities=Lists.newArrayList();
		if(user!=null){
			List<CheckModel> checkBoxModels = JSON.parseArray(datas, CheckModel.class);
			for(CheckModel checkBoxModel:checkBoxModels){
				BigInteger id = checkBoxModel.getId();
				FrameOrg relateOrg = this.frameOrgService.findOne(id);
				FrameUserOrg entitie=new FrameUserOrg();
				entitie.setUserId(userId);
				entitie.setRelateOrg(relateOrg);
				//判断用户是否关联机构，如果无关联取第一条数据为默认
				boolean exists = this.frameUserOrgService.existsUserOrg(userId);
				if(!exists){
					this.frameUserService.updateUserDefaultOrg(userId, relateOrg.getId());
					entitie.setIsDefault(Boolean.TRUE);
				}
				entities.add(entitie);
			}
			this.frameUserOrgService.save(entities);
		}
		super.success(model);
		return new ModelAndView(VIEW_SUCCESS);
	}
	/**
	 * 
	 * @Desc：查询机构信息，并包装
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
	@RequiresPermissions("user:audit")
	@RequestMapping(value = apiVer+"/{userId}/orgs" )
	public ModelAndView userOrgs(HttpServletRequest request, HttpServletResponse response,
			@PathVariable BigInteger userId,
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value = "page", required = false, defaultValue = CommonConst.Page.DEFAULT_PAGE+"") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = CommonConst.Page.DEFAULT_SIZE+"") Integer size,
			@RequestParam(value = "sort", required = false ,defaultValue="code") String sort,
			@RequestParam(value = "order", required = false,defaultValue = CommonConst.Page.DEFAULT_ORDER) String order,
			@RequestParam(value = CommonConst.RequestResult.ACCESS_TOKEN, required = false) String token,
			ModelMap model) {
		List<FrameUserOrg> userOrgs = this.frameUserOrgService.findByUserId(userId);
		Map<BigInteger, Boolean> relates=Maps.newHashMap();
		if(userOrgs!=null){
			for(FrameUserOrg userOrg: userOrgs){
				relates.put(userOrg.getRelateOrg().getId(), userOrg.getIsDefault());
			}
		}
		final Collection<SearchFilter> filters= DynamicSpecifications.buildSearchFilters(request);
		filters.add(new SearchFilter("status",Operator.EQ,Status.enabled));//
		Specification<FrameOrg> spec=DynamicSpecifications.bySearchFilter(filters, FrameOrg.class);
		PageRequest pageRequest=DynamicPageRequest.buildPageRequest(page,size,sort,order,FrameOrg.class);
		Page<FrameOrg> pag = this.frameOrgService.findAll(spec, pageRequest);
		if(pag!=null&&pag.hasContent()){
			List<FrameOrgVO> vos=Lists.newArrayList();
			for(FrameOrg org :pag.getContent()){
				FrameOrgVO vo=new FrameOrgVO(org);
				BigInteger id=org.getId();
				if(relates.containsKey(id)){
					vo.setIsRelated(true);
					vo.setIsDefault(relates.get(id));
				}
				vos.add(vo);
			}
			Page<FrameOrgVO> pagvos =new PageImpl<FrameOrgVO>(vos,pageRequest,pag.getTotalElements());
			super.success(model,pagvos);
			return new ModelAndView(VIEW_RELATED);
		}
		super.success(model,pag);
		return new ModelAndView(VIEW_RELATED);
	}
	@RequiresPermissions("user:audit")
	@RequestMapping(value = apiVer+"/org/{ids}/broken",method = RequestMethod.POST)
	protected ModelAndView chainBroken(
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable BigInteger[] ids,
			HttpServletRequest request,
			ModelMap model) {
		for(BigInteger id:ids){
			this.frameUserOrgService.delete(id);
		}
		super.success(model);
		return new ModelAndView(VIEW_SUCCESS);
	}
}
