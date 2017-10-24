package im.heart.security.tags;


import org.apache.shiro.subject.Subject;


public class HasAnyRolesTag extends RoleTag{

	  // Delimeter that separates role names in tag attribute
    private static final String ROLE_NAMES_DELIMETER = ",";
    @Override
    protected boolean showTagBody(String roleNames) {
        boolean hasAnyRole = false;
        Subject subject = getSubject();
        if (subject != null) {
            // Iterate through roles and check to see if the user has one of the roles
            for (String role : roleNames.split(ROLE_NAMES_DELIMETER)) {
                if (subject.hasRole(role.trim())) {
                    hasAnyRole = true;
                    break;
                }
            }
        }

        return hasAnyRole;
    }

}
