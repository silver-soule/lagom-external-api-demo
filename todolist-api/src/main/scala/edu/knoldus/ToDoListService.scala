package edu.knoldus

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import play.api.libs.json.{Format, Json}

/**
  * Created by Neelaksh on 26/8/17.
  */
trait ToDoListService extends Service {

  def getToDoData(): ServiceCall[NotUsed, List[ToDoData]]

  override final def descriptor = {
    import Service._
    // @formatter:off
    named("todo-list")
      .withCalls(
        restCall(Method.GET, "/todo/all", getToDoData _)
      )
      .withAutoAcl(true)
    // @formatter:on
  }
}

case class ToDoData(userId: Int, id: Int, title: String, completed: Boolean)

object ToDoData {
  implicit val format: Format[ToDoData] = Json.format[ToDoData]
}
