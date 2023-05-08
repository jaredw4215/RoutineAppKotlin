package com.example.routineappas

import kotlin.random.Random

data class RoutineModel(
    var id: Int = getAutoId(),
    var r_name: String = "",
){
    companion object{
        fun getAutoId():Int{
            val random = Random
            return random.nextInt(100)
        }
    }

}
