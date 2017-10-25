package im.heart.log.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import im.heart.log.entity.FrameLogLogin;
/**
 * 
 * @author gg
 * @desc 访问日志表CROD 接口
 */
public interface FrameLogLoginRepository extends JpaRepository<FrameLogLogin, BigInteger> ,JpaSpecificationExecutor<FrameLogLogin> {

}
