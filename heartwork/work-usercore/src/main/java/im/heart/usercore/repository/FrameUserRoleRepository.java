package im.heart.usercore.repository;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import im.heart.usercore.entity.FrameUserRole;

/**
 * 
 * @author gg
 * @desc : 用户角色关联 Repository
 */ 
public interface FrameUserRoleRepository extends JpaRepository<FrameUserRole, BigInteger>  ,JpaSpecificationExecutor<FrameUserRole>{
	/**
	 * 
	 * @desc：根据用户id,查询用户拥有角色
	 * @param userId
	 * @return
	 */
	public List<FrameUserRole> findByUserId(BigInteger userId);
	

	
	@Modifying
	@Transactional
	@Query("DELETE FROM FrameUserRole model WHERE model.userId = :userId")
	public void deleteByUserId(@Param("userId")BigInteger userId);
}
