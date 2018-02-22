package com.github.damdev.dambackend

import java.util.concurrent.{ExecutorService, Executors}

import cats.effect._
import com.github.damdev.dambackend.config.Config
import fs2.StreamApp.ExitCode
import fs2.{Stream, StreamApp}
import kamon.Kamon
import kamon.system.SystemMetrics

import scala.concurrent.ExecutionContext

object App extends StreamApp[IO] {

  import kamon.kamino.{KaminoReporter, KaminoTracingReporter}

  Kamon.addReporter(new KaminoReporter())
  Kamon.addReporter(new KaminoTracingReporter())
  SystemMetrics.startCollecting()

  private val executor: ExecutorService  = Executors.newFixedThreadPool(Config.server.threadPool.size)
  implicit val ec = ExecutionContext.fromExecutor(executor)

  override def stream(args: List[String], requestShutdown: IO[Unit]): Stream[IO, ExitCode] =
    DamServer.serve[IO]()
}





