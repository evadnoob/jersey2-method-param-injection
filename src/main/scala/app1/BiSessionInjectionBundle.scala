package app1

import io.dropwizard.ConfiguredBundle
import io.dropwizard.setup.{Bootstrap, Environment}
import org.glassfish.hk2.api.{InjectionResolver, TypeLiteral}
import org.glassfish.hk2.utilities.binding.AbstractBinder
import javax.inject.Singleton

import org.glassfish.jersey.process.internal.RequestScoped
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider

class BiSessionInjectionBundle[T] extends ConfiguredBundle[T] {
  override def initialize(bootstrap: Bootstrap[_]): Unit = {
  }

  override def run(t: T, environment: Environment): Unit =  {
    environment.jersey().register(new AbstractBinder() {
      override def configure()  {

        //bindFactory(classOf[BiSessionFactory]).proxy(true).proxyForSameScope(false).to(classOf[BiSessionFactory]).in(classOf[RequestScoped])
        bind(classOf[BiSessionValueFactoryProvider]).proxy(true).proxyForSameScope(false).to(classOf[ValueFactoryProvider]).in(classOf[Singleton])
        //bind(classOf[BiSessionIdParamInjectionResolver]).to(new TypeLiteral[InjectionResolver[BiSessionIdInjectable]] {}).in(classOf[Singleton])

      }
    })
  }
}
