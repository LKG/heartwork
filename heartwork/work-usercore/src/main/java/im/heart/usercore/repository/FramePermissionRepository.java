package im.heart.usercore.repository;

import im.heart.usercore.entity.FramePermission;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 
 * @author gg
 * @Desc : FramePermission Repository
 */
public interface FramePermissionRepository extends JpaRepository<FramePermission, BigInteger> ,JpaSpecificationExecutor<FramePermission> {

}
