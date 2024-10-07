package ru.otus.todo.repo.postgresql

actual fun getEnv(name: String): String? = System.getenv(name)
