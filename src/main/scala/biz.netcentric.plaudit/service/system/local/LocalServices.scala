package biz.netcentric.plaudit.service.system.local

import akka.actor.{ ActorContext, ActorSelection }
import biz.netcentric.plaudit.service.system.extender.ActorSystemExtender._

trait LocalServices {
  implicit val context: ActorContext

  def localActor(actorName: String): ActorSelection = {
    context.system.lookup_existing_actor(actorName)
  }
}
