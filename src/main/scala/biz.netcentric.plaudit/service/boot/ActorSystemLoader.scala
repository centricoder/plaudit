package biz.netcentric.plaudit.service.boot

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import biz.netcentric.plaudit.service.system.ServiceName
import com.typesafe.config.Config

class ActorSystemLoader(config: Config) extends ServiceName {
  implicit val system: ActorSystem = ActorSystem(serviceName, config)
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  def actorSystemInterface: String = {
    config.getString("akka.remote.netty.tcp.hostname")
  }

  def actorSystemPort: Int = {
    config.getInt("akka.remote.netty.tcp.port")
  }
}
