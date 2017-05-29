package controllers

import play.api.mvc._
import play.api.Logger
import play.api.libs.json._

import com.fasterxml.jackson.core.JsonParseException

/**
  * Created by bwbecker on 2017-05-23.
  */
class LogCtrl extends Controller {

  implicit private val LogReads = Json.reads[LogMsg]
  implicit private val LogMsgReads = Json.reads[LogBatch]

  private val logger = Logger("client")

  def log = Action { implicit request ⇒

    def handleOneMsg(m: LogMsg): Unit = {
      val m2 = try {
        // Attempt to parse the error message as a JSON object.
        val json = Json.parse(m.m)
        if (json.isInstanceOf[JsObject]) {
          Json.prettyPrint(json).replace("\\n", "\n").replace("\\t", "\t")
        } else m.m
      } catch {
        case e: JsonParseException ⇒
          // Message wasn't a JSON object
          m.m
      }

      val logLevel = m.l
      val msg = s"${m.n} - ${m2} @ ${m.t}"
      (logLevel - 1) / 1000 match {
        case 0 ⇒ logger.trace(msg)
        case 1 ⇒ logger.debug(msg)
        case 2 ⇒ logger.info(msg)
        case 3 ⇒ logger.warn(msg)
        case 4 ⇒ logger.error(msg)
        case _ ⇒ logger.error("Fatal Error: " + msg)
      }
    }

    //println("rawJson: " + request.body.asJson)

    Json.fromJson[LogBatch](request.body.asJson.getOrElse(JsString("error"))) match {
      case JsSuccess(lm: LogBatch, p: JsPath) ⇒
        // Might have several messages sent as a batch
        for (m ← lm.lg) {
          handleOneMsg(m)
        }

      case e: JsError ⇒
        println(request.body.toString)
        Logger.error("Error reading JSON from browser: " + JsError.toJson(e).toString())
    }
    Ok
  }


  private case class LogMsg(l: Int, m: String, n: String, t: Long)

  private case class LogBatch(r: String, lg: Seq[LogMsg])

}

