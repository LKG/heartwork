package im.heart.cms.repository;

import im.heart.cms.entity.ArticleCategory;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 
 * @功能说明：文章接口
 * @作者 LKG
 */
public interface ArticleCategoryRepository extends JpaRepository<ArticleCategory, BigInteger> ,JpaSpecificationExecutor<ArticleCategory> {


}
