/**
 * Created by radu on 29.01.2015.
 */
import collection.mutable.Stack
import org.scalatest._

class TestHelloWorld extends FlatSpec with Matchers {

  "Hello world" should "be obtained from Hello and world" in {
    ("Hello" + " " + "world")  should be ("Hello world")
  }
}
