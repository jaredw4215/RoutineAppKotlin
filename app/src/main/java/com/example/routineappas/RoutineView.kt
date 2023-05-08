package com.example.routineappas

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout

class RoutineView : ConstraintLayout {

    lateinit var rootLayout : ConstraintLayout
    lateinit var name : TextView
    lateinit var card : CardView
    lateinit var dots : ImageView

    constructor(context: Context) : super(context) {
        initRoutineView(context)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context,attrs) {
        initRoutineView(context)
    }

    constructor(context: Context, attrs: AttributeSet?,defStyleAttr: Int) : super(context,attrs,defStyleAttr){
        initRoutineView(context)
    }

    private fun initRoutineView(context: Context){
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        LayoutInflater.from(context).inflate(R.layout.routine_view,this,true)
        rootLayout = findViewById(R.id.constraintLayout)
        name = findViewById(R.id.evName)
        card = findViewById(R.id.evCard)
        dots = findViewById(R.id.evDots)
    }

}



