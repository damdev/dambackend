package com.github.damdev.dambackend

import cats.effect.{Effect, Sync}
import com.github.damdev.dambackend.config.Config
import com.github.damdev.dambackend.db.{DDLs, TaskStore}
import com.github.damdev.dambackend.service.{OkService, TaskService}
import com.typesafe.scalalogging.LazyLogging
import doobie.util.transactor.Transactor
import fs2.{Stream, StreamApp}
import org.http4s.HttpService
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.ExecutionContext
import doobie.implicits._

object DamServer extends LazyLogging {

  def serve[F[_]: Effect]()(implicit ec: ExecutionContext, transactor: Transactor[F]): Stream[F, StreamApp.ExitCode] = {
    val router: HttpService[F] = Router(
      "/" -> OkService[F]().service()
    )

    for {
      _ <- Stream.eval(Sync[F].delay(logger.info("Starting server...")))
      taskStore <- Stream(new TaskStore())
      _ <- Stream.eval(DDLs.createAll().transact(transactor))
      exitCode <- BlazeBuilder[F]
        .bindHttp(Config.server.port, Config.server.interface)
        .mountService(router)
        .mountService(TaskService.service[F](taskStore), "/tasks")
        .withExecutionContext(ec).serve
    } yield exitCode
  }
}
