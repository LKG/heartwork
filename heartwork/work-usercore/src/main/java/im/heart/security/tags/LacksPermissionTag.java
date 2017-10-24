package im.heart.security.tags;


public class LacksPermissionTag extends PermissionTag{

	protected boolean showTagBody(String p) {
        return !isPermitted(p);
    }

}
