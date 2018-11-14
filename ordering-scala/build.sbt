lazy val akkaHttpVersion = "10.1.5"
lazy val akkaVersion    = "2.5.17"

lazy val commonSettings = Seq(
  version         := "0.1-SNAPSHOT",
  organization    := "com.nudemeth",
  scalaVersion    := "2.12.7"
)

lazy val root = (project in file("."))
  .aggregate(api)
  .dependsOn(api)
  .settings(commonSettings: _*)
  /*.settings(
    mainClass in (Compile, run) := Some("com.nudemeth.poc.ordering.api.QuickstartServer")
  )*/

lazy val api = (project in file("./ordering-api"))
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
      "org.scalatest"     %% "scalatest"            % "3.0.5"         % Test
    )
  )

mainClass in (Compile, run) := Some("com.nudemeth.poc.ordering.api.QuickstartServer")