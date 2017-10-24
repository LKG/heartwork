package im.heart.conf;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * 
 * @author: gg
 * @desc :
 */
@Component
public class AppHealthIndicator implements HealthIndicator {
	@Override
	public Health health() {
		return Health.up().build();
	}
}
