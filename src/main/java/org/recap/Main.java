package org.recap;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.recap.filter.XSSFilter;
import org.recap.security.SessionFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Main.
 */
@SpringBootApplication
public class Main {

	/**
	 * The Tomcat max parameter count.
	 */
	@Value("${tomcat.maxParameterCount}")
	Integer tomcatMaxParameterCount;

	/**
	 * The Tomcat secure.
	 */
	@Value("${server.secure}")
	boolean tomcatSecure;

	/**
	 * Servlet container factory embedded servlet container factory.
	 *
	 * @return the embedded servlet container factory
	 */
	@Bean
	public EmbeddedServletContainerFactory servletContainerFactory() {
		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
		factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
			@Override
			public void customize(Connector connector) {
				connector.setMaxParameterCount(tomcatMaxParameterCount);
				connector.setSecure(tomcatSecure);
			}
		});
		return factory;
	}

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}


	/**
	 * Get filter registered bean filter registration bean.
	 *
	 * @return the filter registration bean
	 */
	@Bean
	public FilterRegistrationBean getFilterRegisteredBean(){
		FilterRegistrationBean filterRegistrationBean= new FilterRegistrationBean();
		Set<String> urlPatterns=new HashSet<>();
		urlPatterns.add("/*");
		filterRegistrationBean.setUrlPatterns(urlPatterns);
		filterRegistrationBean.setFilter(new SessionFilter());
		return filterRegistrationBean;
	}

	/**
	 * Gets xss filter registered bean.
	 *
	 * @return the xss filter registered bean
	 */
	@Bean
	public FilterRegistrationBean getXSSFilterRegisteredBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		Set<String> urlPatterns = new HashSet<>();
		urlPatterns.add("/request");
		urlPatterns.add("/roles#");
		urlPatterns.add("/userRoles#");
		filterRegistrationBean.setUrlPatterns(urlPatterns);
		filterRegistrationBean.setFilter(new XSSFilter());
		return filterRegistrationBean;
	}

	/**
	 * Container customizer embedded servlet container customizer.
	 *
	 * @return the embedded servlet container customizer
	 */
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				if (container.getClass().isAssignableFrom(TomcatEmbeddedServletContainerFactory.class)) {
					TomcatEmbeddedServletContainerFactory tomcatContainer = (TomcatEmbeddedServletContainerFactory) container;
					tomcatContainer.addContextCustomizers(new ContextSecurityCustomizer());
				}
			}
		};
	}

	private static class ContextSecurityCustomizer implements TomcatContextCustomizer {
		@Override
		public void customize(Context context) {
			SecurityConstraint constraint = new SecurityConstraint();
			SecurityCollection securityCollection = new SecurityCollection();
			securityCollection.setName("restricted_methods");
			securityCollection.addPattern("/*");
			securityCollection.addMethod(HttpMethod.OPTIONS.toString());
			securityCollection.addMethod(HttpMethod.HEAD.toString());
			securityCollection.addMethod(HttpMethod.PUT.toString());
			securityCollection.addMethod(HttpMethod.PATCH.toString());
			securityCollection.addMethod(HttpMethod.DELETE.toString());
			securityCollection.addMethod(HttpMethod.TRACE.toString());
			constraint.addCollection(securityCollection);
			constraint.setAuthConstraint(true);
			context.addConstraint(constraint);
		}
	}

}
