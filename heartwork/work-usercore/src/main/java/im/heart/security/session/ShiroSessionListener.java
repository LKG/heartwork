package im.heart.security.session;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
public class ShiroSessionListener implements SessionListener {
	protected static final Logger logger = LoggerFactory.getLogger(ShiroSessionListener.class);
	
	private static final AtomicInteger sessionCount = new AtomicInteger(0);

	@Override
	public void onStart(Session session) {
		// TODO Auto-generated method stub
		logger.info("回话个数+1=={}",sessionCount.get());
	}

	@Override
	public void onStop(Session session) {
		// TODO Auto-generated method stub
		logger.info("回话个数-1=={}",sessionCount.get());
	}

	@Override
	public void onExpiration(Session session) {
		// TODO Auto-generated method stub
		logger.info("登录过期-1== {}",sessionCount.get());
	}
	public int getSessionCount() {
        return sessionCount.get();
    }
}
