package im.heart.common.web;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;

import im.heart.core.CommonConst;
import im.heart.core.plugins.persistence.DynamicPageRequest;
import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.core.plugins.persistence.SearchFilter;
import im.heart.core.plugins.persistence.SearchFilter.Operator;
import im.heart.core.web.AbstractController;
import im.heart.log.entity.FrameLogLogin;
import im.heart.log.service.FrameLogLoginService;
import im.heart.log.vo.FrameUserLoginLogVO;
import im.heart.security.utils.SecurityUtilsHelper;
@Controller
public class UserLoginLogController extends AbstractController {
	protected static final String apiVer = "/userinfo/loginlog";
	protected static final String VIEW_LIST="userinfo/user-loginlog";
	
	@Autowired
	private FrameLogLoginService frameLogLoginService;
	/**
	 * 
	 * @Desc：查询用户信息列表
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
	@RequestMapping(value={apiVer,apiVer+"s"})
	public ModelAndView lists(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value = "sort", required = false,defaultValue="createTime") String sort,
			@RequestParam(value = "order", required = false,defaultValue = CommonConst.Page.ORDER_DESC) String order,
			@RequestParam(value = CommonConst.RequestResult.ACCESS_TOKEN, required = false) String token,
			ModelMap model) {
		final Collection<SearchFilter> filters= DynamicSpecifications.buildSearchFilters(request);
		filters.add(new SearchFilter("userId",Operator.EQ,SecurityUtilsHelper.getCurrentUser().getUserId()));//查询
		Specification<FrameLogLogin> spec=DynamicSpecifications.bySearchFilter(filters, FrameLogLogin.class);
		PageRequest pageRequest=DynamicPageRequest.buildPageRequest(CommonConst.Page.DEFAULT_PAGE,CommonConst.Page.DEFAULT_SIZE*2,sort,order,FrameLogLogin.class);
		Page<FrameLogLogin> pag = this.frameLogLoginService.findAll(spec, pageRequest);
		if(pag!=null&&pag.hasContent()){
			List<FrameUserLoginLogVO> vos=Lists.newArrayList();
			for(FrameLogLogin po :pag.getContent()){
				FrameUserLoginLogVO vo=new FrameUserLoginLogVO(po);
				vos.add(vo);
			}
			Page<FrameUserLoginLogVO> pagvos =new PageImpl<FrameUserLoginLogVO>(vos,pageRequest,pag.getTotalElements());
			super.success(model,pagvos);
			return new ModelAndView(VIEW_LIST);
		}
		super.success(model,pag);
		return new ModelAndView(VIEW_LIST);
	}
}
