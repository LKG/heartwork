package im.heart.admin.usercore.web;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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

import im.heart.core.CommonConst;
import im.heart.core.enums.Status;
import im.heart.core.plugins.persistence.DynamicPageRequest;
import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.core.plugins.persistence.SearchFilter;
import im.heart.core.plugins.persistence.SearchFilter.Operator;
import im.heart.core.web.AbstractController;
import im.heart.usercore.entity.FrameResource;
import im.heart.usercore.entity.FrameRole;
import im.heart.usercore.model.ResourceCheckModel;
import im.heart.usercore.service.FrameResourceService;
import im.heart.usercore.service.FrameRoleResourceService;
import im.heart.usercore.service.FrameRoleService;
import im.heart.usercore.vo.FrameResourceVO;
/**
 * 
 * @author gg
 *
 */
@Controller
@RequestMapping("/admin")
public class FrameRoleResourceController extends AbstractController {
	protected static final String apiVer = "/role";
	protected static final String VIEW_LIST="admin/usercore/role_resource_list";
	protected static final String VIEW_CREATE="admin/usercore/role_resource_create";
	protected static final String VIEW_RES_DETAILS="admin/usercore/role_res_details";
	@Autowired
	private FrameRoleService frameRoleService;
	@Autowired
	private FrameRoleResourceService frameRoleResourceService;
	
	@Autowired
	private FrameResourceService frameResourceService;
	
	@RequiresPermissions("role:audit")
	@RequestMapping(value = apiVer+"/{roleId}/addRes",method = RequestMethod.POST)
	protected ModelAndView addRule(
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable BigInteger roleId,
			HttpServletRequest request,
			ModelMap model) {
		FrameRole po = this.frameRoleService.findOne(roleId);
		String datas = request.getParameter("datas");//直接从请求中取值避免spring 转换
		if(po!=null){
			List<ResourceCheckModel> checkBoxModels = JSON.parseArray(datas, ResourceCheckModel.class);
			this.frameRoleResourceService.saveRoleResource(po,checkBoxModels);
		}
		super.success(model);
		return new ModelAndView(VIEW_SUCCESS);
	}
	@RequiresPermissions("role:audit")
	@RequestMapping(value = apiVer+"/{roleId}/resview")
	protected ModelAndView resview(
			@RequestParam(value = "page", required = false, defaultValue = CommonConst.Page.DEFAULT_PAGE+"") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "2000") Integer size,
			@RequestParam(value = "sort", required = false, defaultValue="parentId" ) String sort,
			@RequestParam(value = "order", required = false,defaultValue = CommonConst.Page.DEFAULT_ORDER) String order,
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable() BigInteger roleId,
			HttpServletRequest request,
			ModelMap model) {
		FrameRole po = this.frameRoleService.findOne(roleId);
		Map<BigInteger,Set<BigInteger>> roleResourceMap=this.frameRoleResourceService.findResourceMapByRoleCode(po.getRoleCode());
		final Collection<SearchFilter> filters= DynamicSpecifications.buildSearchFilters(request);
		filters.add(new SearchFilter("status",Operator.EQ,Status.enabled));
		Specification<FrameResource> spec=DynamicSpecifications.bySearchFilter(filters, FrameResource.class);
		PageRequest pageRequest=DynamicPageRequest.buildPageRequest(page,size,sort,order,FrameResource.class);
		Page<FrameResource> pag = this.frameResourceService.findAll(spec, pageRequest);
		super.success(model,po);
		if(pag!=null&&pag.hasContent()){
			List<FrameResourceVO> vos = new ArrayList<FrameResourceVO>();
			for(FrameResource res:pag.getContent()){
				FrameResourceVO	resourceVO=new FrameResourceVO(res,roleResourceMap);
				vos.add(resourceVO);
			}
			Page<FrameResourceVO> pagvos =new PageImpl<FrameResourceVO>(vos,pageRequest,pag.getTotalElements());
			model.put("resources", pagvos);
			return new ModelAndView(VIEW_RES_DETAILS);
		}

		return new ModelAndView(VIEW_RES_DETAILS);
	}
}
