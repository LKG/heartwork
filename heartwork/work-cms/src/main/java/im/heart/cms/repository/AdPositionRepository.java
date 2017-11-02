package im.heart.cms.repository;


import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import im.heart.cms.entity.AdPosition;

/**
 * 
 * @功能说明：AdPosition接口
 * @作者 LKG
 */
public interface AdPositionRepository extends JpaRepository<AdPosition, BigInteger> ,JpaSpecificationExecutor<AdPosition> {


}
