package indi.ly.crush.handler;

import indi.ly.crush.util.MapUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * <h2>无语义障碍异常处理程序</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
@RestControllerAdvice(basePackages = "indi.ly.crush")
public class NoSemanticsExceptionHandler
		implements Ordered {
	static final Log LOG = LogFactory.getLog(NoSemanticsExceptionHandler.class);
	
	@ExceptionHandler(value = Exception.class)
	public Map<String, Object> handlerException(Exception exception) {
		String message = exception.getMessage();
		LOG.error(message, exception);
		return MapUtil.error(500,message);
	}
	
	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}
}
