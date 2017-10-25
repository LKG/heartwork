package im.heart.frame.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import im.heart.frame.entity.FrameTpl;
/**
 * 
 * @author gg
 * @Desc : FrameTpl CROD 接口
 */
public interface FrameTplRepository extends JpaRepository<FrameTpl, BigInteger> ,JpaSpecificationExecutor<FrameTpl> {


}
