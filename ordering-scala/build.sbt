lazy val akkaHttpVersion  = "10.1.5"
lazy val akkaVersion      = "2.5.17"
lazy val phantomVersion   = "2.29.0"

lazy val commonSettings = Seq(
  version         := "0.1-SNAPSHOT",
  organization    := "nudemeth",
  scalaVersion    := "2.12.7"
)

lazy val root = (project in file("."))
  .aggregate(api)
  .dependsOn(api)
  .settings(commonSettings: _*)
  .settings(
    mainClass in (Compile, run) := Some("nudemeth.poc.ordering.api.Server"),
  )
  .disablePlugins(RevolverPlugin)


lazy val api = (project in file("./ordering-api"))
  .aggregate(domain)
  .dependsOn(domain)
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

      "com.outworkers"    %% "phantom-dsl"          % phantomVersion,
      "com.outworkers"    %% "phantom-jdk8"         % phantomVersion,
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