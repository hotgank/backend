package org.example.backend.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class TomcatConfig {

  private final Environment env;

  public TomcatConfig(Environment env) {
    this.env = env;
  }

  @Bean
  public ServletWebServerFactory tomcatServletWebServerFactory() {
    TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
    factory.addAdditionalTomcatConnectors(createHttpConnector());
    return factory;
  }

  private Connector createHttpConnector() {
    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setScheme("http");
    int httpPort = env.getProperty("server.http.port", Integer.class, 8080);
    connector.setPort(httpPort);
    connector.setSecure(false);
    return connector;
  }
}
