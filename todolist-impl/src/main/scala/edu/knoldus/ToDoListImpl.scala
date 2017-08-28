package edu.knoldus

import javax.inject.Singleton
import akka.NotUsed
import akka.actor.ActorSystem
import com.lightbend.lagom.scaladsl.api.ServiceCall
import edu.knoldus.DataLogs.LogAll
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

/**
  * Created by Neelaksh on 26/8/17.
  */
@Singleton
class ToDoListImpl(externalService: ExternalService) extends ToDoListService {
  val system = ActorSystem("system")
  val logActor = system.actorOf(DataLogs.props(externalService))
  val cancellable =
    system.scheduler.schedule(
      0 milliseconds,
      20 seconds,
      logActor,
      LogAll)

  override def getToDoData(): ServiceCall[NotUsed, List[ToDoData]] = ServiceCall { _ =>
    val result: Future[List[ToDoData]] = externalService.getToDoList().invoke()
    result.map(response => response)
  }
}
