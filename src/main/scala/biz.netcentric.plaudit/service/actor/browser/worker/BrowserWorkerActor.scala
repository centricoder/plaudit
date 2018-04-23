package biz.netcentric.plaudit.service.actor.browser.worker

import akka.actor.Props
import biz.netcentric.plaudit.service.actor.ServiceActor

case class PerformBrowserWork(url: String)

class BrowserWorkerActor extends ServiceActor {

  def receive = {
    case PerformBrowserWork(url) =>
      throw new RuntimeException("NI")
  }
}

object BrowserWorkerActor {
  def props() = Props[BrowserWorkerActor]
  val name = "browser-worker"
}