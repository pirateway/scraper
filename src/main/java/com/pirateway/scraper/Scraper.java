package com.pirateway.scraper;


import com.ocpsoft.pretty.PrettyFilter;
import com.pirateway.scraper.api.service.IBetScraperService;
import com.pirateway.scraper.api.service.IForkService;
import com.pirateway.scraper.exception.DataValidateException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import java.io.IOException;
import java.util.EnumSet;

@SpringBootApplication()
public class Scraper extends SpringBootServletInitializer {

    public static void main(String[] args) throws DataValidateException, IOException {
        ConfigurableApplicationContext context = SpringApplication.run(Scraper.class, args);
       /* IBetScraperService scraperService = context.getBean(IBetScraperService.class);

        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        scraperService.clear();
                        scraperService.refresh();
                        Thread.sleep(5000);
                    } catch (DataValidateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        myThread.start();*/
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        FacesServlet servlet = new FacesServlet();
        return new ServletRegistrationBean(servlet, "*.xhtml");
    }

    @Bean
    public FilterRegistrationBean prettyFilter() {
        FilterRegistrationBean rwFilter = new FilterRegistrationBean(new PrettyFilter());
        rwFilter.setDispatcherTypes(EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST,
                DispatcherType.ASYNC, DispatcherType.ERROR));
        rwFilter.addUrlPatterns("/*");
        return rwFilter;
    }

}
