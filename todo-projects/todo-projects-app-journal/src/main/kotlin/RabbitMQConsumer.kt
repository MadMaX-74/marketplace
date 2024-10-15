package ru.otus.todo.app.journal

import com.rabbitmq.client.ConnectionFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets

object RabbitMQConsumer {
    private const val r_host = "localhost"
    private const val queueName = "tasks_queue"
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun start() {
        val factory = ConnectionFactory().apply {
            host = r_host
        }

        scope.launch {
            val connection = factory.newConnection()
            val channel = connection.createChannel()
            channel.queueDeclare(queueName, false, false, false, null)
            println("Waiting for messages...")

            val deliverCallback: (String, com.rabbitmq.client.Delivery) -> Unit = { _: String, delivery: com.rabbitmq.client.Delivery ->
                val message = String(delivery.body, StandardCharsets.UTF_8)
                println("Received message: $message")

                scope.launch {
                    try {
                        MessageProcessor.processMessage(message)
                    } catch (e: Exception) {
                        println("Error processing message: ${e.message}")
                    }
                }
            }

            channel.basicConsume(queueName, true, deliverCallback) { _ -> }
        }
    }
}
