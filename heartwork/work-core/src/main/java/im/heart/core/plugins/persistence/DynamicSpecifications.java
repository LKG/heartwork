package im.heart.core.plugins.persistence;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import im.heart.core.plugins.persistence.SearchFilter.Operator;
import im.heart.core.utils.DateUtilsEx;
import im.heart.core.utils.ReflectUtils;
import im.heart.core.utils.StringToBooleanUtils;
import im.heart.core.utils.StringUtilsEx;
import im.heart.core.web.utils.WebUtilsEx;

/**
 * 
 * @author gg
 * @desc 动态拼接查询条件 
 */
public class DynamicSpecifications {
	protected static final Logger logger = LoggerFactory.getLogger(DynamicSpecifications.class);
	/**
	 *  用于存储每个线程的request请求
	 */
	protected static final ThreadLocal<HttpServletRequest> LOCAL_REQUEST = new ThreadLocal<HttpServletRequest>();

	public static void putRequest(HttpServletRequest request) {
		LOCAL_REQUEST.set(request);
	}

	public static HttpServletRequest getRequest() {
		return LOCAL_REQUEST.get();
	}

	public static void removeRequest() {
		LOCAL_REQUEST.remove();
	}
	/**
	 * 
	 * @Desc：解析参数
	 * @param request
	 * @return
	 */
	public static Map<String, SearchFilter> buildSearchFilterMap(HttpServletRequest request) {
		Map<String, Object> parameters = WebUtilsEx.getParameters(request,false); ;
		return SearchFilter.parse(parameters);
	}
	
	/**
	 * 
	 * @Desc：解析参数  不可动态添加数据
	 * @param request
	 * @return
	 */
	public static final Collection<SearchFilter> buildFinalSearchFilters(HttpServletRequest request) {
		Map<String, SearchFilter> maps = buildSearchFilterMap(request);
		if(!maps.values().isEmpty()){
			return maps.values();
		}
		return new HashSet<SearchFilter>();
	}
	/**
	 * 
	 * @Desc：解析参数  不可动态添加数据
	 * @param request
	 * @return
	 */
	public static Collection<SearchFilter> buildSearchFilters(HttpServletRequest request) {
		Map<String, SearchFilter> maps = buildSearchFilterMap(request);
		Collection<SearchFilter> filters = new HashSet<SearchFilter>();
		filters.addAll(maps.values());
		return filters;
	}
	/**
	 * 
	 * @Desc：：处理查询参数，动态拼接查询条件
	 * @param request
	 * @param entityClazz
	 * @return
	 */
	public static <T> Specification<T> bySearchFilter(HttpServletRequest request, final Class<T> entityClazz) {
		final Collection<SearchFilter> filters = buildFinalSearchFilters(request);
		return bySearchFilter(filters, entityClazz);
	}

	/**
	 * 
	 * @Desc：
	 * @param entityClazz
	 * @return
	 */
	public static <T> Map<String, String> getDeclaredFieldsMap(final Class<T> entityClazz) {
		Map<String, String> fieldMap = new HashMap<String, String>();
		Field[] fields = entityClazz.getDeclaredFields();
		for (Field field : fields) {
			fieldMap.put(field.getName(), field.getName());
		}
		return fieldMap;
	}

	/**
	 * 
	 * @Desc：处理查询参数，动态拼接查询条件
	 * @param filters
	 * @param entityClazz
	 * @return
	 */
	public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> entityClazz) {
		final Map<String, String> fieldMap = getDeclaredFieldsMap(entityClazz);
		Specification<T> specification = new Specification<T>() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (!filters.isEmpty()) {
					List<Predicate> predicates = new ArrayList<Predicate>();
					for (SearchFilter filter : filters) {
						String[] names = StringUtils.split(filter.fieldName, ".");
						if (!fieldMap.containsKey(names[0])) {
							// TODO: 考虑处理如Task的名为"user.name"的filedName, 转换问题
							logger.debug(filter.fieldName + " is not entity field " + entityClazz);
							continue;
						}
						// nested path translate, 如Task的名为"user.name"的filedName,
						// 转换为Task.user.name属性
						Path<Object> expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}
						// 处理查询条件
						// 判断数据类型，进行类型转换
						Class classz = expression.type().getJavaType();
						Object val = filter.value;
						Operator operator = filter.operator;
						if (classz.equals(Date.class)) {
							if (!(val instanceof Date)) {
								logger.debug("val is not Date");
								try {
									String dataTime=val.toString();
									if (Operator.LT.equals(operator) || Operator.LTE.equals(operator)) {
										if (DateUtilsEx.isValidDate(dataTime)) {
											dataTime = dataTime+ " 23:59:59";
										}
									}
									 val=DateUtilsEx.parseDateStrictly(dataTime, 
											 "yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm","yyyy-MM-dd HH","yyyy-MM-dd","yyyy-MM","yyyy");
								} catch (ParseException e) {
									logger.error(e.getMessage(), e);
								}
							}
						}
						// 枚举类型转换
						if (classz.isEnum()) {// 枚举类型转换
							logger.debug("枚举类型......");
							if (StringUtilsEx.isNumeric(val.toString())) {
								Method method = ReflectUtils.getMethod(classz, true, false, "findByIntValue", int.class);
								if(method!=null){
									try {
										Integer intVal = Integer.valueOf(val.toString());
										val=method.invoke(Integer.class,intVal);
									} catch (Exception e) {
										logger.warn(e.getMessage(), e);
										continue;
									}
								}
							}else{
								val = EnumUtils.getEnum(classz, val.toString());
							}
						}
						if (classz.equals(String.class)) {
							val=StringUtilsEx.trim(val.toString());
						}
						if (classz.equals(Boolean.class)) {
							logger.debug("Boolean类型......");
							val=StringToBooleanUtils.convert(val.toString());
						}
						
						switch (operator) {
						case EQ:
							predicates.add(builder.equal(expression, val));
							break;
						case NOTEQ:
							predicates.add(builder.notEqual(expression, val));
							break;
						case LIKEP:
							predicates.add(builder.like((Expression)expression, "%" + val));
							break;
						case NOTLIKEP:
							predicates.add(builder.notLike((Expression)expression, "%" + val));
							break;
						case LIKES:
							predicates.add(builder.like((Expression)expression, val + "%"));
							break;
						case NOTLIKES:
							predicates.add(builder.notLike((Expression)expression, val + "%"));
							break;
						case LIKE:
							predicates.add(builder.like((Expression)expression, "%" + val + "%"));
							break;
						case NOTLIKE:
							predicates.add(builder.notLike((Expression)expression, "%" + val + "%"));
							break;
						case GT:
							predicates.add(builder.greaterThan((Expression)expression, (Comparable) val));
							break;
						case LT:
							predicates.add(builder.lessThan((Expression)expression, (Comparable) val));
							break;
						case GTE:
							predicates.add(builder.greaterThanOrEqualTo((Expression)expression, (Comparable) val));
							break;
						case LTE:
							predicates.add(builder.lessThanOrEqualTo((Expression)expression, (Comparable) val));
							break;
						case ISNULL:
							predicates.add(builder.isNull(expression));
							break;
						case ISNOTNULL:
							predicates.add(builder.isNotNull(expression));
							break;
						default:
							break;
						}

					}
					// 将所有条件用 and 联合起来
					if (!predicates.isEmpty()) {
						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}
				return builder.conjunction();
			}
		};
		return specification;
	}
}