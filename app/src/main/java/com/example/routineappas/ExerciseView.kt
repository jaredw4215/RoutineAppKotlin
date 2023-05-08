package com.example.routineappas

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout

class ExerciseView : ConstraintLayout{

    lateinit var rootLayout : ConstraintLayout
    lateinit var card: CardView
    lateinit var name: TextView
    lateinit var dots: ImageButton
    lateinit var setHolder: LinearLayout
    lateinit var reps: TextView
    lateinit var weight: TextView
    lateinit var type: TextView

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
        rootLayout = findViewById(R.id.constraintLayout)
        card = findViewById(R.id.evCard)
        name = findViewById(R.id.evName)
        dots = findViewById(R.id.evDots)
        setHolder = findViewById(R.id.evSetHolder)
        reps = findViewById(R.id.evRepTV)
        weight = findViewById(R.id.evWeightTV)
        type = findViewById(R.id.weightType)

    }
}