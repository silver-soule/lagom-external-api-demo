package edu.knoldus
import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import edu.knoldus.DataLogs.LogAll
import play.api.Logger

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by Neelaksh on 26/8/17.
  */
class ToDoListImpl(externalService: ExternalService) extends ToDoListService{

  val system = akka.actor.ActorSystem("system")

  val logActor = system.actorOf(DataLogs.props(externalService))

  val cancellable =
  system.scheduler.schedule(
    0 milliseconds,
    20 seconds,
    logActor,
    LogAll)

  override def getToDoData(): ServiceCall[NotUsed, List[ToDoData]] = ServiceCall{ _ =>
    val result: Future[List[ToDoData]] = externalService.getToDoList().invoke()
    Logger.info(s"n\n\n\n\n$result")
    result.map(response => response)
  }

}

object ToDoListImpl{

}
