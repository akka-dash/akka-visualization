package io.akka.visualization

import akka.actor.Actor
import akka.actor.ActorLogging

/**
 * @author wilmveel
 */

trait ActorVisualization extends Actor with ActorLogging {

  override def preStart() = {
    context.system.eventStream.publish(VisualizationActor.StartActorEvent(self.path.toStringWithoutAddress))
    super.preStart()
  }

  override def postStop() = {
    context.system.eventStream.publish(VisualizationActor.StopActorEvent(self.path.toStringWithoutAddress))
    super.postStop()
  }

  def updateVisualizationState(state: String) = {
    context.system.eventStream.publish(VisualizationActor.StateActorEvent(self.path.toStringWithoutAddress, state))
  }

}