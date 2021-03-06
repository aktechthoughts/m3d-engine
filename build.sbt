import sbt.ExclusionRule

name := "m3d-engine"

version := "1.0"

scalaVersion := "2.11.12"

val sparkVersion = "2.4.0"
val hadoopVersion = "2.8.5"

conflictManager := sbt.ConflictManager.latestTime

mainClass in Compile := Some("com.adidas.analytics.AlgorithmFactory")

// TODO: should be deleted as it exists in the Spark distribution
libraryDependencies += "org.scala-lang" % "scala-library" % scalaVersion.value

libraryDependencies += "joda-time" % "joda-time" % "2.9.3" % Provided
libraryDependencies += "org.joda" % "joda-convert" % "2.1.1"

libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.16"

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion % Provided  withExclusions Vector(
  ExclusionRule("org.apache.hadoop", "hadoop-common"),
  ExclusionRule("org.apache.hadoop", "hadoop-hdfs"),
  ExclusionRule("com.google.guava", "guava")
)

libraryDependencies += "org.apache.spark" %% "spark-hive" % sparkVersion % Provided withExclusions Vector(
  ExclusionRule("org.apache.hadoop", "hadoop-hdfs")
)

libraryDependencies += "org.apache.hadoop" % "hadoop-common" % hadoopVersion % Provided withExclusions Vector(
    ExclusionRule("io.netty", "netty-all")
)
libraryDependencies += "org.apache.hadoop" % "hadoop-hdfs" % hadoopVersion % Provided
libraryDependencies += "org.apache.hadoop" % "hadoop-distcp" % hadoopVersion % Provided

// TODO: replace exiting configuration with pureconfig
//libraryDependencies += "com.github.pureconfig" %% "pureconfig" % "0.9.2"

// Dependencies for test

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test

libraryDependencies += "org.apache.hadoop" % "hadoop-hdfs" % hadoopVersion % Test classifier "tests" withExclusions Vector(
  ExclusionRule("io.netty", "netty-all")
)
libraryDependencies += "org.apache.hadoop" % "hadoop-common" % hadoopVersion % Test classifier "tests" withExclusions Vector(
  ExclusionRule("io.netty", "netty-all")
)


fork in Test := true

// disable parallel execution
parallelExecution in Test := false

// skipping tests when running assembly 
test in assembly := {}