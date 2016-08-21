package pasman.filters;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by Max on 19.08.2016.
 */
@Provider
public class LoginForm implements ContainerResponseFilter, ContainerRequestFilter {
    public static String USER_NAME = "";

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        System.out.println("Request filter");
        System.out.println("Headers: " + containerRequestContext.getHeaders());

    }

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        System.out.println("Response filter");
        System.out.println("Headers: " + responseContext.getHeaders());
    }
}