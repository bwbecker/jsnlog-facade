package controllers

import javax.inject._

import play.api.mvc._
import shared.SharedMessages

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index(SharedMessages.itWorks))
  }

  def takes2sec = Action {
    Thread.sleep(2000)
    Ok("Waited 2 seconds")
  }
}
