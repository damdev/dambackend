package com.github.damdev.dambackend.service

import com.typesafe.scalalogging.LazyLogging
import kamon.Kamon
import kamon.context.{Context, Key}
import org.http4s.HttpService
import org.http4s.dsl._

object OkService {
  def apply(): OkService = new OkService()
}

class OkService() extends LazyLogging {

  def service(): HttpService =
    HttpService {
      case GET -> Root / "ok" => ok()
    }


  def ok() = Kamon.withContext(Kamon.currentContext().withKey(Key.local("damkey", 0), 10)) {
    Ok("ok")
  }
}