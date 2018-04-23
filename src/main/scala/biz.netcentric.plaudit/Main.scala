package biz.netcentric.plaudit

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import biz.netcentric.plaudit.http.HttpRoutes
import biz.netcentric.plaudit.http.boot.HttpStartup
import biz.netcentric.plaudit.service.boot.ServiceBoot
import biz.netcentric.plaudit.service.init.ServiceActors
import com.typesafe.config.Config

import scala.concurrent.ExecutionContextExecutor

object Main extends ServiceBoot {

  override def loadRepositories(config: Config): Unit = {
    //todo: wire-in repositories
  }

  override def loadServiceActors(system: ActorSystem, config: Config): Unit = {
    ServiceActors.initialize(system, config)
  }

  override def loadHttpServer(configuration: Config, actorSystem: ActorSystem, systemExecutor: ExecutionContextExecutor, systemMaterializer: ActorMaterializer): Unit = {
    class HttpStartupLoader extends HttpStartup with HttpRoutes {
      override implicit val config: Config = configuration
      override implicit val system: ActorSystem = actorSystem
      override implicit val executor: ExecutionContextExecutor = systemExecutor
      override implicit val materializer: ActorMaterializer = systemMaterializer

      override def httpRoutes: Route = allHttpRoutes
    }
    new HttpStartupLoader().startHttpServer()
  }

}

