package com.github.damdev.dambackend

import com.github.damdev.dambackend.config.Config
import com.github.damdev.dambackend.service.OkService
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext
import cats.effect._
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router

object DamServer extends LazyLogging {
  def serve()(implicit ec: ExecutionContext): IO[ExitCode] = {
    val router = Router[IO](
      "/" -> OkService[IO]().service()
    ).orNotFound

    BlazeServerBuilder[IO](ec)
        .bindHttp(Config.server.port, Config.server.interface)
        .withHttpApp(router)
        .resource
        .use(_ => IO.never)
        .as(ExitCode.Success)
  }
}
