package com.github.damdev.dambackend.db

import doobie._
import doobie.implicits._

object DDLs {

  def createAll(): ConnectionIO[Int] =
    sql"create table TASKS(id  int NOT NULL AUTO_INCREMENT, name  VARCHAR(30) not null, description VARCHAR(255) not null);"
      .update.run

}