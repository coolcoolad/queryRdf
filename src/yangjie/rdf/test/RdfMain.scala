package yangjie.rdf.test

import scala.xml._
import org.apache.spark.{SparkConf,SparkContext}
import org.apache.spark.rdd.RDD

object RdfMain { 
  
  def parseXml(fileContent : RDD[(String,String)]): Seq[(String,String)] = {
    val xml = XML.load(fileContent)
    val entityNode = (xml\\"_").filter(x=>x.prefix=="ub" && x.attribute("http://www.w3.org/1999/02/22-rdf-syntax-ns#","about")!=None)
    entityNode.map(x=>(x.attribute("http://www.w3.org/1999/02/22-rdf-syntax-ns#","about"),x.label))
  }
  
  def main(args:Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("rdf test")
    val sc = new SparkContext(conf)
    
    val files = sc.wholeTextFiles("owl/*.owl")
    val entityKv = files.flatMap(parseXml)
  }

}