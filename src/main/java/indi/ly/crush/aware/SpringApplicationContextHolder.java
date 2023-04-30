package indi.ly.crush.aware;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * <h2>Spring 应用程序上下文持有者</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
@Component(value = "SpringApplicationContextAware")
public class SpringApplicationContextHolder
		implements ApplicationContextAware {
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(@NonNull ApplicationContext applicationContext)
			throws BeansException {
		SpringApplicationContextHolder.applicationContext = applicationContext;
	}
	
	public static Object getBean(String name) {
		return SpringApplicationContextHolder.applicationContext.getBean(name);
	}
	
	public static Boolean containsBean(String name) {
		return SpringApplicationContextHolder.applicationContext.containsBean(name);
	}
}
