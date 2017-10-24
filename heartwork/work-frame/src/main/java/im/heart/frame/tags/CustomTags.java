
package im.heart.frame.tags;

import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;
@Component
public class CustomTags extends SimpleHash {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3072016765569327101L;

	public CustomTags() {
		super(new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_24).build());
		put("dict", new FrameDictItemTag());
	}
}
