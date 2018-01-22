
organization := "com.github.damdev"

name := "dambackend"

scalaVersion := "2.12.3"

fork in run := true

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-unchecked",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture")

val Http4sVersion   = "0.17.2"
val CirceVersion    = "0.8.0"

libraryDependencies ++= Seq(
  "org.http4s"              %%  "http4s-blaze-server"     % Http4sVersion,
  "org.http4s"              %%  "http4s-circe"            % Http4sVersion,
  "org.http4s"              %%  "http4s-dsl"              % Http4sVersion,
  "org.http4s"              %%  "http4s-blaze-client"     % Http4sVersion,
  "org.tpolecat"            %%  "doobie-core-cats"        % "0.4.4",
  "ch.qos.logback"          %   "logback-classic"         % "1.2.1",
  "com.typesafe.scala-logging" %% "scala-logging"         % "3.7.2",
  "com.github.pureconfig"   %%  "pureconfig"              % "0.7.1",
  "io.circe"                %%  "circe-generic"           % CirceVersion,
  "io.circe"                %%  "circe-java8"             % CirceVersion,
  "com.h2database"          %   "h2"                      % "1.4.196",
  "com.h2database"          %   "h2"                      % "1.4.196"                 % "test",
  "org.scalatest"           %   "scalatest_2.12"          % "3.0.3"                   % "test",
  "org.mockito"             %   "mockito-core"            % "2.7.22"                  % "test"
)

libraryDependencies ++= Seq(
  "io.kamon" %% "kamon-core" % "1.0.0",
  "io.kamon" %% "kamino-reporter" % "1.0.0"

)

mainClass := Some("com.github.damdev.dambackend.Server")
