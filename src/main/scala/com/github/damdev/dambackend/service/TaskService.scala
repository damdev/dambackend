package com.github.damdev.dambackend.service

import cats.effect.Effect
import cats.implicits._
import com.github.damdev.dambackend.db.{Task, TaskStore}
import com.typesafe.scalalogging.LazyLogging
import doobie.implicits._
import doobie.util.transactor.Transactor
import io.circe.syntax._
import org.http4s.HttpService
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.dsl.impl.QueryParamDecoderMatcher
import Task._

object NameQs extends QueryParamDecoderMatcher[String]("name")

object TaskService extends LazyLogging {

  def service[F[_]: Effect](taskStore: TaskStore)(implicit transactor: Transactor[F]): HttpService[F] = {

    val dsl = Http4sDsl[F]
    import dsl._

    HttpService {
      case GET -> Root / LongVar(id) =>
        taskStore.get(id).transact[F](transactor).flatMap(
          _.map { t: Task =>
            Ok(t.asJson)
          }.getOrElse(NotFound(s"Missing task with id ${id}"))
        )
      case DELETE -> Root / LongVar(id) =>
        taskStore.delete(id).transact(transactor).flatMap(i => Ok(i.toString))
      case GET -> Root => taskStore.all()
        .transact(transactor).flatMap(l => Ok(l.asJson))
      case GET -> Root :? NameQs(name) => taskStore.searchByName(name)
        .transact(transactor).flatMap(l => Ok(l.asJson))
      case r@POST -> Root => r.decodeWith(jsonOf[F, Task], strict = false) { t =>
        taskStore.insert(t).transact(transactor).flatMap(i => Ok(i.toString))
      }
    }
  }

}