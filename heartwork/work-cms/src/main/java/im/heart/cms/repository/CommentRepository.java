package im.heart.cms.repository;


import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import im.heart.cms.entity.Comment;

/**
 * 
 * @功能说明：评论接口
 * @作者 LKG
 */
public interface CommentRepository extends JpaRepository<Comment, BigInteger> ,JpaSpecificationExecutor<Comment> {


}
