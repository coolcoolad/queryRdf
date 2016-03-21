import scala.xml._
import org.apache.spark.{SparkConf,SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.graphx._

object RdfMain {
  def main() {
    val conf = new SparkConf()
    conf.setAppName("rdf test")
    val sc = new SparkContext(conf)
    
    val files = sc.wholeTextFiles("owl/*.owl")
    val entityKv = files.flatMap(parseXml)
    
    
  }
  
  def parseXml(fileContent) (String,String) {
    val xml = XML.load(fileContent)
    val entityNode = (xml\\"_").filter(x=>x.prefix=="ub" && x.attribute("http://www.w3.org/1999/02/22-rdf-syntax-ns#","about")!=None)
    entityNode.map(x=>(x.attribute("http://www.w3.org/1999/02/22-rdf-syntax-ns#","about"),x.label))
  }
}