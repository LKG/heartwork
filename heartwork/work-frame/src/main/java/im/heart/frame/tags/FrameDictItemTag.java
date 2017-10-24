
package im.heart.frame.tags;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.google.common.collect.Lists;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import im.heart.common.context.ContextManager;
import im.heart.core.template.BaseDirective;
import im.heart.core.utils.StringUtilsEx;
import im.heart.frame.entity.FrameDictItem;
import im.heart.frame.service.FrameDictItemService;
import im.heart.frame.vo.FrameDictItemVO;
@Component
public class FrameDictItemTag extends BaseDirective {
	protected static final Logger logger = LoggerFactory.getLogger(FrameDictItemTag.class);

	@Override
	public void render(Environment env, Map<?, ?> params, TemplateDirectiveBody body)
			throws IOException, TemplateException {
		String dictCode=super.getParam(params, "dictCode");
		List<FrameDictItem> items=Lists.newArrayList();
		List<FrameDictItemVO> itemVOs=Lists.newArrayList();
		if(StringUtilsEx.isNotBlank(dictCode)){
			FrameDictItemService dictItemService = (FrameDictItemService) ContextManager.getBean(FrameDictItemService.BEAN_NAME);
			items= dictItemService.findByDictCode(dictCode);
			for(FrameDictItem po :items){
				itemVOs.add(new FrameDictItemVO(po));
			}
		}
		super.setVariable("items",itemVOs,env);	
		super.renderBody(env,body);
	}
	@Override
	protected void verifyParameters(Map<?, ?> params) throws TemplateModelException {
		 
       
	}

}
