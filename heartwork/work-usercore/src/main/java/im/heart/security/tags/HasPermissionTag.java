package im.heart.security.tags;


public class HasPermissionTag extends PermissionTag{

	@Override
	protected boolean showTagBody(String p) {
		return isPermitted(p);
	}
}
