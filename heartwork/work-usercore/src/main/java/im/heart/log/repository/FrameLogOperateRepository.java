package im.heart.log.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import im.heart.log.entity.FrameLogOperate;
/**
 * 
 * @author gg
 * @desc 操作日志CROD 接口
 */
public interface FrameLogOperateRepository extends JpaRepository<FrameLogOperate, BigInteger> ,JpaSpecificationExecutor<FrameLogOperate> {



}
