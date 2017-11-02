package im.heart.admin.cms.web;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import im.heart.cms.entity.ArticleCategory;
import im.heart.cms.service.ArticleCategoryService;
import im.heart.core.CommonConst;
import im.heart.core.plugins.persistence.DynamicPageRequest;
import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.core.web.AbstractController;

@Controller
@RequestMapping("/admin")
public class ArticleCategoryController extends AbstractController { 
	protected static final String apiVer = "/article/category";
	protected static final String VIEW_LIST="admin/cms/article_category_list";
	protected static final String VIEW_DETAILS="admin/cms/article_category_details";
	@Autowired
	private ArticleCategoryService articleCategoryService;
	
	@RequestMapping(value = apiVer+"/{id}")
	protected ModelAndView findById(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			@PathVariable BigInteger id,
			@RequestParam(value = "access_token", required = false) String token,
			HttpServletRequest request,
			ModelMap model) {
		ArticleCategory po = this.articleCategoryService.findOne(id);
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
		Specification<ArticleCategory> spec=DynamicSpecifications.bySearchFilter(request, ArticleCategory.class);
		PageRequest pageRequest=DynamicPageRequest.buildPageRequest(page,size,sort,order,ArticleCategory.class);
		
		Page<ArticleCategory> pag = this.articleCategoryService.findAll(spec, pageRequest);
		super.success(model,pag);
		return new ModelAndView(VIEW_LIST);
	}
}
