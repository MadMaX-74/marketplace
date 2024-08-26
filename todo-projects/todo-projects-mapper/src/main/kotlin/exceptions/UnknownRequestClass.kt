package ru.otus.todo.mapper.exceptions

class UnknownRequestClass(clazz: Class<*>) : RuntimeException("Class $clazz cannot be mapped to TodoContext")
