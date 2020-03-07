lazy val scalaMainVersion = "2.12.10"
lazy val akkaHttpVersion  = "10.1.11"
lazy val akkaVersion      = "2.6.1"
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
  .aggregate(domain, infrastructure, util)
  .dependsOn(domain, infrastructure, util)
  .settings(commonSettings: _*)
  .settings(
    name := "ordering-api",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-xml"        % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream-typed"    % akkaVersion,
      
      "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-testkit"         % akkaVersion     % Test,
      "com.typesafe.akka" %% "akka-stream-testkit"  % akkaVersion     % Test,
      "org.scalatest"     %% "scalatest"            % "3.0.5"         % Test,
      "org.scala-lang"    %  "scala-reflect"        % scalaMainVersion,

      "com.datastax.oss"  %  "java-driver-core"     % "4.3.0",

      "ch.qos.logback"    %  "logback-classic"      % logbackVersion   % Runtime,

      "com.pauldijou"     %% "jwt-core"             % "4.2.0",
      "com.newmotion"     %% "akka-rabbitmq"        % "5.1.2"
    )
  )

lazy val domain = (project in file("./ordering-domain"))
  .aggregate(util)
  .dependsOn(util)
  .settings(commonSettings: _*)
  .settings(
    name := "ordering-domain",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed"     % akkaVersion,
      "org.scalatest"     %% "scalatest"            % "3.0.5"         % Test
    )
  )
  .disablePlugins(RevolverPlugin)

lazy val infrastructure = (project in file("./ordering-infrastructure"))
  .aggregate(domain, util)
  .dependsOn(domain, util)
  .settings(commonSettings: _*)
  .settings(
    name := "ordering-infrastructure",
    libraryDependencies ++= Seq(
      "org.scala-lang"    %  "scala-reflect"        % scalaMainVersion,

      "com.typesafe"      %  "config"               % "1.4.0",

      "com.outworkers"    %% "phantom-dsl"          % phantomVersion,
      "com.outworkers"    %% "phantom-jdk8"         % phantomVersion,

      "com.newmotion"     %% "akka-rabbitmq"        % "5.1.2",

      "org.scalatest"     %% "scalatest"            % "3.0.5"         % Test
    )
  )
  .disablePlugins(RevolverPlugin)

lazy val util = (project in file("./ordering-util"))
  .settings(commonSettings: _*)
  .settings(
    name := "ordering-util"
  )