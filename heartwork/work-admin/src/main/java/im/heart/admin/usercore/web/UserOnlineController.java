package im.heart.admin.usercore.web;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import im.heart.core.web.AbstractController;
import im.heart.security.session.OnlineSession;
import im.heart.security.session.ShiroSessionDAO;
@Controller
@RequestMapping("/admin")
public class UserOnlineController extends AbstractController {
	protected static final String apiVer = "/online";
	protected static final String VIEW_LIST="admin/usercore/user_online_list";
	@Autowired
	private ShiroSessionDAO shiroSessionDAO;

	@RequiresPermissions("online:view")
	@RequestMapping(apiVer+"s")  
    public ModelAndView list(ModelMap model) {  
        Collection<Session> sessions = this.shiroSessionDAO.getActiveSessions();  
        super.success(model,sessions);
        Serializable sessionId = SecurityUtils.getSubject().getSession().getId();
        model.addAttribute("sessionId", sessionId);  
        model.addAttribute("sesessionCount", sessions.size());  
		return new ModelAndView(VIEW_LIST);
    }  
	@RequiresPermissions("online:delete")
	@RequestMapping(apiVer+"/forceLogout")
	public ModelAndView forceLogout(@RequestParam(value = "ids") String[] ids,ModelMap model){
		Serializable sessionId = SecurityUtils.getSubject().getSession().getId();
		for (String id : ids) {		
			if(sessionId.equals(id)){
				continue;
			}
	       OnlineSession online = (OnlineSession) this.shiroSessionDAO.readSession(id);
	       if (online == null) {
	          continue;
	       }
	       online.setStatus(OnlineSession.OnlineStatus.force_logout);
	     }
		super.success(model);
		return new ModelAndView(VIEW_LIST);
	}
}
