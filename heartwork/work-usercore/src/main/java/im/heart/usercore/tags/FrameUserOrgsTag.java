package im.heart.usercore.tags;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import im.heart.common.context.ContextManager;
import im.heart.core.template.BaseDirective;
import im.heart.security.utils.SecurityUtilsHelper;
import im.heart.usercore.entity.FrameUserOrg;
import im.heart.usercore.service.FrameUserOrgService;
import im.heart.usercore.vo.FrameUserVO;
@Component
public class FrameUserOrgsTag extends BaseDirective {
	protected static final Logger logger = LoggerFactory.getLogger(FrameUserOrgsTag.class);


	@Override
	public void render(Environment env, Map<?, ?> params, TemplateDirectiveBody body)
			throws IOException, TemplateException {
		FrameUserVO userVO = SecurityUtilsHelper.getCurrentUser();
		if(userVO!=null){
			FrameUserOrgService userOrgService=	ContextManager.getBean(FrameUserOrgService.class);
			List<FrameUserOrg> userOrgs = userOrgService.findByUserId(userVO.getUserId());
			super.setVariable("userOrgs",userOrgs,env);
			super.renderBody(env,body);
		}
	}
	@Override
	protected void verifyParameters(Map<?, ?> params) throws TemplateModelException {
		  // 检查是否传递参数，此指令禁止传参！  
        if (!params.isEmpty()) {  
            throw new TemplateModelException(  
                    "This directive doesn't allow parameters.");  
        }  
	}

}
