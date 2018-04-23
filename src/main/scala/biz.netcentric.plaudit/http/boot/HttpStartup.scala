package biz.netcentric.plaudit.http.boot

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.{ BadRequest, Forbidden, MethodNotAllowed, NotFound, _ }
import akka.http.scaladsl.server.Directives.{ complete, _ }
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import biz.netcentric.plaudit.service.init.ConfigAccessor
import biz.netcentric.plaudit.service.system.ActorSystemAccessor
import org.jboss.netty.handler.codec.http.HttpHeaders

trait HttpStartup extends ActorSystemAccessor with ConfigAccessor with HttpShutdown {
  implicit val materializer: ActorMaterializer

  implicit def customRejectionHandler =
    RejectionHandler.newBuilder()
      .handle {
        case AuthorizationFailedRejection =>
          complete((Forbidden, "Access Denied, computer says no"))
      }
      .handleAll[MethodRejection] { methodRejections =>
        val names = methodRejections.map(_.supported.name)
        complete((MethodNotAllowed, s"Method rejected, supported: ${names mkString " or "}!"))
      }
      .handle {
        case MissingQueryParamRejection(param) =>
          complete(HttpResponse(BadRequest, entity = s"Missing Parameter required $param was not found."))
      }
      .handle {
        case MissingFormFieldRejection(param) =>
          complete(HttpResponse(BadRequest, entity = s"Missing field required $param was not supplied."))
      }
      .handleNotFound {
        complete((NotFound, "Not Found"))
      }
      .result()

  def startHttpServer() {
    val httpServerIp = config.getString("service.http.interface")
    val httpServerPort = config.getInt("service.http.port")

    val httpBinder = Http().bindAndHandle(httpRoutes, httpServerIp, httpServerPort)

    println(s"LOADED web server> $httpServerIp:$httpServerPort")
    sys.addShutdownHook(shutdownHttpServer(httpBinder))
  }

  def httpRoutes: Route
}
