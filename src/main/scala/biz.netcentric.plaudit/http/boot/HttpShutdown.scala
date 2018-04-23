package biz.netcentric.plaudit.http.boot

import akka.http.scaladsl.Http.ServerBinding
import biz.netcentric.plaudit.service.system.ActorSystemAccessor

import scala.concurrent.{ Await, ExecutionContextExecutor, Future }
import scala.concurrent.duration.Duration

trait HttpShutdown extends ActorSystemAccessor {
  implicit val executor: ExecutionContextExecutor

  def shutdownHttpServer(httpBinder: Future[ServerBinding]) {
    println("Shutting down http server...")

    httpBinder
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate())

    Await.result(system.whenTerminated, Duration.Inf)

    println("Http server shutdown")
  }
}
