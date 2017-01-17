package com.learn2Pro.demo

import org.apache.log4j.{Level, Logger}
import org.apache.spark.internal.Logging
/**
  * Created with IntelliJ IDEA.
  * User: tang
  * Date: 2017/1/16
  * Time: 18:07
  * To change this template use File | Settings | File Templates.
  */

/** Utility functions for Spark Streaming examples. */
object StreamingExamples {

  /** Set reasonable logging levels for streaming if the user has not configured log4j. */
  def setStreamingLogLevels() {
    val log4jInitialized = Logger.getRootLogger.getAllAppenders.hasMoreElements
    if (!log4jInitialized) {
      // We first log something to initialize Spark's default logging, then we override the
      // logging level.
      println("Setting log level to [WARN] for streaming example." +
        " To override add a custom log4j.properties to the classpath.")
      Logger.getRootLogger.setLevel(Level.WARN)
    }
  }
}
