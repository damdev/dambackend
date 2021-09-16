package com.github.damdev.dambackend.config

import com.typesafe.config.ConfigFactory
import pureconfig.generic.auto._
import pureconfig.ConfigSource

object Config {

  lazy val config = ConfigFactory.load

  case class ThreadPool(size:Int)
  case class Server(interface: String, port: Int, threadPool: ThreadPool)

  lazy val server: Server = ConfigSource.fromConfig(config.getConfig("server")).loadOrThrow[Server]

}
