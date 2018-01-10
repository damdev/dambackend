package com.github.damdev.dambackend.config

import com.typesafe.config.ConfigFactory

object Config {

  lazy val config = ConfigFactory.load

  case class ThreadPool(size:Int)
  case class Server(interface: String, port: Int, threadPool: ThreadPool)

  lazy val server: Server = pureconfig.loadConfigOrThrow[Server](config.getConfig("server"))


}
