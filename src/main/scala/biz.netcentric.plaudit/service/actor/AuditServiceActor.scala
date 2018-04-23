package biz.netcentric.plaudit.service.actor

import akka.actor.Props
import biz.netcentric.plaudit.service.actor.browser.{ BrowseUrl, BrowseUrlDone, BrowserMasterActor }

case class AuditUrl(url: String)
case class AuditUrlDone(result: Boolean, url: String)

class AuditServiceActor extends ServiceActor {

  def receive = {
    case AuditUrl(url) =>
      val requester = sender()
      localActor(BrowserMasterActor.name) ! BrowseUrl(url, requester)

    case BrowseUrlDone(url, requester) =>
      requester ! AuditUrlDone(true, url)
  }
}

object AuditServiceActor {
  def props() = Props[AuditServiceActor]
  val name = "audit-service"
}
