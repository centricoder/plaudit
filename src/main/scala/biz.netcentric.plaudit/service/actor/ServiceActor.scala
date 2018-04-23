package biz.netcentric.plaudit.service.actor

import akka.actor.{ Actor, ActorLogging }
import biz.netcentric.plaudit.service.system.local.LocalServices

trait ServiceActor extends Actor with ActorLogging with LocalServices