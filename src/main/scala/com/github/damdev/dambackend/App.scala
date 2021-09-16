package com.github.damdev.dambackend

import java.util.concurrent.{ExecutorService, Executors}

import cats.effect._
import com.github.damdev.dambackend.config.Config

import scala.concurrent.ExecutionContext

object App extends IOApp {

  private val executor: ExecutorService  = Executors.newFixedThreadPool(Config.server.threadPool.size)
  implicit val ec = ExecutionContext.fromExecutor(executor)

  override def run(args: List[String]): IO[ExitCode] =
    DamServer.serve()
}





