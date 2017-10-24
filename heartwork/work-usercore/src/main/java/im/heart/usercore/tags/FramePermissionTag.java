package im.heart.usercore.tags;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import im.heart.common.context.ContextManager;
import im.heart.core.enums.Status;
import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.core.plugins.persistence.SearchFilter;
import im.heart.core.plugins.persistence.SearchFilter.Operator;
import im.heart.core.template.BaseDirective;
import im.heart.core.utils.StringUtilsEx;
import im.heart.security.utils.SecurityUtilsHelper;
import im.heart.usercore.entity.FramePermission;
import im.heart.usercore.service.FramePermissionService;
import im.heart.usercore.vo.FramePermissionVO;
import im.heart.usercore.vo.FrameUserVO;
@Component
public class FramePermissionTag extends BaseDirective {
	protected static final Logger logger = LoggerFactory.getLogger(FramePermissionTag.class);


	@Override
	public void render(Environment env, Map<?, ?> params, TemplateDirectiveBody body)
			throws IOException, TemplateException {
		FrameUserVO userVO = SecurityUtilsHelper.getCurrentUser();
		if(userVO!=null){
			FramePermissionService framePermissionService=	ContextManager.getBean(FramePermissionService.class);
			final Collection<SearchFilter> filters= new HashSet<SearchFilter>();
			filters.add(new SearchFilter("status",Operator.EQ,Status.enabled));//
			Specification<FramePermission> spec=DynamicSpecifications.bySearchFilter(filters, FramePermission.class);
			List<FramePermission> permissions =framePermissionService.findAll(spec);
			List<FramePermissionVO> vos=new ArrayList<FramePermissionVO>(); 
			String permissionIds = getParam(params, "permissionIds");
			Map<BigInteger, String> permissionMap=Maps.newHashMap();
			if(StringUtilsEx.isNotBlank(permissionIds)){
				String[] ids = StringUtilsEx.split(permissionIds,",");
				for(String permissionId:ids){	
					permissionMap.put(new BigInteger(permissionId), permissionId);
				}
			}
			for(FramePermission permission :permissions){
				FramePermissionVO vo=new FramePermissionVO(permission);
				if(permissionMap.containsKey(permission.getPermissionId())){
					vo.setIsChecked(Boolean.TRUE);
				}
				vos.add(vo);
			}
			super.setVariable("permissions",vos,env);
			super.renderBody(env,body);
		}
	}
	@Override
	protected void verifyParameters(Map<?, ?> params) throws TemplateModelException {
	}

}
