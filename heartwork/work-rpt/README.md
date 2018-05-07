# 程序设计之动态报表
------
## 程序设计应该采用渐进式 ，程序是进化来的
## 需求说明
 - 需要动态形成报表，实现报表权限可控制
 - 支持SQL语句形式，支持java 代码解析方式，支持外部嵌入式报表
 - 方式SQL 注入
 - 支持简单的 **if** 语法及三目运算 ，支持设置默认值
## 概要设计
程序需要支持多种解析类型 
统一数据入口：
    （可以进行统一的数据解析，权限认证，方便做统一的加密解密及数据压缩等操作）
## 设计说明
### 1. 表结构设计
| 字段名        | 数据类型   |  约束  |描述  |
| --------   | -----:  | :----:  | :----:  |
| rpt_id     | bigint(32) |NOT NULL AUTO_INCREMENT PRIMARY KEY  |   报表Id     |
| rpt_code   | varchar(64)  |   NOT NULL   |   报表代号     |
| rpt_name   | varchar(128)  |   NOT NULL   |   报表名称    |
| rpt_type     |varchar(64)  |   NOT NULL   |   报表类型     |
| rpt_order     | int(11) |   NOT NULL |   排序号     |
| rpt_url     | varchar(128) |   NOT NULL |   报表展示地址    |
| rpt_cri_type     | enum('sql','java','stored','other') | NOT NULL DEFAULT 'sql' |   解析类型   |
| rpt_cri_cont     | text |   NOT NULL |   SQL语句 或者Java类绝对路径    |
| group_id     | bigint(32) |   NOT NULL DEFAULT '1000'  |   分组id    |
| status     | enum('pending','enabled','disabled') |   NOT NULL DEFAULT 'enabled'   |   状态    |
| create_time  | datetime |   NOT NULL   |   创建日期    |
| modi_time     | datetime |   NOT NULL  |   修改日期    |
| remark     | varchar(256) |   NOT NULL  |   描述    |





### 防止sql 注入处理
```java
    /**
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
```
### 逻辑条件处理
```java
    protected static final String openTag = "{{";
	protected static final String endTag = "}}";
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
```
### 解析处理sqlCont
```java
    protected static final String openTag = "{{";
	protected static final String endTag = "}}";
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
					sqlCont = StringUtils.replaceOnce(sqlCont, openTag + arg+ endTag, defaultValue);
				}
			}
		}
		sqlCont=sqlCont.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");//替换空行
		return sqlCont;
	}
```
