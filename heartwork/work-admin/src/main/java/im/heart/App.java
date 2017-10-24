package im.heart;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAutoConfiguration
@ComponentScan()
@EnableAsync
@EnableCaching
//@PropertySource("file:/mnt/conf/itaobao/application-prod.properties") //
public class App extends SpringBootServletInitializer implements CommandLineRunner{
	
	public static ApplicationContext context;
	@Override
	public void run(String... args) throws Exception {
	}
	public static void main(String[] args) {
		if(args.length==0){
		//	args=new String[] {"--spring.config.location=file:/mnt/conf/itaobao/application-prod.properties"};
		}
		SpringApplication application=new SpringApplication(App.class);
		application.setBannerMode(Banner.Mode.OFF); 
		context = application.run(args); 
	}
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(App.class);
    }
}
