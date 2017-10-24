package im.heart.usercore.repository;

import java.math.BigInteger;

import im.heart.usercore.entity.FrameRole;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 
 * @author gg
 * @Desc : 角色Repository
 */
public interface FrameRoleRepository extends JpaRepository<FrameRole, BigInteger> ,JpaSpecificationExecutor<FrameRole> {
	public  FrameRole findByRoleCode(String roleCode);
}
