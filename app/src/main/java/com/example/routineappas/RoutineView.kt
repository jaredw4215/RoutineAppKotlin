package com.example.routineappas

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView

class RoutineView : CardView {

    lateinit var root : CardView
    lateinit var name : TextView
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
        root = findViewById(R.id.rv_Root)
        name = findViewById(R.id.rv_Name)
        dots = findViewById(R.id.rv_Dots)
    }

}



