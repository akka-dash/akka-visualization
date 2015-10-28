package io.akka.visualization

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import spray.httpx.SprayJsonSupport._
import spray.routing.HttpService

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.language.postfixOps

/**
 * @author wilmveel
 */

trait VisualizationRoute extends HttpService with VisualizationMarshalling {

  val visualizationActor: ActorRef

  implicit val executionContext: ExecutionContext
  implicit val visualizationTimeout: Timeout = 5 seconds

  val visualizationRoute = {
    path("visualization") {
      get {
        def response = visualizationActor.ask(VisualizationActor.ActorNodeTree).mapTo[VisualizationActor.ActorNode]
        onSuccess(response) {
          case result => complete(result)
        }
      }
    } ~
      path("tree") {
        getFromResource("visualization/tree.html")
      }
  }

}