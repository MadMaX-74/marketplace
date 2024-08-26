package ru.otus.todo.app.journal

import com.rabbitmq.client.ConnectionFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets

object RabbitMQConsumer {
    private const val r_host = "localhost"
    private const val queueName = "tasks_queue"

    @OptIn(DelicateCoroutinesApi::class)
    fun start() {
        val factory = ConnectionFactory().apply {
            host = r_host
        }

        GlobalScope.launch(Dispatchers.IO) {
            val connection = factory.newConnection()
            val channel = connection.createChannel()
            channel.queueDeclare(queueName, false, false, false, null)
            println("Waiting for messages...")

            val deliverCallback: (String, com.rabbitmq.client.Delivery) -> Unit = { _: String, delivery: com.rabbitmq.client.Delivery ->
                val message = String(delivery.body, StandardCharsets.UTF_8)
                println("Received message: $message")

                // Обрабатываем каждое сообщение в отдельной корутине, используя диспетчер для работы с I/O
                launch(Dispatchers.IO) {
                    MessageProcessor.processMessage(message)
                }
            }

            channel.basicConsume(queueName, true, deliverCallback) { _ -> }
        }
    }
}

