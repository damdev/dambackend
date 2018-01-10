package com.github.damdev.dambackend

import java.util.concurrent.{ExecutorService, Executors}

import com.github.damdev.dambackend.config.Config
import com.github.damdev.dambackend.service.OkService
import fs2.{Stream, Task}
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.util.StreamApp

import scala.concurrent.ExecutionContext

object Server extends StreamApp {

  private val executor: ExecutorService  = Executors.newFixedThreadPool(Config.server.threadPool.size)

  override def stream(args: List[String]): Stream[Task, Nothing] = {

    val router = Router(
      "/" -> OkService().service()
    )

    BlazeBuilder
      .bindHttp(Config.server.port, Config.server.interface)
      .mountService(router)
      .withExecutionContext(ExecutionContext.fromExecutor(executor))
      .serve
  }
}



