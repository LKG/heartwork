package im.heart.core.support.view;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import im.heart.core.CommonConst.RequestResult;

/**
 * 
 * @author gg
 * @desc 扩展FASTJSONView 支持JSONP 格式 hibernate 延时代理不进行序列化
 */
public class JsonpView extends FastJsonJsonView {

	public boolean isUpdateContentLength() {
		return updateContentLength;
	}
	@Override
	public void setUpdateContentLength(boolean updateContentLength) {
		this.updateContentLength = updateContentLength;
	}

	private boolean updateContentLength = false;
	

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String jsoncallback = request.getParameter(RequestResult.JSONCALLBACK);
		SerializeFilter  filter =null;
	/*	if(lazy){
			filter = new FastJosnPropertyFilter();
		}*/
		Object value = super.filterModel(model);
		String text = JSON.toJSONString(value,filter, super.getFastJsonConfig().getSerializerFeatures());	
		if (jsoncallback != null && !StringUtils.isBlank(jsoncallback)) {
			text = jsoncallback + "(" + text + ")";
		}
		byte[] bytes = text.getBytes(super.getFastJsonConfig().getCharset());
		OutputStream stream = this.updateContentLength ? createTemporaryOutputStream()
				: response.getOutputStream();
		stream.write(bytes);

		if (this.updateContentLength) {
			writeToResponse(response, (ByteArrayOutputStream) stream);
		}
	}

}