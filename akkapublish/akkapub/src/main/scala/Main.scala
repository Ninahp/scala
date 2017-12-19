import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import scala.util.Random

object Main extends App {
  println("Hello, World!")
  val brokerUrl = "tcp://sungura1-angani-ke-host.africastalking.com:1882"
  val topic = "akka/pub/test"
  val username = "akka"
  val authKey = ("devicemanager")
  val pass = authKey.toArray

  // Auto-Generate Client
   val client :  MqttClient = null 

  val persistence = new MqttDefaultFilePersistence("/tmp")
  val opts = new MqttConnectOptions()
  opts.setUserName(username)
  opts.setPassword(pass)
  try{
    val client = new MqttClient(brokerUrl, MqttClient.generateClientId, persistence)
    client.connect(opts)
    val toPic = client.getTopic(topic)

    while(true)
    {
       val payload = Random.nextInt(10) + 640
      val message = new MqttMessage(s"$payload".getBytes)
      toPic.publish(message)
      println("Publishing; Topic: %s, Message: %s".format(toPic.getName, message))
      Thread.sleep(800)
    }
  }
  catch{
     case e: MqttException => println("Exception Caught: " + e)
  }
      finally {
      client.disconnect()
    }

}