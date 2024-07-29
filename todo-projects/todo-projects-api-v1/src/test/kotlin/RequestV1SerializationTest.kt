

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import ru.otus.todo.api.v1.models.TaskCreateObject
import ru.otus.todo.api.v1.models.TaskCreateRequest
import ru.otus.todo.api.v1.models.TaskStatus
import ru.otus.todoprojects.api.v1.apiV1RequestDeserialize
import ru.otus.todoprojects.api.v1.apiV1RequestSerialize

class RequestV1SerializationTest {
    private val request = TaskCreateRequest(
        requestType = "create",
        task = TaskCreateObject(
            title = "Task title",
            description = "Task description",
            status = TaskStatus.IN_PROGRESS
        )
    )
    @Test
    fun serialize() {
        val json = apiV1RequestSerialize(request)

        assertTrue(json.contains(Regex("\"title\":\\s*\"Task title\"")))
        assertTrue(json.contains(Regex("\"description\":\\s*\"Task description\"")))
        assertTrue(json.contains(Regex("\"status\":\\s*\"in_progress\"")))
        assertTrue(json.contains(Regex("\"requestType\":\\s*\"create\"")))
    }

    @Test
    fun deserialize() {
        val json = apiV1RequestSerialize(request)
        val obj = apiV1RequestDeserialize<TaskCreateRequest>(json)

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"task": null, "requestType": "create"}
        """.trimIndent()
        val obj = apiV1RequestDeserialize<TaskCreateRequest>(jsonString)

        assertEquals("create", obj.requestType)
        assertEquals(null, obj.task)
    }
}
