package ru.otus.todo.app.journal

import com.rabbitmq.client.ConnectionFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.nio.charset.StandardCharsets

object RabbitMQConsumer {
    val r_host = "localhost"
    val queueName = "tasks_queue"
    fun start() {
        val factory = ConnectionFactory().apply {
            host = r_host
        }
        val connection = factory.newConnection()
        val channel = connection.createChannel()

        channel.queueDeclare(queueName, false, false, false, null)
        println("Waiting for messages...")

        val deliverCallback: (String, com.rabbitmq.client.Delivery) -> Unit = { _: String, delivery: com.rabbitmq.client.Delivery ->
            val message = String(delivery.body, StandardCharsets.UTF_8)
            println("Received message: $message")
            runBlocking {
                launch {
                    MessageProcessor.processMessage(message)
                }
            }
        }

        channel.basicConsume(queueName, true, deliverCallback) { _ -> }
    }
}
