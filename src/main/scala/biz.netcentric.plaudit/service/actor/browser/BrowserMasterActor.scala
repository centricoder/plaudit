package biz.netcentric.plaudit.service.actor.browser

import akka.actor.{ ActorRef, Props }
import biz.netcentric.plaudit.service.actor.ServiceActor

case class BrowseUrl(url: String, origin: ActorRef)
case class BrowseUrlDone(url: String, origin: ActorRef)

class BrowserMasterActor extends ServiceActor {

  def receive = {
    case BrowseUrl(url, origin) =>
      println("browsing url:" + url)
      sender ! BrowseUrlDone(url, origin)
  }
}

object BrowserMasterActor {
  def props() = Props[BrowserMasterActor]

  val name = "browser-master"
}