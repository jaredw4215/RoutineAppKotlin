package com.example.routineappas

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout

class ExerciseView : CardView{

    lateinit var root : CardView
    lateinit var name: TextView
    lateinit var dots: ImageButton
    lateinit var setHolder: LinearLayout
    lateinit var reps: TextView
    lateinit var weight: TextView
    lateinit var weightType: TextView

    constructor(context: Context) : super(context) {
        initRoutineView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context,attrs) {
        initRoutineView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context,attrs,defStyleAttr){
        initRoutineView(context)
    }

    private fun initRoutineView(context: Context){
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        LayoutInflater.from(context).inflate(R.layout.exercise_view,this,true)
        root = findViewById(R.id.ev_Root)
        name = findViewById(R.id.ev_Name)
        dots = findViewById(R.id.ev_Dots)
        setHolder = findViewById(R.id.ev_setHolder)
        reps = findViewById(R.id.ev_Reps)
        weight = findViewById(R.id.ev_Weight)
        weightType = findViewById(R.id.ev_WeightType)

    }
}