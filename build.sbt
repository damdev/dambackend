import sbt.Resolver

organization := "com.github.damdev"

name := "dambackend"

scalaVersion := "2.12.6"

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

val Http4sVersion   = "0.18.11"
val CirceVersion    = "0.9.3"

resolvers ++= Seq(
  Resolver.bintrayRepo("kamon-io", "snapshots"),
  Resolver.bintrayRepo("kamon-io", "releases")
)

libraryDependencies ++= Seq(
  "org.http4s"              %%  "http4s-blaze-server"     % Http4sVersion,
  "org.http4s"              %%  "http4s-circe"            % Http4sVersion,
  "org.http4s"              %%  "http4s-dsl"              % Http4sVersion,
  "org.http4s"              %%  "http4s-blaze-client"     % Http4sVersion,
  "ch.qos.logback"          %   "logback-classic"         % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging"         % "3.9.0",
  "com.github.pureconfig"   %%  "pureconfig"              % "0.9.1",
  "io.circe"                %%  "circe-generic"           % CirceVersion,
  "io.circe"                %%  "circe-java8"             % CirceVersion,
  "org.scalatest"           %%   "scalatest"              % "3.0.5"                   % "test",
  "org.mockito"             %   "mockito-core"            % "2.18.3"                  % "test"
)

libraryDependencies ++= Seq(
  "io.kamon" %% "kamino-reporter" % "1.0.0",
  //"io.kamon" %% "kamon-system-metrics" % "1.0.0",
  "io.kamon" %% "kamon-http4s" % "1.0.7"

)

javaAgents += "io.kamon" % "kanela-agent" % "0.0.11" % "compile;runtime;test"

enablePlugins(JavaAgent)

mainClass := Some("com.github.damdev.dambackend.App")

fork := true

lazy val alpn_boot = "org.mortbay.jetty.alpn" % "alpn-boot" % "8.1.11.v20170118"

// Adds ALPN to the boot classpath for Http2 support
libraryDependencies += alpn_boot

javaOptions in run ++= addAlpnPath((managedClasspath in Runtime).value)

/* Helper Functions */

def addAlpnPath(attList: Keys.Classpath): Seq[String] = {
  for {
    file <- attList.map(_.data)
    path = file.getAbsolutePath if path.contains("jetty.alpn")
  } yield { println(s"Alpn path: $path"); "-Xbootclasspath/p:" + path}
}
