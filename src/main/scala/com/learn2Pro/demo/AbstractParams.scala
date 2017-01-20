package com.learn2Pro.demo

import scala.reflect.runtime.universe._
/**
  * Created with IntelliJ IDEA.
  * User: tang
  * Date: 2017/1/20
  * Time: 15:05
  * To change this template use File | Settings | File Templates.
  */
abstract class AbstractParams[T: TypeTag] {

  private def tag: TypeTag[T] = typeTag[T]

  /**
    * Finds all case class fields in concrete class instance, and outputs them in JSON-style format:
    * {
    *   [field name]:\t[field value]\n
    *   [field name]:\t[field value]\n
    *   ...
    * }
    */
  override def toString: String = {
    val tpe = tag.tpe
    val allAccessors = tpe.declarations.collect {
      case m: MethodSymbol if m.isCaseAccessor => m
    }
    val mirror = runtimeMirror(getClass.getClassLoader)
    val instanceMirror = mirror.reflect(this)
    allAccessors.map { f =>
      val paramName = f.name.toString
      val fieldMirror = instanceMirror.reflectField(f)
      val paramValue = fieldMirror.get
      s"  $paramName:\t$paramValue"
    }.mkString("{\n", ",\n", "\n}")
  }
}
