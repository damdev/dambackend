package com.github.damdev.dambackend.service

import cats.effect.Effect
import com.typesafe.scalalogging.LazyLogging
import kamon.Kamon
import kamon.context.Key
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl

object OkService {
  def apply[F[_]: Effect](): OkService[F] = new OkService[F]()
}

class OkService[F[_]: Effect]() extends LazyLogging {

  implicit val dsl = Http4sDsl[F]
  import dsl._

  def service(): HttpService[F] =
    HttpService {
      case GET -> Root / "ok" => ok()
    }


  def ok() = Kamon.withContext(Kamon.currentContext().withKey(Key.local("damkey", 0), 10)) {
    Ok("ok")
  }
}