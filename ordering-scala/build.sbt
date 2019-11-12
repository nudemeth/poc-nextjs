lazy val scalaMainVersion = "2.12.7"
lazy val akkaHttpVersion  = "10.1.5"
lazy val akkaVersion      = "2.5.17"
lazy val phantomVersion   = "2.31.0"
lazy val logbackVersion   = "1.2.3"

lazy val commonSettings = Seq(
  version         := "0.1-SNAPSHOT",
  organization    := "nudemeth",
  scalaVersion    := scalaMainVersion
)

lazy val root = (project in file("."))
  .aggregate(api)
  .dependsOn(api)
  .settings(commonSettings: _*)
  .settings(
    mainClass in (Compile, run) := Some("nudemeth.poc.ordering.api.Server")
  )
  .disablePlugins(RevolverPlugin)


lazy val api = (project in file("./ordering-api"))
  .aggregate(domain, infrastructure)
  .dependsOn(domain, infrastructure)
  .settings(commonSettings: _*)
  .settings(
    name := "ordering-api",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-xml"        % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream"          % akkaVersion,
      
      "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-testkit"         % akkaVersion     % Test,
      "com.typesafe.akka" %% "akka-stream-testkit"  % akkaVersion     % Test,
      "org.scalatest"     %% "scalatest"            % "3.0.5"         % Test,
      "org.scala-lang"    %  "scala-reflect"        % scalaMainVersion,

      "com.outworkers"    %% "phantom-dsl"          % phantomVersion,
      "com.outworkers"    %% "phantom-jdk8"         % phantomVersion,

      "ch.qos.logback"    %  "logback-classic"      % logbackVersion   % Runtime,

      "com.pauldijou"     %% "jwt-core"             % "4.2.0"
    )
  )

lazy val domain = (project in file("./ordering-domain"))
  .settings(commonSettings: _*)
  .settings(
    name := "ordering-domain",
    libraryDependencies ++= Seq(
      "org.scalatest"     %% "scalatest"            % "3.0.5"         % Test
    )
  )
  .disablePlugins(RevolverPlugin)

lazy val infrastructure = (project in file("./ordering-infrastructure"))
  .aggregate(domain)
  .dependsOn(domain)
  .settings(commonSettings: _*)
  .settings(
    name := "ordering-infrastructure",
    libraryDependencies ++= Seq(
      "com.outworkers"    %% "phantom-dsl"          % phantomVersion,
      "com.outworkers"    %% "phantom-jdk8"         % phantomVersion,

      "org.scalatest"     %% "scalatest"            % "3.0.5"         % Test
    )
  )
  .disablePlugins(RevolverPlugin)