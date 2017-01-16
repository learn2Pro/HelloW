package com.learn2Pro.demo

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

/**
  * Created with IntelliJ IDEA.
  * User: tang
  * Date: 17-1-9
  * Time: 下午3:48
  * To change this template use File | Settings | File Templates.
  */
object SparkWordCount {
  def FILE_NAME: String = "word_count_results_";

  def main(args: Array[String]) {
    if (args.length < 1) {
      println("Usage:SparkWordCount Filename");
      System.exit(1);
    }

    val conf = new SparkConf().setAppName("Spark Exercise: Spark Version Word Count Program");
    val sc = new SparkContext(conf);
    val textFile = sc.textFile(args(0));
    val wordCounts = textFile.flatMap(line => line.split("\\t")).map(
      word => (word, 1)).reduceByKey((a, b) => a + b)
    wordCounts.saveAsTextFile(args(1) + FILE_NAME + System.currentTimeMillis());
    println("Word Count program running results are successfully saved.");
  }
}

