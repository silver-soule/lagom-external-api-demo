package edu.knoldus

import akka.actor.{Actor, Props}
import edu.knoldus.DataLogs.LogAll
import play.api.Logger
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Neelaksh on 27/8/17.
  */
class DataLogs(externalService: ExternalService) extends Actor {
  override def receive: Receive = {
    case LogAll =>
      externalService.getToDoList().invoke().map {
        data =>
          val formattedData = data.foldLeft("")((acc, todo) => acc + todo.toString + "\n")
          Logger.info(formattedData)
      }
  }
}

object DataLogs {

  case object LogAll

  def props(externalService: ExternalService) = Props(classOf[DataLogs], externalService)
}
