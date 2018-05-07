package im.heart.rpt.api;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;

import im.heart.core.utils.StringUtilsEx;
import im.heart.core.web.AbstractController;
import im.heart.core.web.utils.WebUtilsEx;
import im.heart.rpt.entity.RptConfig;
import im.heart.rpt.service.RptConfigService;
/**
 * 
 * @author gg
 * @desc 报表配置控制器 功能： 读取配置表中的信息根据配置表中的信息， 根据配置表里的信息，调用相应的类查询数据 并把数据返回到配置表里指定的页面 ，配置器内不负责对数据解析， 数据的解析交给配置表中指定的类完成，或者交给页面完成； 
 */
@Controller
@RequestMapping("/api")
public class SysRptController extends AbstractController {

	protected static final Logger logger = LoggerFactory.getLogger(SysRptController.class);

	protected static final String apiVer = "/sysRpt";
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private RptConfigService rptConfigService;

	protected static final String openTag = "{{";
	protected static final String endTag = "}}";
	
	/**
	 * 
	 * @功能说明：防止sql 注入
	 * @param sSql
	 * @return
	 */
	public static String filterSql(String sSql){
		Map<String,String> tokens = new HashMap<String,String>();
		tokens.put("exec", " ");
		tokens.put("delete", " ");
		tokens.put("alter", " ");
		tokens.put("drop", " ");
		tokens.put("master", " ");
		tokens.put("truncate", " ");
		tokens.put("declare", " ");
		tokens.put("create", " ");
		tokens.put("update", " ");
		sSql=StringUtilsEx.replace(sSql,tokens,true,true);
		return sSql.replace(" xp_", "no");
	}

	/**
	 * 
	 * @功能说明： 处理逻辑条件 {{if test="query" }}{{/if}}
	 * @param sqlCont
	 * @param reqargs
	 * @return
	 */
	private String logic(String sqlCont, Map<String, Object> reqargs) {
		String open = openTag + "if";
		String colse = "if" + endTag;
		if (sqlCont.indexOf(openTag) < 0) {// 是否有逻辑判断
			return sqlCont;
		}
		String[] templates = StringUtils.substringsBetween(sqlCont, open,endTag);
		if (templates != null) {
			for (String logic : templates) {
				//抽取条件
				String propTest = StringUtilsEx.groupString(logic,"test=\"(.+?)\"",1);
				String replacement = "";
				if (StringUtils.isNotBlank(propTest)) {
					if (reqargs.containsKey(propTest)) {
						String reqVal = reqargs.get(propTest).toString();
						if (StringUtils.isNotBlank(reqVal)) {
						   replacement= StringUtils.substringBetween(logic, openTag,endTag);
//						   replacement= StringUtils.substringBeforeLast(logic, openTag);
//				   		   replacement= StringUtils.substringAfter(replacement, endTag);
						}
					}

				}
				sqlCont = StringUtils.replaceOnce(sqlCont, open + logic+ colse, replacement);
			}
			return sqlCont;
		}
		return sqlCont;
	}

	/**
	 * 
	 * @功能说明：处理SQL
	 * @param sqlCont
	 * @param reqargs
	 * @return
	 */
	private String buildSQL(String sqlCont, Map<String, Object> reqargs) {
		if (sqlCont.indexOf(openTag) > 0) {
			// 判断是否有逻辑处理
			sqlCont = this.logic(sqlCont, reqargs);
			String[] temp = StringUtils.substringsBetween(sqlCont, openTag, endTag);
			if (temp != null) {
				for (String arg : temp) {
					int index = arg.indexOf(":");
					String defaultValue = "";
					String strTemp = arg;
					if (index > 0) {// 判断是否有默认值
						strTemp = arg.substring(0, index);
						defaultValue = arg.substring(index + 1);
					}
					if (reqargs.containsKey(strTemp)) {// 判断是否传递的有参数
						String reqargsValue = reqargs.get(strTemp).toString();
						if (StringUtils.isNotBlank(reqargsValue)) {
							defaultValue = reqargsValue;
						}

					}
					sqlCont = StringUtils.replaceOnce(sqlCont, openTag + arg	+ endTag, defaultValue);
				}
			}
		}
		sqlCont=sqlCont.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");//替换空行
		return sqlCont;
	}
	/**
	 * @功能说明：根据报表Id 查询信息
	 * @param request
	 * @param rptId
	 * @return
	 */
	protected List<Map<String, Object>> queryForList( HttpServletRequest request,RptConfig po){
		List<Map<String, Object>> datas=Lists.newArrayList();
		if (po != null) {
			String rptCont = po.getRptCriCont();
			Map<String, Object> reqargs = WebUtilsEx.getParametersStartingWithEx(request, null);
			if (RptConfig.RptCriType.sql == po.getRptCriType()) {
				rptCont = buildSQL(rptCont, reqargs);
				logger.debug("sql:" + rptCont);
				rptCont=filterSql(rptCont);
				logger.debug("filterend:" + rptCont);
				datas = this.jdbcTemplate.queryForList(rptCont);
			}
		}
		return datas;
	}
	
	/**
	 * 
	 * @功能说明：根据报表Id 查询项目信息
	 * @param jsoncallback
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = apiVer + "/{rptId}")
	protected ModelAndView findById(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			@PathVariable BigInteger rptId, HttpServletRequest request,
			ModelMap model) throws UnsupportedEncodingException {
		RptConfig po = this.rptConfigService.findOne(rptId);
		if (po != null) {
			List<Map<String, Object>> datas = queryForList(request, po);
			super.success(model, "data", datas);
			return new ModelAndView(po.getRptUrl());
		}
		super.fail(model);
		return new ModelAndView(RESULT_PAGE);
	}

}
