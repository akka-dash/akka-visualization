package io.akka.visualization

import akka.actor.{ Actor, ActorLogging, Props }

import scala.collection.mutable

/**
 * @author wilmveel
 */

object VisualizationActor {

  def name = "visualization-actor"
  def props() = Props(new VisualizationActor())

  case class ActorNode(name: String, var state: Option[String], parent: Option[ActorNode], children: mutable.Map[String, ActorNode])

  sealed trait ActorEvent
  case class StartActorEvent(path: String) extends ActorEvent
  case class StopActorEvent(path: String) extends ActorEvent
  case class StateActorEvent(path: String, state: String) extends ActorEvent

  case object ActorNodeTree
}

class VisualizationActor extends Actor with ActorLogging {

  import VisualizationActor._

  val root = ActorNode("root", None, None, mutable.Map.empty)

  override def preStart() = {
    context.system.eventStream.subscribe(context.self, classOf[ActorEvent])
    log.info("VisualizationActor -> preStart")
    super.preStart()
  }

  def receive = {

    case ActorNodeTree => sender ! root

    case StartActorEvent(path) =>
      log.info("VisualizationActor -> StartActorEvent -> " + path)
      findActorNode(path)

    case StopActorEvent(path) =>
      val actor = findActorNode(path)
      actor.parent.get.children.remove(actor.name)

    case StateActorEvent(path, state) =>
      findActorNode(path).state = Some(state)

  }

  def findActorNode(path: String): ActorNode = {
    var parentActorNode: ActorNode = root
    path.split("/").tail.foreach { name =>
      if (parentActorNode.children.contains(name)) {
        parentActorNode = parentActorNode.children(name)
      } else {
        val newActorNode = ActorNode(name, None, Some(parentActorNode), mutable.Map.empty)
        parentActorNode.children.put(name, newActorNode)
        parentActorNode = newActorNode
      }
    }
    parentActorNode
  }
}
