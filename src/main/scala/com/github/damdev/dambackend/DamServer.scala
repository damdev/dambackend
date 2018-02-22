package com.github.damdev.dambackend

import cats.effect.{Effect, Sync}
import com.github.damdev.dambackend.config.Config
import com.github.damdev.dambackend.service.OkService
import com.typesafe.scalalogging.LazyLogging
import fs2.{Stream, StreamApp}
import org.http4s.HttpService
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.ExecutionContext

object DamServer extends LazyLogging {
  def serve[F[_]: Effect]()(implicit ec: ExecutionContext): Stream[F, StreamApp.ExitCode] = {
    val router: HttpService[F] = Router(
      "/" -> OkService[F]().service()
    )

    for {
      _ <- Stream.eval(Sync[F].delay(logger.info("Starting server...")))
      exitCode <- BlazeBuilder[F]
        .bindHttp(Config.server.port, Config.server.interface)
        .mountService(router)
        .withExecutionContext(ec).serve
    } yield exitCode
  }
}
