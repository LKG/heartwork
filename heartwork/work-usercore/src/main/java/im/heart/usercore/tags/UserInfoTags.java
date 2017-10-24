
package im.heart.usercore.tags;

import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;
@Component
public class UserInfoTags extends SimpleHash {
	/**
	 * 
	 */
	private static final long serialVersionUID = -470260362141833832L;

	public UserInfoTags() {
		super(new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_24).build());
		put("orgs", new FrameUserOrgsTag());
		put("permission", new FramePermissionTag());
	}
}
