lazy val Scala212 = "2.12.8"
lazy val scalatestVersion = SettingKey[String]("scalatestVersion")

lazy val root = (project in file(".")).settings(
  organization := "org.scalatra.scalate",
  name := "scalamd",
  version := "1.7.2",
  scalaVersion := Scala212,
  crossScalaVersions := Seq(Scala212, "2.11.12", "2.10.7", "2.13.0"),
  transitiveClassifiers in Global := Seq(Artifact.SourceClassifier),
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
  scalatestVersion := {
    scalaBinaryVersion.value match {
      case "2.13" => "3.1.0-SNAP12"
      case _ => "3.0.5"
    }
  },
  libraryDependencies ++= Seq(
    "commons-io"    %  "commons-io" % "2.6"                  % Test
  ),
  libraryDependencies += {
    // TODO https://github.com/scalatest/scalatest/issues/1601
    if(scalaBinaryVersion.value == "2.13")
      "org.scalatest" % "scalatest_2.13.0-RC3" % scalatestVersion.value % Test
    else
      "org.scalatest" %% "scalatest" % scalatestVersion.value % Test
  },
  publishMavenStyle := true,
  publishTo := sonatypePublishTo.value,
  pomIncludeRepository := { x => false },
  pomExtra := <url>https://github.com/scalatra/scalamd/</url>
  <licenses>
    <license>
      <name>Apache License, Version 2.0</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:scalatra/scalamd.git</url>
    <connection>scm:git:git@github.com:scalatra/scalamd.git</connection>
  </scm>
  <developers>
    <developer>
      <id>rossabaker</id>
      <name>Ross A. Baker</name>
      <url>http://www.rossabaker.com/</url>
    </developer>
    <developer>
      <id>seratch</id>
      <name>Kazuhiro Sera</name>
      <url>https://github.com/seratch</url>
    </developer>
  </developers>
)
