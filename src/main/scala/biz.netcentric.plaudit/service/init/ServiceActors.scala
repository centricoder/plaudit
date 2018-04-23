package biz.netcentric.plaudit.service.init

import akka.actor.ActorSystem
import biz.netcentric.plaudit.service.actor.AuditServiceActor
import biz.netcentric.plaudit.service.actor.browser.BrowserMasterActor
import com.typesafe.config.Config

object ServiceActors {

  def initialize(system: ActorSystem, config: Config): Unit = {
    system.actorOf(BrowserMasterActor.props(), BrowserMasterActor.name)
    system.actorOf(AuditServiceActor.props(), AuditServiceActor.name)
  }

}
