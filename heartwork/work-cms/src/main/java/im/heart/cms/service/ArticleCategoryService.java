package im.heart.cms.service;

import java.math.BigInteger;

import im.heart.cms.entity.ArticleCategory;
import im.heart.core.service.CommonService;

/**
 * 
 * @功能说明：文章分类操作接口
 * @作者 LKG
 */
public interface   ArticleCategoryService extends CommonService<ArticleCategory, BigInteger>{
	
	public static final String BEAN_NAME = "articleCategoryService";

}
