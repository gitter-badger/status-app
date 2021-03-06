package model

import org.specs2.mutable._
import com.amazonaws.services.autoscaling.model.{TagDescription, AutoScalingGroup}
import collection.convert.wrapAll._
import org.joda.time.DateTime
import scala.util.Random

class EstateTest extends Specification {
  "Estate stages" should {
    "be sorted with PROD first" in {
      val estate = PopulatedEstate(Seq(
        asg("TEST"),
        asg("PROD"),
        asg("CODE"),
        asg("QA")
      ), Seq(), DateTime.now)
      estate.stageNames should contain(exactly("PROD", "CODE", "QA", "TEST"))
    }
  }

  "Should have a list of stages, each with all relevant stacks" in {
    val estate = PopulatedEstate(Seq(
      asg("PROD", Some("a")),
      asg("PROD", Some("a")),
      asg("PROD", Some("b")),
      asg("PROD", None)
    ), Seq(), DateTime.now)
    estate("PROD").stacks should haveLength(3)
  }

  "Stacks should be in alphabetic order" in {
    val estate = PopulatedEstate(Seq(
      asg("PROD", Some("a")),
      asg("PROD", Some("a")),
      asg("PROD", Some("b")),
      asg("PROD", None)
    ), Seq(), DateTime.now)
    estate("PROD").stacks.map(_.name).toSeq should be_== (Seq("a", "b", "unknown"))
  }

  def asg(stage: String, stack: Option[String] = None) = ASG(
    s"name-${Random.alphanumeric}", Some(stage), None, stack, None, Nil, Nil, Nil, Nil, None, None
  )
}
