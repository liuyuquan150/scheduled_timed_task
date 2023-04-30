package indi.ly.crush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <h2>主启动类</h2>
 *
 * @author 云上的云
 * @see <a href="http://www.360doc.com/content/22/0925/17/74661_1049314207.shtml">JPA踩坑系列之save方法</a>
 * @see <a href="https://blog.csdn.net/csdnwenjun/article/details/124349019">Spring-Data-Jpa 保存时save和saveAndFlush的区别</a>
 * @since 1.0
 */
@EnableJpaAuditing

@EnableScheduling
@SpringBootApplication(scanBasePackages = "indi.ly.crush")
public class ScheduledTimedTaskApplication {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ScheduledTimedTaskApplication.class);
		application.run(args);
	}
}
