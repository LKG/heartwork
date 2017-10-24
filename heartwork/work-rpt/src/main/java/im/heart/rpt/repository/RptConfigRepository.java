package im.heart.rpt.repository;

import im.heart.rpt.entity.RptConfig;
import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RptConfigRepository extends JpaRepository<RptConfig, BigInteger> ,JpaSpecificationExecutor<RptConfig> {

}
