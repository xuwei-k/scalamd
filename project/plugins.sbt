scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

addSbtPlugin("org.xerial.sbt"   % "sbt-sonatype"    % "3.9.5")
addSbtPlugin("com.jsuereth"     % "sbt-pgp"         % "1.1.2")
addSbtPlugin("org.scalariform"  % "sbt-scalariform" % "1.8.3")
