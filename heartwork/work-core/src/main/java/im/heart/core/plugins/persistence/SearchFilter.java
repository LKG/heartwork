package im.heart.core.plugins.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author gg
 * @desc 搜索条件处理，参考springside 及ketayao-keta-custom
 */
public class SearchFilter {
	protected static final Logger logger = LoggerFactory.getLogger(SearchFilter.class);

	public enum Operator {
		EQ("EQ", "等价于 SQL 中的="), NOTEQ("NOTEQ", "等价于 SQL 中的!= <>"), 
		LIKE("LIKE", "等价于 SQL 中的like '%查询条件%' "), 
		NOTLIKE("NOTLIKE", "等价于 SQL 中的not like '%查询条件%' "), 
		LIKEP("LIKEP", "等价于 SQL 中的like '%查询条件' "), 
		NOTLIKEP("NOTLIKEP", "等价于 SQL 中的 not like '%查询条件%' "), 
		LIKES("LIKES", "等价于 SQL 中的 like '查询条件%' "),
		NOTLIKES("NOTLIKES", "等价于 SQL 中的 not like '查询条件%' "),
		GT("GT", "等价于 SQL 中的>"), 
		LT("LT", "等价于 SQL 中的<"), 
		GTE("GTE", "等价于 SQL 中的>="), 
		LTE("LTE", "等价于 SQL 中的<="), 
		ISNULL("null", "is null"), 
		ISNOTNULL("null",	"is not null"), 
		IN("in", "in"), 
		NOTIN("notin", "not in");
		private String code;
		private String desc;

		Operator(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

	}

	public String fieldName;
	public Object value;
	public Operator operator;

	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	/**
	 * @Desc：处理查询参数searchParams中key的格式为FIELDNAME_OPERATOR
	 * @param searchParams
	 * @return
	 */
	public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
			String[] names = StringUtils.split(key, "_");
			// 拆分operator与filedAttribute
			if (names.length == 0) {
				continue;// 忽略 单独的"_"
			}
			if (names.length != 2) {
				logger.debug(key + " is not a valid search filter name");
			}
			Operator operator = null;
			if (names.length <= 1) {
				operator = Operator.EQ;
			} else {
				operator = Operator.valueOf(names[1]);
			}
			String filedName = names[0];
			if (value instanceof String[]) {
				 String[] vals=( String[])value;
				for(String val:vals){
					if (StringUtils.isBlank(val)) {
						continue;
					}
					SearchFilter filter = new SearchFilter(filedName, operator, val);
					filters.put(key, filter);
				}
				continue;
			}
			if (StringUtils.isBlank((String) value)) {
				continue;
			}
			// 创建searchFilter
			SearchFilter filter = new SearchFilter(filedName, operator, value);
			filters.put(key, filter);
		}

		return filters;
	}
}