package app1

import javax.inject.Singleton

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import io.dropwizard.setup.{Bootstrap, Environment}
import io.dropwizard.{Configuration, Application => DropwizardApplication}
import org.glassfish.hk2.utilities.binding.AbstractBinder
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider

class DropwizardConfiguration extends Configuration {
}

class Application extends DropwizardApplication[DropwizardConfiguration] {

  override def run(t: DropwizardConfiguration, environment: Environment): Unit =  {
    environment.getObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    environment.getObjectMapper.registerModule(DefaultScalaModule)

     environment.jersey().register(new AbstractBinder() {
      override def configure()  {
        bind(classOf[BiSessionValueFactoryProvider]).to(classOf[ValueFactoryProvider]).in(classOf[Singleton])
      }
    })
    environment.jersey().register(new InjectionResource())

  }

  override def getName: String = "dropwizrd-app-1"

  override def initialize(bootstrap: Bootstrap[DropwizardConfiguration]): Unit =  {
    super.initialize(bootstrap)
  }
}

object Application extends App {
  val app = new Application
  app.run("server", "src/main/resources/config/application/dropwizard-app-1/dropwizard-app-1.yaml")
}
