package biz.netcentric.plaudit.service.boot

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import biz.netcentric.plaudit.service.actor.base.ServiceEchoActor
import biz.netcentric.plaudit.service.init.DependencyLoader
import biz.netcentric.plaudit.service.system.ServiceName
import com.typesafe.config.{ Config, ConfigFactory }

import scala.concurrent.ExecutionContextExecutor

trait ServiceBoot extends App with ServiceName with DependencyLoader {
  var config: Config = _
  var system: ActorSystem = _
  var executor: ExecutionContextExecutor = _
  var materializer: ActorMaterializer = _

  def init(): Unit = {
    config = ConfigFactory.load()

    val serviceName = config.getString("service.name")
    println(s"INITIALIZING service name:'$serviceName'")

    setServiceName(serviceName)
  }

  def loadActorSystem(config: Config): Unit = {
    val actorSystemLoader = new ActorSystemLoader(config)

    println(s"LOADED actor system> ${actorSystemLoader.actorSystemInterface}:${actorSystemLoader.actorSystemPort}")

    system = actorSystemLoader.system
    executor = actorSystemLoader.executor
    materializer = actorSystemLoader.materializer
  }

  def loadCoreServiceActors(system: ActorSystem): Unit = {
    println("INITIALIZING core service actors...")
    system.actorOf(ServiceEchoActor.props(), ServiceEchoActor.name)
  }

  def loadHttpServer(configuration: Config, actorSystem: ActorSystem, systemExecutor: ExecutionContextExecutor, systemMaterializer: ActorMaterializer): Unit

  //boot lifecycle
  init()

  loadActorSystem(config)

  loadRepositories(config)

  loadCoreServiceActors(system)
  loadServiceActors(system, config)

  loadHttpServer(config, system, executor, materializer)
}