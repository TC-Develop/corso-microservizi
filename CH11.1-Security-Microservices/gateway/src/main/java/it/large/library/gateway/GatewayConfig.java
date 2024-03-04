package it.large.library.gateway;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> catalogueGet(){
        return GatewayRouterFunctions.route("sales").GET("/catalogue/**",
                HandlerFunctions.http("http://localhost:8081")).build();
    }

    @Bean
    public RouterFunction<ServerResponse> cataloguePost(){
        return GatewayRouterFunctions.route("sales").POST("/catalogue/**",
                HandlerFunctions.http("http://localhost:8081")).build();
    }

    @Bean
    public RouterFunction<ServerResponse> cataloguePut(){
        return GatewayRouterFunctions.route("sales").PUT("/catalogue/**",
                HandlerFunctions.http("http://localhost:8081")).build();
    }

    @Bean
    public RouterFunction<ServerResponse> catalogueDelete(){
        return GatewayRouterFunctions.route("sales").DELETE("/catalogue/**",
                HandlerFunctions.http("http://localhost:8081")).build();
    }

}
