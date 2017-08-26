package edu.knoldus

/**
  * Created by Neelaksh on 26/8/17.
  */
import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

trait ExternalService extends Service {

  def getToDoList():ServiceCall[NotUsed, List[ToDoData]]

  override final def descriptor = {
    import Service._
    // @formatter:off
    named("external-service")
      .withCalls(
        pathCall("/todos", getToDoList _)
      ).withAutoAcl(true)
    // @formatter:on
  }
}
