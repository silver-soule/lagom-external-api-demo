package edu.knoldus

/**
  * Created by Neelaksh on 26/8/17.
  */

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader}
import com.softwaremill.macwire.wire
import play.api.libs.ws.ahc.AhcWSComponents

class ToDoListLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new ToDoListApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new ToDoListApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[ToDoListService])
}

abstract class ToDoListApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  lazy val externalService = serviceClient.implement[ExternalService]
  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[ToDoListService](wire[ToDoListImpl])
}