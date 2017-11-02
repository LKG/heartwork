package im.heart.cms.service.mpl;

import java.math.BigInteger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import im.heart.cms.entity.ArticleCategory;
import im.heart.cms.service.ArticleCategoryService;
import im.heart.core.service.impl.CommonServiceImpl;

@Service(value = ArticleCategoryService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class ArticleCategoryServiceImpl extends CommonServiceImpl<ArticleCategory, BigInteger> implements ArticleCategoryService {

}
