package com.iot.baobiao.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;

/**
 * Created by ja on 2016/6/22.
 */
public class BaobiaoWebAppInitializer implements WebApplicationInitializer {

//    //指定ContextLoaderListener加载应用上下文时使用的配置类
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class<?>[] { RootConfig.class};
//    }
//
//    //指定DispatcherServlet加载应用上下文时使用的配置类
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class<?>[] { WebConfig.class};
//    }
//
//    //把DispatcherServlet映射到"/"
//    protected String[] getServletMappings() {
//        return new String[] {"/"};
//    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootConfig.class);

        // Manage the lifecycle of the root application context
        servletContext.addListener(new ContextLoaderListener(rootContext));


        // Create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.setServletContext(servletContext);
        dispatcherContext.setParent(rootContext);
        dispatcherContext.register(WebConfig.class);


        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");


        FilterRegistration.Dynamic filter = servletContext.addFilter("shiroFilter",
                new DelegatingFilterProxy("shiroFilter", dispatcherContext));
        filter.addMappingForUrlPatterns(null, false, "/*");
        filter.setInitParameter("targetFilterLifeCycle", "true");
    }
}
