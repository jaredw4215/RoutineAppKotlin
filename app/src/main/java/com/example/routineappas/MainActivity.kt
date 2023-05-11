package com.example.routineappas

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var addRoutine : FloatingActionButton
    lateinit var mainLayout : ConstraintLayout
    lateinit var mainLinearLayout : LinearLayout
    lateinit var sqLiteManager: SQLiteManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMainView()
    }    private fun addEditPopup(view: RoutineView,rModel: RoutineModel){
        val popup = Dialog(this)
        popup.requestWindowFeature(Window.FEATURE_NO_TITLE)
        popup.setContentView(R.layout.routine_popup_view)
        popup.setCancelable(true)
        popup.window?.setBackgroundDrawableResource(android.R.color.transparent)
        popup.show()
        val title = popup.findViewById<TextView>(R.id.ePopupTitle)
        val cancelBtn = popup.findViewById<Button>(R.id.ePopupCancel)
        val submitBtn = popup.findViewById<Button>(R.id.ePopupSubmit)
        val inputText = popup.findViewById<TextView>(R.id.ePopupName)
        title.text = getString(R.string.update_your_routine)
        inputText.text = rModel.r_name
        cancelBtn.setOnClickListener {
            popup.dismiss()
        }
        submitBtn.setOnClickListener {
            val exerciseList = sqLiteManager.getAllExercises()
            for (x in 0 until exerciseList.size){
                if (exerciseList[x].r_name == rModel.r_name){
                    val exerciseItem = exerciseList[x]
                    exerciseItem.r_name = inputText.text.toString()
                    sqLiteManager.updateExercise(exerciseItem)
                }
            }
            rModel.r_name = inputText.text.toString()
            sqLiteManager.updateRoutine(rModel)
            view.name.text = inputText.text.toString()
            popup.dismiss()
        }
    }
    private fun addPopup(){
        val popup = Dialog(this)
        popup.requestWindowFeature(Window.FEATURE_NO_TITLE)
        popup.setContentView(R.layout.routine_popup_view)
        popup.setCancelable(true)
        popup.window?.setBackgroundDrawableResource(android.R.color.transparent)
        popup.show()
        val cancelBtn = popup.findViewById<Button>(R.id.ePopupCancel)
        val submitBtn = popup.findViewById<Button>(R.id.ePopupSubmit)
        val inputText = popup.findViewById<TextView>(R.id.ePopupName).text
        cancelBtn.setOnClickListener {
            popup.dismiss()
        }
        submitBtn.setOnClickListener {
            val newRoutineModel = RoutineModel(r_name = inputText.toString())
            sqLiteManager.insertRoutine(newRoutineModel)
            addRoutineView(newRoutineModel)
            popup.dismiss()
        }
    }
    private fun addRoutineView(rModel: RoutineModel) {
        val newRoutineView = RoutineView(this)
        val intent = Intent(this, SecondActivity::class.java)
        newRoutineView.name.text = rModel.r_name
        newRoutineView.card.setOnClickListener {
            intent.putExtra("Title",rModel.r_name)
            startActivity(intent)

        }
        newRoutineView.dots.setOnClickListener{
            val dropDownMenu = PopupMenu(this,newRoutineView.dots)
            initMenu(dropDownMenu,newRoutineView,rModel)
            dropDownMenu.show()
        }
        mainLinearLayout.addView(newRoutineView)
    }

    private fun initMenu (menu: PopupMenu,view: RoutineView,rModel: RoutineModel){
        menu.inflate(R.menu.drop_down_menu)
        menu.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.item1 -> {
                    addEditPopup(view,rModel)
                    true
                }
                R.id.item2 -> {
                    deleteRoutine(view,rModel)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun deleteRoutine(view: RoutineView, rModel: RoutineModel){
        val exerciseList = sqLiteManager.getAllExercises()
        for (x in 0 until exerciseList.size){
            if (exerciseList[x].r_name == rModel.r_name){
                sqLiteManager.deleteExercise(exerciseList[x].id)
            }
        }
        sqLiteManager.deleteRoutine(rModel.id)
        mainLinearLayout.removeView(view)
    }

    private fun initMainView(){
        mainLinearLayout = findViewById(R.id.mainLinearLayout)
        addRoutine = findViewById(R.id.fabAddRoutine)
        mainLayout = findViewById(R.id.conMainLayout)
        addRoutine.setOnClickListener {
            addPopup()
        }
        sqLiteManager = SQLiteManager(this)
        val routineList = sqLiteManager.getAllRoutines()

        for (x in 0 until routineList.size){
            addRoutineView(routineList[x])
        }
    }

}