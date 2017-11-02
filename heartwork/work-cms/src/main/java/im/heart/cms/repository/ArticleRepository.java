package im.heart.cms.repository;

import java.math.BigInteger;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import im.heart.cms.entity.Article;

/**
 * 
 * @功能说明：文章接口
 * @作者 LKG
 */
public interface ArticleRepository extends JpaRepository<Article, BigInteger>, JpaSpecificationExecutor<Article> {
	@Transactional
	@Query(value="select count(1) from dic_cms_article model where model.id = :id",nativeQuery=true)
	public Boolean existsId(@Param("id") BigInteger id);
}
