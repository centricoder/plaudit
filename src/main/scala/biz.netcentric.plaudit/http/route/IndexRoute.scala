package biz.netcentric.plaudit.http.route

import akka.http.scaladsl.server.Directives._
import biz.netcentric.plaudit.http.route.base.RouteBase

trait IndexRoute extends RouteBase {

  val indexRoutes = {
    pathEndOrSingleSlash {
      get {
        getFromResource("html/index.html")
      }
    }
  }

}
