package edu.knoldus

import akka.actor.{Actor, Props}
import edu.knoldus.DataLogs.LogAll
import play.api.Logger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by Neelaksh on 27/8/17.
  */
class DataLogs(externalService: ExternalService) extends Actor{



  override def receive: Receive = {
    case LogAll =>
      externalService.getToDoList().invoke().map{
        data => Logger.info(data.toString())
      }
  }
}

object DataLogs{
  case object LogAll
  def props(externalService: ExternalService) = Props(classOf[DataLogs], externalService)
}
