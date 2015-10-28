// TODO no package definition. to be fixed.

package io.akka.visualization

import akka.actor.{ ActorSystem, PoisonPill, actorRef2Scala }
import akka.io.IO
import spray.can.Http
import spray.can.Http.Bind

import scala.concurrent.duration._
import scala.language.postfixOps

/**
 * @author wilmveel
 */
object Boot extends App {

  implicit val system = ActorSystem("akka-visualization")

  // Start visualization server
  val testActor1 = system.actorOf(TestActor.props(), TestActor.name("001"))
  val testActor2 = system.actorOf(TestActor.props(), TestActor.name("002"))
  val testActor3 = system.actorOf(TestActor.props(), TestActor.name("003"))

  import system.dispatcher
  system.scheduler.schedule(1 seconds, 1 seconds) { testActor1 ! TestActor.UpdateState }
  system.scheduler.schedule(3 seconds, 3 seconds) { testActor2 ! TestActor.UpdateState }
  system.scheduler.schedule(5 seconds, 5 seconds) { testActor3 ! TestActor.UpdateState }

  system.scheduler.schedule(1 seconds, 5 seconds) {
    val createActor = system.actorOf(TestActor.props(), TestActor.name("createActor"))
    system.scheduler.scheduleOnce(3 seconds) {
      createActor ! PoisonPill
    }
  }

  val visualizationService = system.actorOf(VisualizationService.props(), VisualizationService.name)
  IO(Http) ! Bind(listener = visualizationService, interface = "0.0.0.0", port = 9000)

}