package io.akka.visualization

import java.util.UUID

import akka.actor.{ Actor, Props }

/**
 * @author wilmveel
 */
object TestActor {

  def name(partyId: String) = s"test-$partyId"
  def props() = Props(new TestActor())

  case object UpdateState

}

class TestActor() extends Actor with ActorVisualization {

  import TestActor._

  override def receive: Receive = {
    case UpdateState => updateVisualizationState(UUID.randomUUID().toString)
  }
}