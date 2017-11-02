package im.heart.cms.repository;


import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import im.heart.cms.entity.FriendLink;

/**
 * 
 * @功能说明：FriendLink 接口
 * @作者 LKG
 */
public interface FriendLinkRepository extends JpaRepository<FriendLink, BigInteger> ,JpaSpecificationExecutor<FriendLink> {


}
