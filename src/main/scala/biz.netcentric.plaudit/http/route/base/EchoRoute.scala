package biz.netcentric.plaudit.http.route.base

import akka.http.scaladsl.model.{ ContentTypes, HttpEntity }
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import biz.netcentric.plaudit.service.actor.base.{ Echo, EchoDone, ServiceEchoActor }
import biz.netcentric.plaudit.service.system.ServiceName
import org.joda.time.DateTime
import biz.netcentric.plaudit.service.system.extender.ActorSystemExtender._
import biz.netcentric.plaudit.utility.DateTimeUtility._

import scala.concurrent.Future

trait EchoRoute extends RouteBase with ServiceName {

  var echoServiceCall: (Any) => Future[Any] = (actorMessage: Any) => {
    system.lookup_existing_actor(ServiceEchoActor.name) ? actorMessage
  }

  val echoRoutes = {
    path("echo") {
      val dateTimeNow = DateTime.now
      val echoResponse = s"""{"serviceName":"$serviceName", "echo":"${dateTimeNow.to_value()}"}"""
      complete(HttpEntity(ContentTypes.`application/json`, echoResponse))
    } ~
      path("echo" / "system") {
        onSuccess(echoServiceCall(Echo()).mapTo[EchoDone]) { results =>
          val dateTimeNow = DateTime.now
          val echoResponse = s"""{"serviceName":"$serviceName", "echo":"${dateTimeNow.to_value()}", "system":"${results.serviceName}"}"""
          complete(HttpEntity(ContentTypes.`application/json`, echoResponse))
        }
      }
  }
}
