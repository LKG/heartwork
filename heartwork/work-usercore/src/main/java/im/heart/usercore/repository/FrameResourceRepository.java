package im.heart.usercore.repository;

import im.heart.usercore.entity.FrameResource;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 
 * @author gg
 * @Desc :资源表Repository
 */
public interface FrameResourceRepository extends JpaRepository<FrameResource, BigInteger> ,JpaSpecificationExecutor<FrameResource> {
	/**
	 * 
	 * @desc：根据ResourceCode名查找FrameResource信息
	 * @param userName
	 * @return
	 */
	public FrameResource findByResourceCode(String resourceCode); 
}
