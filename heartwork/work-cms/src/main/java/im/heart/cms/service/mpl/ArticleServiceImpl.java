package im.heart.cms.service.mpl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import im.heart.cms.entity.Article;
import im.heart.cms.repository.ArticleRepository;
import im.heart.cms.service.ArticleService;
import im.heart.core.service.impl.CommonServiceImpl;

@Service(value = ArticleService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class ArticleServiceImpl extends CommonServiceImpl<Article, BigInteger> implements ArticleService {
	@Autowired
	private ArticleRepository articleRepository;
	@Override
	public List<Article> save(Iterable<Article> entities) {
		return this.articleRepository.save(entities);
	}

	@Override
	public Article save(Article entitie) {
		return this.articleRepository.save(entitie);
	}

	@Override
	public boolean exists(BigInteger id) {
		return this.articleRepository.existsId(id);
	}

}
