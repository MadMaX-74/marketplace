package ru.otus.todo.app.todo.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

fun LocalDateTime.toFormattedString(): String = this.format(formatter)

data class TaskMessage(
    val date: String,
    val operation: String,
    val title: String,
    val description: String,
    val status: String
)

@Service
class RabbitMQSender(private val rabbitTemplate: RabbitTemplate) {
    fun sendTaskMessage(taskMessage: TaskMessage) {
        val message = jacksonObjectMapper().writeValueAsString(taskMessage)
        rabbitTemplate.convertAndSend("tasks_queue", message)
        println("Sent message to RabbitMQ: $message")
    }
}
