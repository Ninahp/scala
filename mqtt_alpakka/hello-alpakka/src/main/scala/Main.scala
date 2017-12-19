object Main extends App {
  println("Hello, World!")
  val connectionSettings = MqttConnectionSettings(
    "tcp://sungura1-angani-host-ke.africastalking.com:1882",
    "alpakka-client",
    new MemoryPersistence
  )
  val topic1 = "alpakka/client/topic1"
  val topic2 = "alpakka/client/topic2"
  val bufferSize = 8
  val settings = MqttSourceSettings(
    conncetionSettings.withClientId("alpakka-client-1"),
    conncetionSettings.withAuth("alpakka","alpakka"),
    Map(topic1 -> MqttQos.AtleastOnce, topic2 -> MqttQos.AtleastOnce)
  )
  val mqttSource = mqttSource(settings, bufferSize)
  val (subscriptionFuture, result) = mqttSource
  .map(m => s"${m.topic}_${m.payload.utf8String}")
  .take(messaGeCount * 2)
  .toMat(Sink.seq)(Keep.both)
  .run()
  Source(messages).runWith(MqttSink(connectionSettings.withClientId("alpakka/sink"), MqttQoS.AtLeastOnce))
  
}