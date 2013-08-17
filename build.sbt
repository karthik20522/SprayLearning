organization  := "com.example"

version       := "0.1"

scalaVersion  := "2.10.2"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/"
)

libraryDependencies ++= Seq(
  "io.spray"            %   "spray-can"     	% "1.2-M8",
  "io.spray"            %   "spray-routing" 	% "1.2-M8",
  "io.spray"            %   "spray-testkit" 	% "1.2-M8" % "test",
  "com.typesafe.akka"   %%  "akka-actor"    	% "2.2.0-RC1",
  "com.typesafe.akka"   %%  "akka-testkit"  	% "2.2.0-RC1" % "test",
  "org.specs2"          %%  "specs2"        	% "1.14" % "test",
  "org.json4s" 			%% 	"json4s-native" 	% "3.2.4",
  "org.mongodb" 		%% "casbah" 			% "2.6.2",
  "com.typesafe" 		%% "scalalogging-slf4j" % "1.0.1",
  "org.slf4j" 			% "slf4j-api" 			% "1.7.1",
  "org.slf4j" 			% "log4j-over-slf4j" 	% "1.7.1",
  "ch.qos.logback" 		% "logback-classic" 	% "1.0.3"
)

seq(Revolver.settings: _*)
