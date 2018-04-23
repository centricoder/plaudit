package biz.netcentric.plaudit.model.cookie

import biz.netcentric.plaudit.model.Identifiable
import org.joda.time.DateTime

case class Cookie(
  var id: Option[String],
  title: Option[String],
  description: Option[String],
  created: Option[DateTime]) extends Identifiable
