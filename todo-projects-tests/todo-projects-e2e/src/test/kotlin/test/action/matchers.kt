package ru.otus.todo.tests.e2e.test.action

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult

val beValidId = Matcher<String?> {
    MatcherResult(
        it != null,
        { "id should not be null" },
        { "id should be null" },
    )
}


