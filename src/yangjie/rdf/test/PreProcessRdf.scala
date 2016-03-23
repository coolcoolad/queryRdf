package yangjie.rdf.test

import scala.xml._
import java.io.PrintWriter

object PreProcessRdf {
  def main(args:Array[String]): Unit = {
    val filePrefix = "owl/University9_"
    val outputFile = new PrintWriter("owl.out")
    var count = 0L;
    for (i <- 0 until 22) {
      val xml = XML.loadFile(s"${filePrefix}${i}.owl")
      val entityNode = (xml\\"_").filter(x=>x.prefix=="ub" && x.attribute("http://www.w3.org/1999/02/22-rdf-syntax-ns#","about")!=None)
      val entityKv = entityNode.map(x=>(x.attribute("http://www.w3.org/1999/02/22-rdf-syntax-ns#","about"),x.label))
      for (kv <- entityKv) {
        val line = s"$count,${kv._1},${kv._2}"
        outputFile.println(line)
        count += 1L
      }
    }
    outputFile.close()
  }
}