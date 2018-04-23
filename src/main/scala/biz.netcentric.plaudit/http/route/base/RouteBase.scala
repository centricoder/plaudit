package biz.netcentric.plaudit.http.route.base

import biz.netcentric.plaudit.http.serialization.JsonCirceCompleteSerialization
import biz.netcentric.plaudit.service.system.ActorSystemAccessor

trait RouteBase extends ActorSystemAccessor with JsonCirceCompleteSerialization
