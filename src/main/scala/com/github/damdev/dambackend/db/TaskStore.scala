package com.github.damdev.dambackend.db

import doobie.free.connection.ConnectionIO
import doobie.implicits._
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

case class Task(id: Option[Long], name: String, description: String)

object Task {
  implicit val encoder: Encoder[Task] = deriveEncoder
  implicit val decoder: Decoder[Task] = deriveDecoder
}

class TaskStore {
  def searchByName(name: String): ConnectionIO[List[Task]] =
    sql"SELECT id, name, description from tasks where name = $name;".query[Task].to[List]

  def all(): ConnectionIO[List[Task]] =
    sql"SELECT id, name, description from tasks;".query[Task].to[List]

  def insert(record: Task): ConnectionIO[Int] =
    sql"INSERT INTO tasks(id, name, description) values(${record.id}, ${record.name}, ${record.description});"
      .update.run

  def get(id: Long): ConnectionIO[Option[Task]] =
    sql"SELECT id, name, description from tasks where id = $id;".query[Task].option

  def delete(id: Long): ConnectionIO[Int] =
    sql"DELETE FROM tasks where id = $id;".update.run
}
