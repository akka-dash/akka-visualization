package io.akka.visualization

import spray.routing.HttpServiceActor
import akka.actor.Props
import akka.actor.ActorRef
import scala.concurrent.ExecutionContext

/**
 * @author wilmveel
 */
object VisualizationService {
  def name = "visualisation-service"
  def props() = Props(new VisualizationService())
}

class VisualizationService() extends HttpServiceActor with VisualizationRoute {

  lazy val executionContext: ExecutionContext = context.dispatcher

  override val visualizationActor: ActorRef = context.actorOf(VisualizationActor.props(), VisualizationActor.name)

  def receive = runRoute(visualizationRoute)
}