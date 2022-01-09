lazy val Scala212 = "2.12.15"
lazy val scalatestVersion = SettingKey[String]("scalatestVersion")

publish / skip := true

lazy val scalamd = (crossProject(JVMPlatform, JSPlatform, NativePlatform) in file(".")).settings(
  organization := "org.scalatra.scalate",
  name := "scalamd",
  version := "1.7.4-SNAPSHOT",
  scalaVersion := Scala212,
  crossScalaVersions := Seq(Scala212, "2.11.12", "2.13.7", "3.1.0"),
  transitiveClassifiers in Global := Seq(Artifact.SourceClassifier),
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
  scalatestVersion := "3.2.10",
  libraryDependencies += {
    "org.scalatest" %%% "scalatest" % scalatestVersion.value % Test
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
).jsSettings(
  scalacOptions += {
    val hash = sys.process.Process("git rev-parse HEAD").lineStream_!.head
    val a = (LocalRootProject / baseDirectory).value.toURI.toString
    val g = "https://raw.githubusercontent.com/scalatra/scalamd/" + hash
    val key = scalaBinaryVersion.value match {
      case "3" =>
        "-scalajs-mapSourceURI"
      case _ =>
        "-P:scalajs:mapSourceURI"
    }
    s"${key}:$a->$g/"
  },
).nativeSettings(
  Compile / doc / scalacOptions --= {
    // TODO remove this workaround
    // https://github.com/scala-native/scala-native/issues/2503
    if (scalaBinaryVersion.value == "3") {
      (Compile / doc / scalacOptions).value.filter(_.contains("-Xplugin"))
    } else {
      Nil
    }
  },
  disableScala3Tests, // TODO scalatest
)

lazy val disableScala3Tests = Def.settings(
  libraryDependencies := {
    if (scalaBinaryVersion.value == "3") {
      libraryDependencies.value.filterNot(_.organization.contains("org.scalatest"))
    } else {
      libraryDependencies.value
    }
  },
  Test / sources := {
    if (scalaBinaryVersion.value == "3") {
      Nil
    } else {
      (Test / sources).value
    }
  },
)
