package im.heart.cms.repository;


import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import im.heart.cms.entity.FrameNotice;

/**
 * 
 * @功能说明：FrameNotice接口
 * @作者 LKG
 */
public interface FrameNoticeRepository extends JpaRepository<FrameNotice, BigInteger> ,JpaSpecificationExecutor<FrameNotice> {


}
