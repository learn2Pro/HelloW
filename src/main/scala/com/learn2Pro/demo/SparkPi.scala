package com.learn2Pro.demo

import scala.math.random
import org.apache.spark.sql.SparkSession

/**
 * Created with IntelliJ IDEA.
 * User: tang
 * Date: 17-1-9
 * Time: 下午4:02
 * To change this template use File | Settings | File Templates.
 */

object SparkPi {
  def main(args: Array[String]) {
    val spark = SparkSession
      .builder
      .appName("Spark Pi")
      .getOrCreate()
    val slices = if (args.length > 0) args(0).toInt else 2
    val n = math.min(100000L * slices, Int.MaxValue).toInt // avoid overflow
    val count = spark.sparkContext.parallelize(1 until n, slices).map { i =>
        val x = random * 2 - 1
        val y = random * 2 - 1
        if (x*x + y*y <= 1) 1 else 0
      }.reduce(_ + _)
    println("Pi is roughly " + 4.0 * count / (n - 1))
    spark.stop()
  }
}

