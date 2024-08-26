package ru.otus.todo.app.journal

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import ru.otus.todo.app.journal.model.TaskMessage
import java.io.FileWriter

private const val csvFilePath = "tasks.csv"

object MessageProcessor {
    suspend fun processMessage(message: String) {
        val taskMessage = Json.decodeFromString<TaskMessage>(message)
        saveToCsv(taskMessage)
    }

    private suspend fun saveToCsv(taskMessage: TaskMessage) = withContext(Dispatchers.IO) {
        FileWriter(csvFilePath, true).use { writer ->
            val csvPrinter = CSVPrinter(writer, CSVFormat.DEFAULT)
            csvPrinter.printRecord(
                taskMessage.date.toString(),
                taskMessage.operation,
                taskMessage.title,
                taskMessage.description,
                taskMessage.status
            )
            csvPrinter.flush()
        }
    }
}
