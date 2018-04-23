package biz.netcentric.plaudit.service.init

import akka.actor.ActorSystem
import com.typesafe.config.Config

trait DependencyLoader {

  def loadRepositories(config: Config): Unit

  def loadServiceActors(system: ActorSystem, config: Config): Unit
}
