scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

addSbtPlugin("org.xerial.sbt"   % "sbt-sonatype"    % "2.3")
addSbtPlugin("com.jsuereth"     % "sbt-pgp"         % "1.1.1")
addSbtPlugin("org.scalariform"  % "sbt-scalariform" % "1.6.0")
