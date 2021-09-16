package com.github.damdev.dambackend.service

import cats.effect.Sync
import com.typesafe.scalalogging.LazyLogging
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object OkService {
  def apply[F[_]: Sync](): OkService[F] = new OkService[F]()
}

class OkService[F[_]: Sync]() extends LazyLogging {

  implicit val dsl = Http4sDsl[F]
  import dsl._

  def service(): HttpRoutes[F] =
    HttpRoutes.of {
      case GET -> Root / "ok" => ok()
    }


  def ok() = Ok("ok")
}