resolvers += "Typesafe repository" at "https://dl.bintray.com/typesafe/maven-releases/"
resolvers += Resolver.typesafeRepo("releases")

addSbtPlugin("com.lightbend.sbt" % "sbt-javaagent" % "0.1.4")