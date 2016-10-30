lazy val root = (project in file(".")).settings(
  organization := "org.scalatra.scalate",
  name := "scalamd",
  version := "1.7.0-SNAPSHOT",
  scalaVersion := "2.11.8",
  crossScalaVersions := Seq("2.12.0-RC2", "2.11.8", "2.10.6"),
  transitiveClassifiers in Global := Seq(Artifact.SourceClassifier),
  incOptions := incOptions.value.withNameHashing(true),
  libraryDependencies ++= Seq(
    "commons-io"   %  "commons-io"   % "1.4"     % Test,
    "commons-lang" %  "commons-lang" % "2.5"     % Test,
    "junit"        %  "junit"        % "4.12"    % Test,
    "org.specs2"   %% "specs2-core"  % "3.8.5.1" % Test
  )
)
