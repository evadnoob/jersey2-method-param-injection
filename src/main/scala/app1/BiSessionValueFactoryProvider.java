package app1;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.internal.inject.AbstractContainerRequestValueFactory;
import org.glassfish.jersey.server.internal.inject.AbstractValueFactoryProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.internal.inject.ParamInjectionResolver;
import org.glassfish.jersey.server.model.Parameter;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

public class BiSessionValueFactoryProvider extends AbstractValueFactoryProvider {

    static class InjectionResolver extends ParamInjectionResolver<BiSessionIdInjectable> {
        public InjectionResolver() {
            super(BiSessionValueFactoryProvider.class);
        }
    }

    private class BiSessionFactory extends AbstractContainerRequestValueFactory<BiSessionId> {

        @Override
        public BiSessionId provide() {
            ContainerRequest request = getContainerRequest();
            if (request == null)  throw new WebApplicationException("unable to get request headers");

            return () -> request.getHeaderString("SessionId");
        }
    }

    @Inject
    public BiSessionValueFactoryProvider(
            final MultivaluedParameterExtractorProvider extractorProvider,
            ServiceLocator locator) {

        super(extractorProvider, locator, Parameter.Source.UNKNOWN);
    }

    @Override
    protected Factory<?> createValueFactory(Parameter parameter) {
         Class<?> paramType = parameter.getRawType();
         BiSessionIdInjectable annotation = parameter.getAnnotation(BiSessionIdInjectable.class);
         if (annotation != null && paramType.isAssignableFrom(BiSessionId.class)) {
             return new BiSessionValueFactoryProvider.BiSessionFactory();
         }
         return null;
    }
}
