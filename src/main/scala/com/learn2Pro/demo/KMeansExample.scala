package com.learn2Pro.demo

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.linalg.Vectors

/**
  * Created with IntelliJ IDEA.
  * User: tang
  * Date: 2017/1/19
  * Time: 15:20
  * To change this template use File | Settings | File Templates.
  */
object KMeansExample {

  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("KMeansExample")
    val sc = new SparkContext(conf)

    // $example on$
    // Load and parse the data
    val data = sc.textFile("kmeans_data")
    val parsedData = data.map(s => Vectors.dense(s.split(' ').map(_.toDouble))).cache()

    // Cluster the data into two classes using KMeans
    val Array(numClus, numIter, path) = args
    val numClusters = numClus.toInt
    val numIterations = numIter.toInt
    val clusters = KMeans.train(parsedData, numClusters, numIterations)
    var clusterIndex: Int = 0
    // Evaluate clustering by computing Within Set Sum of Squared Errors
    clusters.clusterCenters.foreach(x => {
      println("Center Point of Cluster" + clusterIndex + ":")
      println(x)
      clusterIndex += 1
    })
    parsedData.collect().foreach(line => {
      val predictedIndex: Int = clusters.predict(line)
      println("The data " + line.toString + " belongs to Cluster " +
        predictedIndex)
    })
    val WSSSE = clusters.computeCost(parsedData)
    println("Cluster Within Set Sum of Squared Errors = " + WSSSE)

    // Save and load model
    clusters.predict(parsedData)
    clusters.save(sc, path + "KMeansModel")
    val sameModel = KMeansModel.load(sc, path + "KMeansModel")
    // $example off$

    sc.stop()
  }
}
