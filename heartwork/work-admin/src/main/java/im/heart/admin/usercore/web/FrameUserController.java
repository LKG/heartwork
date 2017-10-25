package im.heart.admin.usercore.web;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;

import im.heart.core.CommonConst;
import im.heart.core.CommonConst.RequestResult;
import im.heart.core.enums.Status;
import im.heart.core.log.OptLog;
import im.heart.core.plugins.persistence.DynamicPageRequest;
import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.core.utils.ValidatorUtils;
import im.heart.core.web.AbstractController;
import im.heart.usercore.entity.FrameRole;
import im.heart.usercore.entity.FrameUser;
import im.heart.usercore.exception.IncorrectCredentialsException;
import im.heart.usercore.service.FrameRoleService;
import im.heart.usercore.service.FrameUserRoleService;
import im.heart.usercore.service.FrameUserService;
import im.heart.usercore.vo.FrameUserRoleVO;
import im.heart.usercore.vo.FrameUserVO;
@Controller
@RequestMapping("/admin")
public class FrameUserController extends AbstractController {
	protected static final String apiVer = "/user";
	protected static final String VIEW_LIST="admin/usercore/user_list";
	protected static final String VIEW_CREATE="admin/usercore/user_create";
	protected static final String VIEW_AUTH="admin/usercore/user_auth";
	protected static final String VIEW_DETAILS="admin/usercore/user_details";
	@Autowired
	private FrameUserService frameUserService;
	
	@Autowired
	private FrameRoleService frameRoleService;
	@Autowired
	private FrameUserRoleService frameUserRoleService;
	
	@RequestMapping(value = apiVer + "/checkUserName", method = RequestMethod.GET)
	protected ModelAndView checkUserName(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			@RequestParam(value = "userName", required = false) String userName,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		if (StringUtils.isBlank(userName) || userName.length() < 5
				|| userName.length() > 30) {
			super.fail(model,false);
			return new ModelAndView(RESULT_PAGE);
		}
		boolean exist = this.frameUserService.existsUserName(userName);
		if (exist) {// 用户名已存在
			super.fail(model,false);
			return new ModelAndView(RESULT_PAGE);
		}
		super.success(model,true);
		return new ModelAndView(RESULT_PAGE);
	}

	@RequestMapping(value = apiVer + "/checkUserEmail")
	protected ModelAndView checkUserEmail(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			@RequestParam(value = "userEmail", required = false) String userEmail,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		if (!ValidatorUtils.isEmail(userEmail)) {
			super.fail(model,false);
			return new ModelAndView(RESULT_PAGE);
		}
		boolean exist = this.frameUserService.existsUserEmail(userEmail);
		if (exist) {// 邮箱已存在
			super.fail(model,false);
			return new ModelAndView(RESULT_PAGE);
		}
		super.success(model,true);
		return new ModelAndView(RESULT_PAGE);
	}

	@RequestMapping(value = apiVer + "/checkUserPhone")
	protected ModelAndView checkUserPhone(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			@RequestParam(value = "userPhone", required = false) String userPhone,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		if (!ValidatorUtils.isPhone(userPhone)) {
			super.fail(model,false);
			return new ModelAndView(RESULT_PAGE);
		}
		boolean exist = this.frameUserService.existsUserPhone(userPhone);;
		if (exist) {// 电话号码已存在
			super.fail(model,false);
			return new ModelAndView(RESULT_PAGE);
		}
		super.success(model,true);
		return new ModelAndView(RESULT_PAGE);
	}
	@RequiresPermissions("user:create")
	@RequestMapping(value={apiVer+"/add"})
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response,
			ModelMap model,FrameUser frameUser){
		super.success(model, frameUser);
		return new ModelAndView(VIEW_CREATE);
	}
	@RequiresPermissions("user:create")
	@RequestMapping(value = apiVer+"/save",method = RequestMethod.POST)
	protected ModelAndView saveOrUpdate(
			@Valid @ModelAttribute(RequestResult.RESULT) FrameUser frameUser,
			@RequestParam(value = "format", required = false) String format,
			BindingResult result, HttpServletRequest request,
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			RedirectAttributes redirectAttributes,ModelMap model){
		String phone=frameUser.getUserPhone();
		if(ValidatorUtils.isPhone(phone)){
			frameUser.setStatus(Status.enabled);
		}
		FrameUser newFrameUser = this.frameUserService.save(frameUser);
		super.success(model,newFrameUser);
		return new ModelAndView(VIEW_SUCCESS);
	}
	@RequiresPermissions("user:audit")
	@RequestMapping(value = apiVer+"/{userId}/authview")
	protected ModelAndView authView(
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable BigInteger userId,
			HttpServletRequest request,
			ModelMap model) {
		FrameUser frameUser = this.frameUserService.findOne(userId);
		Map<String, BigInteger> roleMap = this.frameUserRoleService.findRoleCodeMapByUserId(userId);
		List<FrameRole> roles = this.frameRoleService.findAll(new Sort(Sort.DEFAULT_DIRECTION,"createTime"));
		List<FrameUserRoleVO> vos = Lists.newArrayList();
		for(FrameRole frameRole:roles){
			FrameUserRoleVO userRoleVO=new FrameUserRoleVO(frameRole);
			if(roleMap.containsKey(frameRole.getRoleCode())){
				userRoleVO.setHasRole(true);
				userRoleVO.setUserId(userId);
			}
			vos.add(userRoleVO);
		}
		super.success(model, frameUser);
		model.put("roleCodes",vos);
		return new ModelAndView(VIEW_AUTH);
	}
	/**
	 * @Desc 用户授权
	 * @param token
	 * @param userId
	 * @param roleCodes
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user:audit")
	@RequestMapping(value = apiVer+"/{userId}/auth",method = RequestMethod.POST)
	protected ModelAndView authRule(
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable BigInteger userId,
			@RequestParam(value ="roleCode" , required = false) String[] roleCodes ,
			HttpServletRequest request,
			ModelMap model) {
		if(roleCodes!=null){
			this.frameUserRoleService.saveUserRole(userId,roleCodes);
		}
		return new ModelAndView(VIEW_SUCCESS);
	}
	/**
	 * 
	 * @Desc：激活用户
	 * @param token
	 * @param ids
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user:audit")
	@RequestMapping(value = apiVer+"/{ids}/activate",method = RequestMethod.POST)
	protected ModelAndView activateUser(
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable BigInteger[] ids,
			HttpServletRequest request,
			ModelMap model) {
		for(BigInteger id:ids){
			FrameUser frameUser = this.frameUserService.findOne(id);
			frameUser.setStatus(Status.enabled);
			this.frameUserService.save(frameUser);
		}
		super.success(model,true);
		return new ModelAndView(VIEW_SUCCESS);
	}
	
	/**
	 * 
	 * @Desc：重置用户密码
	 * @param token
	 * @param ids
	 * @param request
	 * @param model
	 * @return
	 * @throws IncorrectCredentialsException 
	 */
	@OptLog(detail="重置密码",type="org")
	@RequiresPermissions("user:audit")
	@RequestMapping(value = apiVer+"/{userId}/resetpwd",method = RequestMethod.POST)
	protected ModelAndView resetUserPassword(
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable BigInteger userId,
			HttpServletRequest request,
			ModelMap model) throws IncorrectCredentialsException {
		String randomPwd=RandomStringUtils.randomAlphabetic(6);
		this.frameUserService.resetPassword(userId, randomPwd);
		super.success(model,randomPwd);
		return new ModelAndView(VIEW_SUCCESS);
	}
	/**
	 * 
	 * @Desc：禁用用户
	 * @param jsoncallback
	 * @param token
	 * @param ids
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user:audit")
	@RequestMapping(value = apiVer+"/{ids}/disabled",method = RequestMethod.POST)
	protected ModelAndView disabledUser(
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable BigInteger[] ids,
			HttpServletRequest request,
			ModelMap model) {
		for(BigInteger id:ids){
			FrameUser frameUser = this.frameUserService.findOne(id);
			frameUser.setStatus(Status.disabled);
			this.frameUserService.save(frameUser);
		}
		super.success(model,true);
		return new ModelAndView(VIEW_SUCCESS);
	}
	
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
	@RequiresPermissions("user:view")
	@RequestMapping(value={apiVer,apiVer+"/",apiVer+"s"})
	public ModelAndView lists(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value = "page", required = false, defaultValue = CommonConst.Page.DEFAULT_PAGE+"") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = CommonConst.Page.DEFAULT_SIZE+"") Integer size,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "order", required = false,defaultValue = CommonConst.Page.DEFAULT_ORDER) String order,
			@RequestParam(value = CommonConst.RequestResult.ACCESS_TOKEN, required = false) String token,
			ModelMap model) {
		Specification<FrameUser> spec=DynamicSpecifications.bySearchFilter(request, FrameUser.class);
		PageRequest pageRequest=DynamicPageRequest.buildPageRequest(page,size,sort,order,FrameUser.class);
		Page<FrameUser> pag = this.frameUserService.findAll(spec, pageRequest);
		if(pag!=null&&pag.hasContent()){
			List<FrameUserVO> vos = new ArrayList<FrameUserVO>();
			for(FrameUser po:pag.getContent()){
				vos.add(new FrameUserVO(po));
			}
			Page<FrameUserVO> pagvos =new PageImpl<FrameUserVO>(vos,pageRequest,pag.getTotalElements());
			super.success(model,pagvos);
			return new ModelAndView(VIEW_LIST);
		}
		super.success(model,pag);
		return new ModelAndView(VIEW_LIST);
	}
	@RequiresPermissions("user:view")
	@RequestMapping(value = apiVer+"/{id}")
	protected ModelAndView findByKey(
			@RequestParam(value = CommonConst.RequestResult.JSONCALLBACK, required = false) String jsoncallback,
			@RequestParam(value =CommonConst.RequestResult.ACCESS_TOKEN , required = false) String token,
			@PathVariable BigInteger id,
			HttpServletRequest request,
			ModelMap model) {
		FrameUser po = this.frameUserService.findOne(id);
		FrameUserVO vo = new FrameUserVO(po);
		super.success(model, vo);
		return new ModelAndView(VIEW_DETAILS);
	}
}
