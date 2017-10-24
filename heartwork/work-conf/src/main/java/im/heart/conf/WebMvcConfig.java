package im.heart.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.MultipartConfigElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.util.ResourceUtils;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Maps;
import freemarker.core.Configurable;
import im.heart.core.CommonConst;
import im.heart.core.support.view.JsonpView;

/**
 * 
 * @author: gg
 * @desc : spring mvc 配置
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	protected static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

	@Value("${prod.upload.path.root}")
	private String prodUploadFilePath = "";
	@Value("${info.app-version}")
	private String appVersion = "";

	/**
	 * 数据格式化处理
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {

	}

	@Bean
	public HttpMessageConverters customConverters() {
		return new HttpMessageConverters();
	}

	@Bean
	public JsonpView jsonpView() {
		return new JsonpView();
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//// 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
		factory.setMaxFileSize("100MB"); // KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("100MB");
		return factory.createMultipartConfig();
	}

	@Bean(name = "stringHttpMessageConverter")
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		supportedMediaTypes.add(MediaType.TEXT_HTML);
		supportedMediaTypes.add(MediaType.TEXT_PLAIN);
		stringHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
		return stringHttpMessageConverter;
	}

	@Bean
	public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
		return new ByteArrayHttpMessageConverter();
	}

	@Bean(name = "fastJsonHttpMessageConverter")
	public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		supportedMediaTypes.add(MediaType.TEXT_HTML);
		supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
		return fastJsonHttpMessageConverter;
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		super.configureContentNegotiation(configurer);
		configurer.ignoreAcceptHeader(true).defaultContentType(MediaType.TEXT_HTML);
		configurer.mediaType("jhtml", MediaType.TEXT_HTML);
	}

	@Autowired
	private FreeMarkerProperties properties;

	protected void applyProperties(FreeMarkerConfigurationFactory factory) {
		factory.setTemplateLoaderPaths(this.properties.getTemplateLoaderPath());
		factory.setPreferFileSystemAccess(this.properties.isPreferFileSystemAccess());
		factory.setDefaultEncoding(this.properties.getCharsetName());
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("ver", appVersion);
		factory.setFreemarkerVariables(variables);
		Properties settings = new Properties();
		settings.put(freemarker.template.Configuration.NUMBER_FORMAT_KEY_SNAKE_CASE, "0.######");
		settings.put(freemarker.template.Configuration.BOOLEAN_FORMAT_KEY, "true,false");
		settings.put(freemarker.template.Configuration.DATETIME_FORMAT_KEY, "yyyy-MM-dd HH:mm:ss");
		settings.put(freemarker.template.Configuration.DATE_FORMAT_KEY, "yyyy-MM-dd");
		settings.put(freemarker.template.Configuration.TIME_FORMAT_KEY, "HH:mm:ss");
		settings.put(freemarker.template.Configuration.WHITESPACE_STRIPPING_KEY, "true");
		settings.put(freemarker.template.Configuration.TAG_SYNTAX_KEY, "auto_detect");
		settings.put(Configurable.CLASSIC_COMPATIBLE_KEY, "true");

		settings.putAll(this.properties.getSettings());
		factory.setFreemarkerSettings(settings);
	}

	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		this.applyProperties(configurer);
		return configurer;
	}

	@Bean
	public FreeMarkerConfigurationFactoryBean freeMarkerConfiguration() {
		FreeMarkerConfigurationFactoryBean factory = new FreeMarkerConfigurationFactoryBean();
		this.applyProperties(factory);
		return factory;
	}

	@Bean
	public FreeMarkerViewResolver freeMarkerViewResolver() {
		FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
		this.properties.applyToViewResolver(viewResolver);
		viewResolver.setRequestContextAttribute("request");
		return viewResolver;
	}

	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);
		List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
		InternalResourceViewResolver viewRresolver = new InternalResourceViewResolver();
		viewRresolver.setOrder(1);
		viewResolvers.add(freeMarkerViewResolver());
		resolver.setViewResolvers(viewResolvers);
		List<View> views = new ArrayList<View>();
		views.add(jsonpView());
		resolver.setDefaultViews(views);
		return resolver;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(byteArrayHttpMessageConverter());
		converters.add(stringHttpMessageConverter());
		converters.add(fastJsonHttpMessageConverter());
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/" + CommonConst.STATIC_UPLOAD_ROOT + "/**")
				.addResourceLocations(ResourceUtils.FILE_URL_PREFIX + prodUploadFilePath);
		super.addResourceHandlers(registry);
	}
}
