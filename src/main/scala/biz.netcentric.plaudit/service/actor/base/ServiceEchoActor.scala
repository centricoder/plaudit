package biz.netcentric.plaudit.service.actor.base

import akka.actor.Props
import biz.netcentric.plaudit.service.actor.ServiceActor
import biz.netcentric.plaudit.service.system.ServiceName

final case class Echo()
final case class EchoDone(serviceName: String)

class ServiceEchoActor extends ServiceActor with ServiceName {

  override def preStart() {
    log.info(s"Starting echo actor for service:'$serviceName'")
  }

  def receive = {
    case Echo() =>
      log.info("Received echo request")
      sender() ! EchoDone(serviceName)
  }
}

object ServiceEchoActor {
  def props() = Props[ServiceEchoActor]
  val name = "echo"
}
