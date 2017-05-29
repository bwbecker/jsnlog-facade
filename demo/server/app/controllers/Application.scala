package controllers

import play.api.mvc._
import shared.SharedMessages

class Application extends Controller {

  def index = Action {
    Ok(views.html.index(SharedMessages.itWorks))
  }

  def takes2sec = Action {
    Thread.sleep(2000)
    Ok("Waited 2 seconds")
  }
}
