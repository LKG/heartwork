package im.heart.usercore.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import im.heart.usercore.entity.FrameUserRelate;

/**
 * 
 * @author gg
 * @Desc : 用户关系表  Repository
 */
public interface FrameUserRelateRepository extends JpaRepository<FrameUserRelate, BigInteger> ,JpaSpecificationExecutor<FrameUserRelate> {
	
}
