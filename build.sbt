import sbt.Resolver

organization := "com.github.damdev"

name := "dambackend"

scalaVersion := "2.13.6"

run / fork := true

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
  // "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture")

val Http4sVersion   = "0.23.3"
val CirceVersion    = "0.14.1"

resolvers ++= Seq(
  Resolver.bintrayRepo("kamon-io", "snapshots"),
  Resolver.bintrayRepo("kamon-io", "releases")
)

libraryDependencies ++= Seq(
  "org.http4s"              %%  "http4s-blaze-server"     % Http4sVersion,
  "org.http4s"              %%  "http4s-circe"            % Http4sVersion,
  "org.http4s"              %%  "http4s-dsl"              % Http4sVersion,
  "org.http4s"              %%  "http4s-blaze-client"     % Http4sVersion,
  "ch.qos.logback"          %   "logback-classic"         % "1.2.6",
  "com.typesafe.scala-logging" %% "scala-logging"         % "3.9.4",
  "com.github.pureconfig"   %%  "pureconfig"              % "0.16.0",
  "io.circe"                %%  "circe-core"              % CirceVersion,
  "io.circe"                %%  "circe-generic"           % CirceVersion,
  "io.circe"                %%  "circe-parser"            % CirceVersion,
  "org.scalatest"           %%   "scalatest"              % "3.2.9"                   % "test",
  "org.mockito"             %   "mockito-core"            % "3.12.4"                  % "test"
)

Compile / run / mainClass := Some("com.github.damdev.dambackend.App")

fork := true

// lazy val alpn_boot = "org.mortbay.jetty.alpn" % "alpn-boot" % "8.1.11.v20170118"

// // Adds ALPN to the boot classpath for Http2 support
// libraryDependencies += alpn_boot

// javaOptions in run ++= addAlpnPath((managedClasspath in Runtime).value)

// /* Helper Functions */

// def addAlpnPath(attList: Keys.Classpath): Seq[String] = {
//   for {
//     file <- attList.map(_.data)
//     path = file.getAbsolutePath if path.contains("jetty.alpn")
//   } yield { println(s"Alpn path: $path"); "-Xbootclasspath/p:" + path}
// }
