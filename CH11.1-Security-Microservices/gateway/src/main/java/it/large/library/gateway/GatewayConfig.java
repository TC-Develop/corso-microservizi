package it.large.library.gateway;

import it.large.library.gateway.filter.before.UserInfoFilter;
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
        return GatewayRouterFunctions.route("catalogue").GET("/catalogue/**",
                HandlerFunctions.http("http://localhost:8081"))
                .before(new UserInfoFilter())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> cataloguePost(){
        return GatewayRouterFunctions.route("catalogue").POST("/catalogue/**",
                HandlerFunctions.http("http://localhost:8081"))
                .before(new UserInfoFilter())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> cataloguePut(){
        return GatewayRouterFunctions.route("catalogue").PUT("/catalogue/**",
                HandlerFunctions.http("http://localhost:8081"))
                .before(new UserInfoFilter())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> catalogueDelete(){
        return GatewayRouterFunctions.route("catalogue").DELETE("/catalogue/**",
                HandlerFunctions.http("http://localhost:8081"))
                .before(new UserInfoFilter())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> libraryGet(){
        return GatewayRouterFunctions.route("library").GET("/library/**",
                HandlerFunctions.http("http://localhost:8082"))
                .before(new UserInfoFilter())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> libraryPost(){
        return GatewayRouterFunctions.route("library").POST("/library/**",
                HandlerFunctions.http("http://localhost:8082"))
                .before(new UserInfoFilter())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> libraryPut(){
        return GatewayRouterFunctions.route("library").PUT("/library/**",
                HandlerFunctions.http("http://localhost:8082"))
                .before(new UserInfoFilter())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> libraryDelete(){
        return GatewayRouterFunctions.route("library").DELETE("/library/**",
                HandlerFunctions.http("http://localhost:8082"))
                .before(new UserInfoFilter())
                .build();
    }

}
