package io.akka.visualization

import io.akka.visualization.VisualizationActor.ActorNode
import spray.json.{ DefaultJsonProtocol, JsArray, JsObject, JsString, JsValue, NullOptions, RootJsonFormat }

import scala.collection.mutable

/**
 * @author bekiroguz
 */

trait VisualizationMarshalling extends DefaultJsonProtocol with NullOptions {

  implicit object ActorNodeJsonFormat extends RootJsonFormat[VisualizationActor.ActorNode] {
    override def write(obj: VisualizationActor.ActorNode): JsValue = JsObject(
      "name" -> JsString(obj.name),
      "state" -> JsString(obj.state.getOrElse("")),
      "parent" -> JsString(obj.parent.map(_.name).getOrElse("None")),
      "children" -> JsArray(obj.children.map(x => write(x._2)).toList: _*)
    )

    override def read(json: JsValue): VisualizationActor.ActorNode = {
      // left empty on purpose, no need for deserialization...
      // returning a default empty root node.
      ActorNode("root", None, None, mutable.Map.empty)
    }
  }

}