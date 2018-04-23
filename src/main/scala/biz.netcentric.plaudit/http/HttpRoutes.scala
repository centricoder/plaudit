package biz.netcentric.plaudit.http

import biz.netcentric.plaudit.http.route.{ AuditRoute, IndexRoute }
import biz.netcentric.plaudit.http.route.base.HttpRoutesBase
import akka.http.scaladsl.server.Directives._

trait HttpRoutes extends HttpRoutesBase with IndexRoute with AuditRoute {

  allHttpRoutes = allHttpRoutes ~ indexRoutes ~ auditRoutes

}
