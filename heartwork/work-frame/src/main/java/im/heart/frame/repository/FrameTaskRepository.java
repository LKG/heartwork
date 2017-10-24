package im.heart.frame.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import im.heart.frame.entity.FrameTask;
/**
 * 
 * @author gg
 * @desc : FrameTask CROD 接口
 */
public interface FrameTaskRepository extends JpaRepository<FrameTask, BigInteger> ,JpaSpecificationExecutor<FrameTask> {
	@Modifying
	@Transactional
	@Query("DELETE FROM FrameTask model WHERE model.taskId  in (:ids)")
	public void delete(@Param("ids")BigInteger[] ids);
}
