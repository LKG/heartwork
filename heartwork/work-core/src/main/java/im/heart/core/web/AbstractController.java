package im.heart.core.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import im.heart.core.CommonConst;
import im.heart.core.CommonConst.RequestResult;
import im.heart.core.support.BigDecimalEditorSupport;
import im.heart.core.support.DateEditorSupport;
import im.heart.core.support.StringEscapeEditorSupport;
/**
 * 
 * @author gg
 * @desc 基础控制器，提供日志记录，记录查询条件，文件上传功能 等一些共用方法
 */
public abstract class AbstractController {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

	/**
	 * 统一的错误页面
	 */
	protected static final String RESULT_PAGE = RequestResult.PAGE;
	protected static final String VIEW_SUCCESS = RequestResult.PAGE_SUCCESS;
	private String viewPrefix;

	/**
	 * 
	 * @Desc： 当前模块 视图的前缀 默认 1、获取当前类头上的@RequestMapping中的value作为前缀
	 * 2、如果没有就使用当前模型小写的简单类名
	 * 
	 * @param viewPrefix
	 */
	public void setViewPrefix(String viewPrefix) {
		if (viewPrefix.startsWith("/")) {
			viewPrefix = viewPrefix.substring(1);
		}
		this.viewPrefix = viewPrefix;
	}

	public String getViewPrefix() {
		return viewPrefix;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// 处理BigDecimal
		binder.registerCustomEditor(BigDecimal.class, new BigDecimalEditorSupport());
		// 处理Date
		binder.registerCustomEditor(Date.class, new DateEditorSupport());
		// 用于防止XSS攻击
		binder.registerCustomEditor(String.class, new StringEscapeEditorSupport());
		// 注册共用校验器
	}

	protected void success(ModelMap model) {
		model.put(RequestResult.SUCCESS, true);
		model.put(RequestResult.HTTP_STATUS, HttpStatus.OK.toString());
	}

	protected void success(ModelMap model, Object attributeValue) {
		this.success(model, RequestResult.RESULT, attributeValue);
	}

	protected void success(ModelMap model, String datakey, Object attributeValue) {
		this.success(model);
		model.put(datakey, attributeValue);
	}

	protected void fail(ModelMap model) {
		model.put(RequestResult.SUCCESS, false);
		model.put(RequestResult.HTTP_STATUS, HttpStatus.OK.toString());
	}

	protected void fail(ModelMap model, String datakey, Object attributeValue) {
		this.fail(model);
		model.put(datakey, attributeValue);
	}

	protected void fail(ModelMap model, Object attributeValue) {
		this.fail(model, RequestResult.RESULT, attributeValue);
	}

	protected void error(ModelMap model, String datakey, Object attributeValue) {
		this.fail(model);
		model.put(datakey, attributeValue);
	}

	protected void error(ModelMap model, Object attributeValue) {
		this.error(model, RequestResult.RESULT, attributeValue);
	}

	/**
	 * 
	 * @Desc：根据参数名获取SESSION 对应值
	 * @param request
	 * @param paraName
	 * @return
	 */
	protected Object getSessionPara(HttpServletRequest request, String paraName) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Object para = session.getAttribute(paraName);
			if (para != null) {
				return para;
			}
		}
		return null;
	}

	/**
	 * 
	 * @Desc：设置session
	 * @param request
	 * @param paraName
	 * @param para
	 */
	protected void setSessionPara(HttpServletRequest request, String paraName, Object para) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute(paraName, para);
		}
	}

	/**
	 * 
	 * @Desc：backURL null 将重定向到默认getViewPrefix()
	 * @param backURL
	 * @return
	 */
	protected String redirectToUrl(String backURL) {
		if (StringUtils.isBlank(backURL)) {
			backURL = getViewPrefix();
		}
		if (!backURL.startsWith("/") && !backURL.startsWith("http") && !backURL.startsWith("https")) {
			backURL = "/" + backURL;
		}
		return "redirect:" + backURL;
	}

	/**
	 * @Desc：获取当前请求根路径
	 * @param req
	 * @return
	 */
	protected String getBasePath(HttpServletRequest req) {
		StringBuffer baseUrl = new StringBuffer();
		String scheme = req.getScheme();
		int port = req.getServerPort();
		baseUrl.append(scheme);
		baseUrl.append("://");
		baseUrl.append(req.getServerName());
		if (("http".equals(scheme) && port != 80) || ("https".equals(scheme) && port != 443)) {
			baseUrl.append(':');
			baseUrl.append(req.getServerPort());
		}
		return baseUrl.toString();
	}

	/**
	 * @Desc：获取上次请求地址
	 * @param request
	 * @return
	 */
	protected String extractBackURL(HttpServletRequest request) {
		String url = request.getParameter(CommonConst.BACK_URL);

		if (StringUtils.isEmpty(url)) {
			url = request.getHeader("Referer");
		}
		if (!StringUtils.isEmpty(url) && (url.startsWith("http://") || url.startsWith("https://"))) {
			return url;
		}
		if (!StringUtils.isEmpty(url) && url.startsWith(request.getContextPath())) {
			url = getBasePath(request) + url;
		}
		return url;
	}

	/**
	 *
	 * @Desc：文件上传
	 * @param file
	 * @param path
	 * @return
	 * @throws Exception
	 */
	protected String uploadFile(MultipartFile file, String path, String fileName) throws Exception {
		FileOutputStream out = null;
		try {
			File folder = new File(path);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			if (StringUtils.isBlank(fileName)) {
				fileName = file.getOriginalFilename();
			}
			
			fileName = System.nanoTime() + "_" + fileName;
			String filePath = path + File.separator + fileName;
			out = new FileOutputStream(filePath);
			out.write(file.getBytes());
			return fileName;
		} catch (Exception e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * @Desc：公共下载方法
	 * @param response
	 * @param file
	 * @param fileName
	 * @param delFile
	 * @return
	 * @throws Exception
	 */
	public HttpServletResponse downFile(HttpServletResponse response, File file, String fileName, boolean delFile,
			boolean inline) throws Exception {
		response.setContentType("application/x-download");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		OutputStream out = null;
		BufferedInputStream br = null;
		try {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		if (inline) {
			response.setHeader("Content-Disposition", "inline; filename=" + fileName + ";");
		} else {// 纯下载方式
			response.addHeader("Content-disposition", "attachment;filename=" + fileName);// 设定输出文件头
		}
		try {
			out = response.getOutputStream();
			byte[] buf = new byte[1024];
			int len = 0;
			br = new BufferedInputStream(new FileInputStream(file));
			while ((len = br.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			br.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new Exception("下载失败!");
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(br);
			if (delFile) {
				file.delete();
			}
		}
		return response;
	}

	/**
	 * 
	 * @Desc：文件上传
	 * @param file
	 * @param path
	 * @return
	 * @throws Exception
	 */
	protected String uploadFile(MultipartFile file, String path) throws Exception {
		return uploadFile(file, path, null);
	}

	/**
	 * 
	 * @Desc：获取上传文件列表信息 ？@RequestParam MultipartFile file[],
	 * @param request
	 * @return
	 */
	protected List<MultipartFile> getFileList(HttpServletRequest request) {
		List<MultipartFile> fileList = new ArrayList<MultipartFile>();
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
				String key = (String) it.next();
				MultipartFile file = multipartRequest.getFile(key);
				if (file.getOriginalFilename().length() > 0) {
					fileList.add(file);
				}
			}
		}
		return fileList;
	}
}