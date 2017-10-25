package im.heart.admin.monitor.web;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import im.heart.core.web.AbstractController;

/**
 * 
 * @author gg
 * @desc 系统jvm信息 监控
 */
@Controller
@RequestMapping("/admin/monitor")
@RequiresPermissions("admin:monitor")
public class JvmMonitorController extends AbstractController {
	protected static final String JVM_HOME = "/admin/monitor/jvm/";
	
	@RequestMapping("/jvm")
	public ModelAndView getJVMIndex(HttpServletRequest request, ModelMap model) {
		MemoryUsage memory = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
		this.success(model);
		model.put("memory", memory);
		OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
		model.put("os", os);
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		model.put("runtime", runtimeMXBean);
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		threadMXBean.setThreadContentionMonitoringEnabled(true);
		model.put("thread", threadMXBean);
		return new ModelAndView(JVM_HOME+"index");
	}

	@RequestMapping("/memory")
	public ModelAndView getJVM(HttpServletRequest request, ModelMap model) {
		MemoryUsage memory = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
		this.success(model,memory);
		return new ModelAndView(JVM_HOME+"memory");
	}

	@RequestMapping("/os")
	public ModelAndView getSysOs(HttpServletRequest request, ModelMap model) {
		OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
		this.success(model,"os",os);
		return new ModelAndView(JVM_HOME+"os");
	}

	@RequestMapping("/runtime")
	public ModelAndView getRuntime(HttpServletRequest request, ModelMap model) {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		this.success(model,runtimeMXBean);
		return new ModelAndView(JVM_HOME+"runtime");
	}

	@RequestMapping("/thread")
	public ModelAndView getThreadMXBean(HttpServletRequest request,
			ModelMap model) {
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		threadMXBean.setThreadContentionMonitoringEnabled(true);
		this.success(model,threadMXBean);
		return new ModelAndView(JVM_HOME+"thread");
	}
}