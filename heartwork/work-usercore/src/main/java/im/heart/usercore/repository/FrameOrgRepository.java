package im.heart.usercore.repository;

import java.math.BigInteger;

import im.heart.usercore.entity.FrameOrg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
 * 
 * @author gg
 * @desc 机构表CROD 接口
 */
public interface FrameOrgRepository extends JpaRepository<FrameOrg, BigInteger> ,JpaSpecificationExecutor<FrameOrg> {



}
