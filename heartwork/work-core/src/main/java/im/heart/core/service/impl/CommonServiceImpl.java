package im.heart.core.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import im.heart.core.service.CommonService;
/**
 * 
 * @author gg
 * @desc  共用服务实现类
 * @param <T>
 * @param <ID>
 */
public class CommonServiceImpl<T, ID extends Serializable> implements 	CommonService<T, ID> {
	@Autowired
	private JpaSpecificationExecutor<T> jpaSpecificationExecutor;
	@Autowired
	private JpaRepository<T, ID> jpaRepository;
	
	@Override
	public Page<T> findAll(Specification<T> spec, Pageable pageable) {
		return this.jpaSpecificationExecutor.findAll(spec,pageable);
	}

	@Override
	public List<T> findAll(Specification<T> spec) {
		return this.jpaSpecificationExecutor.findAll(spec);
	}

	@Override
	public Long count(Specification<T> spec) {
		return this.jpaSpecificationExecutor.count(spec);
	}

	@Override
	public T findOne(ID id) {
		return this.jpaRepository.findOne(id);
	}

	@Override
	public T  save(T entity) {
		return this.jpaRepository.save(entity);
	}

	@Override
	public void delete(ID id) {
		this.jpaRepository.delete(id);
	}

	@Override
	public void delete(Iterable<? extends T> entities) {
		this.jpaRepository.delete(entities);
	}

}
