package im.heart.usercore.repository;



import im.heart.usercore.entity.FrameUser;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author gg
 * @desc 用户Repository
 */
public interface FrameUserRepository extends JpaRepository<FrameUser, BigInteger> ,JpaSpecificationExecutor<FrameUser> {
	/**
	 * 
	 * @Desc：根据用户名查找用户信息
	 * @param userName
	 * @return
	 */
	public FrameUser findByUserName(String userName); 
	
	/**
	 * 
	 * @Desc：根据邮箱查找
	 * @param userEmail
	 * @return
	 */
	public FrameUser findByUserEmail(String userEmail); 
	
	/**
	 * 
	 * @Desc：根据电话号码查找
	 * @param userPhone
	 * @return
	 */
	public FrameUser findByUserPhone(String userPhone); 
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE FrameUser model SET  model.headPortrait= :headPortrait WHERE  model.userId= :userId  ")
	public void updateUserheadPortrait(@Param("userId") BigInteger userId,@Param("headPortrait") String headPortrait);// 设置头像
	
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE FrameUser model SET  model.relateOrg.id= :defaultOrgId WHERE  model.userId= :userId  ")
	public void updateUserDefaultOrg(@Param("userId") BigInteger userId,@Param("defaultOrgId") BigInteger defaultOrgId);// 设置默认机构
}
