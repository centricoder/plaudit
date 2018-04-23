package biz.netcentric.plaudit.http.route

import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import biz.netcentric.plaudit.http.route.base.RouteBase
import biz.netcentric.plaudit.service.actor.{ AuditServiceActor, AuditUrl, AuditUrlDone }
import biz.netcentric.plaudit.service.system.extender.ActorSystemExtender._

import scala.concurrent.Future

trait AuditRoute extends RouteBase {

  var auditServiceCall: (Any) => Future[Any] = (actorMessage: Any) => {
    system.lookup_existing_actor(AuditServiceActor.name) ? actorMessage
  }

  val auditRoutes = {
    path("audit") {
      get {
        parameters('url) { url =>
          onSuccess(auditServiceCall(AuditUrl(url)).mapTo[AuditUrlDone]) { results =>
            complete(results.result)
          }
        }
      }
    }
  }
}