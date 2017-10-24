package im.heart.usercore.repository;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import im.heart.usercore.entity.FrameUserOrg;

/**
 * 
 * @author gg
 * @Desc : 用户机构关联 Repository
 */
public interface FrameUserOrgRepository extends JpaRepository<FrameUserOrg, BigInteger> ,JpaSpecificationExecutor<FrameUserOrg>{
	/**
	 * 
	 * @Desc：根据用户id,查询用户拥有机构
	 * @param userId
	 * @return
	 */
	public List<FrameUserOrg> findByUserId(BigInteger userId);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM FrameUserOrg model WHERE model.userId = :userId")
	public void deleteByUserId(@Param("userId")BigInteger userId);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE FrameUserOrg model SET  model.isDefault= (CASE  WHEN model.relateId = :relateId  THEN true ELSE false END)  WHERE  model.userId= :userId")
	public void updateUserDefaultOrg(@Param("userId") BigInteger userId,@Param("relateId") BigInteger relateId);// 设置默认机构
}
