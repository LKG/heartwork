package im.heart.frame.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;

import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.core.plugins.persistence.SearchFilter;
import im.heart.core.plugins.persistence.SearchFilter.Operator;
import im.heart.core.service.impl.CommonServiceImpl;
import im.heart.frame.entity.FrameTpl;
import im.heart.frame.repository.FrameTplRepository;
import im.heart.frame.service.FrameTplService;
import im.heart.frame.service.TemplateService;
@Service(value = FrameTplService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class FrameTplServiceImpl extends CommonServiceImpl<FrameTpl, BigInteger> implements FrameTplService, TemplateService<FrameTpl, BigInteger>,ServletContextAware {
	protected static final Logger logger = LoggerFactory.getLogger(FrameTplServiceImpl.class);
	/** servletContext */
	private ServletContext servletContext;
	@Autowired
	private FrameTplRepository frameTplRepository;
	
	@Autowired
	private FreeMarkerProperties properties;
	
	@Override
	public boolean exists(String tplCode) {
		final Collection<SearchFilter> filters = new HashSet<SearchFilter>();
		filters.add(new SearchFilter("tplCode", Operator.EQ, tplCode));
		Specification<FrameTpl> spec = DynamicSpecifications.bySearchFilter(filters, FrameTpl.class);
		long count = this.frameTplRepository.count(spec);
		if(count>0){
			return true;
		}
		return false;
	}
	@Override
	@Cacheable("template")
	public FrameTpl findOne(BigInteger id) {
		return this.frameTplRepository.findOne(id);
	}
	@Override
	public String read(BigInteger id) {
		FrameTpl frameTpl = this.findOne(id);
		return read(frameTpl);
	}
	@Override
	public String read(FrameTpl template) {
		String templatePath =getRealPath(template.getTplPath());
		File templateFile = new File(templatePath);
		String templateContent = null;
		try {
			templateContent = FileUtils.readFileToString(templateFile, "UTF-8");
		} catch (IOException e) {
			logger.warn("[TemplateServiceImpl]-" + e.getMessage());
		}
		return templateContent;
	}
	@Override
	public void write(BigInteger id, String content) {
		FrameTpl template = this.findOne(id);
		write(template, content);
	}
	@Override
	public void write(FrameTpl template, String content) {
		String templatePath =getRealPath(template.getTplPath());
		File templateFile = new File(templatePath);
		try {
			FileUtils.writeStringToFile(templateFile, content, "UTF-8");
		} catch (IOException e) {
			logger.warn("[TemplateServiceImpl]-" + e.getMessage());
		}
	}
	protected String getRealPath(String tplPath){
		return this.servletContext.getRealPath(properties.getTemplateLoaderPath()[0]+ tplPath);
	}  
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
