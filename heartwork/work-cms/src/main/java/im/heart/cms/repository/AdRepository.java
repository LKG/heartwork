package im.heart.cms.repository;


import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import im.heart.cms.entity.Ad;

/**
 * 
 * @功能说明：Ad接口
 * @作者 LKG
 */
public interface AdRepository extends JpaRepository<Ad, BigInteger> ,JpaSpecificationExecutor<Ad> {


}
