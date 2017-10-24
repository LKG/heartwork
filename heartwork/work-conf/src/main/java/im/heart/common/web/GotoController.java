package im.heart.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import im.heart.core.web.AbstractController;


@Controller
public class GotoController extends AbstractController {
	protected static final String apiVer = "/index";
	protected static final String VIEW="index";
	@RequestMapping(value={"/",apiVer,apiVer+"/"})
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response,
			ModelMap model){
		super.success(model);
		return new ModelAndView(VIEW);
	}
	/**
	 * 跳转到其他网站
	 * @param url
	 * @return
	 */
	@RequestMapping(value="/www/goto",method=RequestMethod.GET)
	public ModelAndView jump(HttpServletRequest request,String u,ModelMap model){
		super.success(model);
		return new ModelAndView("www/go_to","url",u);
	}
}
