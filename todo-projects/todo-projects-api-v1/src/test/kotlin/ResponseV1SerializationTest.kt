import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import com.fasterxml.jackson.module.kotlin.readValue
import ru.otus.todoprojects.api.v1.apiV1Mapper
import ru.otus.todoprojects.api.v1.models.*

class ResponseV1SerializationTest {

    private val response = TaskCreateResponse(
        responseType = "create",
        result = ResponseResult.SUCCESS,
        errors = listOf(),
        task = TaskResponseObject(
            id = "some-id",
            title = "task title",
            description = "task description",
            status = TaskStatus.IN_PROGRESS,
            createdDate = "2024-07-28T12:00:00Z",
            completedDate = null
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertTrue(json.contains(Regex("\"id\":\\s*\"some-id\"")))
        assertTrue(json.contains(Regex("\"title\":\\s*\"task title\"")))
        assertTrue(json.contains(Regex("\"description\":\\s*\"task description\"")))
        assertTrue(json.contains(Regex("\"status\":\\s*\"in_progress\"")))
        assertTrue(json.contains(Regex("\"createdDate\":\\s*\"2024-07-28T12:00:00Z\"")))
        assertTrue(json.contains(Regex("\"responseType\":\\s*\"create\"")))
        assertTrue(json.contains(Regex("\"result\":\\s*\"success\"")))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue<IResponse>(json) as TaskCreateResponse

        assertEquals(response, obj)
    }
}
