package com.learn2Pro.demo

import scala.math.random

/**
 * Created with IntelliJ IDEA.
 * User: tang
 * Date: 17-1-9
 * Time: 下午3:57
 * To change this template use File | Settings | File Templates.
 */
object LocalPi {
  def main(args: Array[String]) {
    var count = 0
    for (i <- 1 to 100000) {
      val x = random * 2 - 1
      val y = random * 2 - 1
      if (x * x + y * y <= 1) count += 1
    }
    println("Pi is roughly " + 4 * count / 100000.0)
  }
}

