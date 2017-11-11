package im.heart.core.forbidden;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author gg
 * @desc 屏蔽关键词 //TODO 暂不处理
 */
public class ForbiddenWordUtils {
	protected static final Logger logger = LoggerFactory.getLogger(ForbiddenWordUtils.class);

	 /**
     * 默认的遮罩文字
     */
    protected static final String DEFAULT_MASK = "***";
    /**
     * 屏蔽关键词抓取的url
     */
    private static String forbiddenWordFetchURL;
	public static String getForbiddenWordFetchURL() {
		return forbiddenWordFetchURL;
	}
	public static void setForbiddenWordFetchURL(String forbiddenWordFetchURL) {
		ForbiddenWordUtils.forbiddenWordFetchURL = forbiddenWordFetchURL;
	}
    
    
}
