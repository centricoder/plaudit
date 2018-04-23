package biz.netcentric.plaudit.service.init

import com.typesafe.config.Config

trait ConfigAccessor {
  implicit val config: Config
}
