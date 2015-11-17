package com.github.photobug;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;

import com.github.photobug.bean.User;


/**
 * 日志记录拦截器 记录用户、用户ip、访问路径及请求参数
 * TODO:规范项目的request，涉及修改的应全部使用post，这样，此处只记录对数据的产生修改的操作
 * 
 * @author sheva.wen
 * 
 */
public class LogFilter implements ActionFilter {
	private static final Log log = Logs.getLog(LogFilter.class);

	public View match(ActionContext actionContext) {
		if (log.isDebugEnabled()) {
			//没用shiro,暂时用session来判断记录日志
			HttpSession session = Mvcs.getHttpSession(false);
	    	if (session != null && null != session.getAttribute("me")){
	    		User user = (User) session.getAttribute("me");
	    		String ip = getRealIp(actionContext.getRequest());
	    		Map<String,String[]> properties = actionContext.getRequest().getParameterMap();
	    		String args = Json.toJson(properties, JsonFormat.forLook());
	    		log.debugf("\nuser:%s[%s]\nip:%s\npath:%s\nargs:%s\n",
						user.getName(), user.getRealName(), ip,
						actionContext.getPath(), args);
	    	}
			
//			String args = "";
//			Map<String,String[]> properties = actionContext.getRequest().getParameterMap();
//			args = Json.toJson(properties, JsonFormat.forLook());
//			String ip = getRealIp(actionContext.getRequest());
//			Subject currentUser = SecurityUtils.getSubject();
//			if (currentUser.isAuthenticated()) {
//				User user = (User) SecurityUtils.getSubject().getPrincipal();
//				log.debugf("\nuser:%s[%s]\nip:%s\npath:%s\nargs:%s\n",
//						user.getName(), user.getRealName(), ip,
//						actionContext.getPath(), args);
//			} else {
//				log.debugf("\nip:%s\npath:%s\nargs:%s\n", ip,
//						actionContext.getPath(), args);
//			}

		}
		return null;
	}
	/**
	 * 获取request真是的ip,
	 * 因为在nginx做代理的情况下，request.getRemoteAddr()只能获得nginx的地址,
	 * 需要再nginx中，为header添加X-Forwarded-For来将真实ip转过来,
	 * nginx需添加的配置包括
	 * <pre>
	 *  proxy_set_header   Host $host;
     *  proxy_set_header   X-Real-IP $remote_addr;
     *  proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
	 * </pre>
	 * @param request
	 * @return
	 */
	private String getRealIp(HttpServletRequest request) {
		String address = request.getHeader("X-Forwarded-For");
		if (address != null && address.length() > 0
				&& !"unknown".equalsIgnoreCase(address)) {
			return address;
		}
		address = request.getHeader("Proxy-Client-IP");
		if (address != null && address.length() > 0
				&& !"unknown".equalsIgnoreCase(address)) {
			return address;
		}
		return request.getRemoteAddr();
	}

}
