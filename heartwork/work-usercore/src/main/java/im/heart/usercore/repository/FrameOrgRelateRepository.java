package im.heart.usercore.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import im.heart.usercore.entity.FrameOrgRelate;

/**
 * 
 * @author gg
 * @Desc : 机构关联表，设置机构非直属关联关系  Repository
 */
public interface FrameOrgRelateRepository extends JpaRepository<FrameOrgRelate, BigInteger> ,JpaSpecificationExecutor<FrameOrgRelate> {
	
}
