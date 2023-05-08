package com.example.routineappas

import kotlin.random.Random

data class ExerciseModel(
    var id: Int = getAutoId(),
    var r_name: String = "",
    var e_name: String = "e_name",
    var repNum: String = "reps",
    var setNum: Int = 0,
    var weight: String = "weight"
){
  companion object{
      fun getAutoId():Int{
          val random = Random
          return random.nextInt(100)
      }
  }

}