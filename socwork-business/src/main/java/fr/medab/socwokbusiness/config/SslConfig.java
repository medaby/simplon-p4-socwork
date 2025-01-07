package fr.medab.socwokbusiness.config;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // SSL onfiguration
public class SslConfig {

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        tomcat.addContextCustomizers(securityCustomizer());
        return tomcat;
    }

    private Connector httpConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }

    private TomcatContextCustomizer securityCustomizer() {
        return context -> {
            SecurityCollection collection = new SecurityCollection();
            collection.addPattern("/*");
            SecurityConstraint constraint = new SecurityConstraint();
            constraint.addCollection(collection);
            constraint.setUserConstraint("CONFIDENTIAL");
            context.addConstraint(constraint);
        };
    }


//    @Bean
//    public BeanPostProcessor addHttpConnectorProcessor() {
//        return new BeanPostProcessor() {
//            @Override
//            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//                if (bean instanceof TomcatServletWebServerFactory factory) {
//                    factory.addAdditionalTomcatConnectors(httpConnector());
//                }
//                return bean;
//            }
//        };
//    }


}
