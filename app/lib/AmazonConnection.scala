package lib

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.services.ec2.AmazonEC2Client
import com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancingClient
import com.amazonaws.services.autoscaling.AmazonAutoScalingClient
import com.amazonaws.services.s3.AmazonS3Client
import java.io.File
import com.amazonaws.services.s3.model.{PutObjectRequest, ObjectMetadata}
import com.amazonaws.services.s3.model.CannedAccessControlList._
import play.api.libs.iteratee.PushEnumerator
import com.amazonaws.ClientConfiguration

class AmazonConnection(credentials: AWSCredentials, clientConfig: ClientConfiguration) {
  val ec2 = new AmazonEC2Client(credentials, clientConfig)
  val elb = new AmazonElasticLoadBalancingClient(credentials, clientConfig)
  val autoscaling = new AmazonAutoScalingClient(credentials, clientConfig)
  val s3 = new AmazonS3Client(credentials, clientConfig)

  ec2.setEndpoint("ec2.eu-west-1.amazonaws.com")
  elb.setEndpoint("elasticloadbalancing.eu-west-1.amazonaws.com")
  autoscaling.setEndpoint("autoscaling.eu-west-1.amazonaws.com")
}
