package com.learn2Pro.demo

import org.apache.spark.ml.feature.Word2Vec
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession

/**
  * Created with IntelliJ IDEA.
  * User: tang
  * Date: 2017/1/19
  * Time: 18:17
  * To change this template use File | Settings | File Templates.
  */
object Word2VecExample {
  def main(args: Array[String]) {
    val spark = SparkSession
      .builder
      .appName("Word2Vec example")
      .getOrCreate()

    // $example on$
    // Input data: Each row is a bag of words from a sentence or document.
    val documentDF = spark.createDataFrame(Seq(
      "Hi I heard about Spark".split(" "),
      "I wish Java could use case classes".split(" "),
      "Logistic regression models are neat".split(" ")
    ).map(Tuple1.apply)).toDF("text")

    // Learn a mapping from words to Vectors.
    val word2Vec = new Word2Vec()
      .setInputCol("text")
      .setOutputCol("result")
      .setVectorSize(3)
      .setMinCount(0)
    val model = word2Vec.fit(documentDF)

    val result = model.transform(documentDF)
    result.collect().foreach { case Row(text: Seq[_], features: Vector) =>
      println(s"Text: [${text.mkString(", ")}] => \nVector: $features\n") }
    // $example off$

    spark.stop()
  }
}
