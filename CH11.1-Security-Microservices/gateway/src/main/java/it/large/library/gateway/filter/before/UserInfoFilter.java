package it.large.library.gateway.filter.before;

import it.large.library.gateway.security.authentication.JwtAuthentication;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.function.Function;

public class UserInfoFilter implements Function<ServerRequest, ServerRequest> {

    @Override
    public ServerRequest apply(ServerRequest serverRequest) {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        String roles =
                StringUtils.join(authentication.getAuthorities().stream().map(String::valueOf).toList(),",");

        return ServerRequest.from(serverRequest)
                .header("username", username)
                .header("roles", roles).build();
    }

    @Override
    public <V> Function<V, ServerRequest> compose(Function<? super V, ? extends ServerRequest> before) {
        return Function.super.compose(before);
    }

    @Override
    public <V> Function<ServerRequest, V> andThen(Function<? super ServerRequest, ? extends V> after) {
        return Function.super.andThen(after);
    }
}
