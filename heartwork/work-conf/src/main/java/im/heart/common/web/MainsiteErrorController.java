package im.heart.common.web;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import im.heart.core.CommonConst.RequestResult;
@Controller
public class MainsiteErrorController implements ErrorController {
	private static final String ERROR_PATH = "/error";

	@RequestMapping(value = ERROR_PATH)
	public ModelAndView handleError(ModelMap model){
		model.put(RequestResult.SUCCESS, true);
		model.put(RequestResult.HTTP_STATUS, HttpStatus.BAD_REQUEST.value());
		return new ModelAndView(RequestResult.PAGE_ERROR);
	}
	@RequestMapping(value = ERROR_PATH+"/404")
	public ModelAndView handle404Error(ModelMap model){
		model.put(RequestResult.SUCCESS, true);
		model.put(RequestResult.HTTP_STATUS, HttpStatus.NOT_FOUND.value());
		return new ModelAndView("errors/404");
	}
	@RequestMapping(value = ERROR_PATH+"/405")
	public ModelAndView handle405Error(ModelMap model){
		model.put(RequestResult.SUCCESS, true);
		model.put(RequestResult.HTTP_STATUS, HttpStatus.OK.value());
		return new ModelAndView("errors/405");
	}

	@RequestMapping(value = ERROR_PATH+"/500")
	public ModelAndView handle500Error(ModelMap model){
		model.put(RequestResult.SUCCESS, true);
		model.put(RequestResult.HTTP_STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ModelAndView("errors/500");
	}
	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

}
