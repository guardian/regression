name := "regression-exercises"
version := "1.0"
scalaVersion := "2.12.8"

resolvers += Resolver.bintrayRepo("cibotech", "public")
libraryDependencies += "com.cibo" %% "evilplot" % "0.6.3"
libraryDependencies += "com.cibo" %% "evilplot-repl" % "0.6.3"
libraryDependencies += "org.specs2" %% "specs2-core" % "4.4.1" % Test