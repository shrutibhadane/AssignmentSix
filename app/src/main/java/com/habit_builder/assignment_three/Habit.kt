package com.habit_builder.assignment_three

data class Habit(
    val id: Int,
    val name: String,
    val date: String,
    val streak: Int,
    var lastCompletedDate: String?
)
