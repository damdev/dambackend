package com.github.damdev.dambackend.db

import java.util.concurrent.Executors

import cats.effect.Effect
import com.zaxxer.hikari.HikariDataSource
import doobie.util.transactor.Transactor

object DamTransactor {

  private def dataSource: HikariDataSource = {
    val ds = new HikariDataSource
    ds.setPoolName("Alfa-Hikari-Pool")
    ds.setMaximumPoolSize(10)
    ds.setDriverClassName("org.h2.Driver")
    ds.setJdbcUrl("jdbc:h2:mem:dam_db;DB_CLOSE_DELAY=-1")
    ds.addDataSourceProperty("user", "sa")
    ds.addDataSourceProperty("password", "")
    ds.addDataSourceProperty("connectTimeout", 1000)
    ds.addDataSourceProperty("log", 1)
    ds.setThreadFactory(Executors.defaultThreadFactory())
    ds
  }

  def transactor[F[_]: Effect] = Transactor.fromDataSource[F](dataSource)
}
