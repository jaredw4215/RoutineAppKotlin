package com.example.routineappas

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast


class SecondActivity : AppCompatActivity() {
    lateinit var title : TextView
    private lateinit var addButton: ImageButton
    lateinit var mainLinearLayout: LinearLayout
    private lateinit var sqLiteManager: SQLiteManager
    private var progessCnt : Int = 0
    private var boxNumber : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        initMainView()
    }

    private fun addEditPopup(view: ExerciseView, eModel: ExerciseModel){
        val popup = Dialog(this)
        popup.requestWindowFeature(Window.FEATURE_NO_TITLE)
        popup.setContentView(R.layout.exercise_popup_view)
        popup.setCancelable(true)
        popup.window?.setBackgroundDrawableResource(android.R.color.transparent)
        popup.show()
        val title = popup.findViewById<TextView>(R.id.ePopupTitle)
        val cancelBtn = popup.findViewById<Button>(R.id.ePopupCancel)
        val submitBtn = popup.findViewById<Button>(R.id.ePopupSubmit)
        val nameInput = popup.findViewById<TextView>(R.id.ePopupName)
        val setInput = popup.findViewById<TextView>(R.id.ePopupSetNum)
        val repInput = popup.findViewById<TextView>(R.id.ePopupRepNum)
        val weightInput = popup.findViewById<TextView>(R.id.ePopupWeight)
        title.text = getString(R.string.update_your_exercise)
        nameInput.text = eModel.e_name
        setInput.text = eModel.setNum.toString()
        repInput.text = eModel.repNum
        weightInput.text = eModel.weight

        cancelBtn.setOnClickListener {
            popup.dismiss()
        }
        submitBtn.setOnClickListener {
            if (nameInput.text.isEmpty() || setInput.text.isEmpty() || repInput.text.isEmpty() || weightInput.text.isEmpty()){
                Toast.makeText(this,"One of the fields is empty",Toast.LENGTH_SHORT).show()
            }
            else if (setInput.text.toString().toInt() > 4){
                Toast.makeText(this,"Cannot add more than 4 sets",Toast.LENGTH_SHORT).show()
            }
            else{
                view.setHolder.removeAllViews()
                boxNumber -= eModel.setNum
                eModel.e_name = nameInput.text.toString()
                eModel.setNum = setInput.text.toString().toInt()
                eModel.repNum = repInput.text.toString()
                eModel.weight = weightInput.text.toString()
                sqLiteManager.updateExercise(eModel)
                view.name.text = eModel.e_name
                view.reps.text = eModel.repNum
                view.weight.text = eModel.weight
                for (x in 0 until eModel.setNum){
                    boxNumber++
                    addCheckbox(view)
                }
//                Toast.makeText(this,"$boxNumber",Toast.LENGTH_SHORT).show()
                popup.dismiss()
            }
        }
    }

    private fun addCheckbox(view: ExerciseView){
        val checkbox = CheckBox(this)
        checkbox.setOnCheckedChangeListener { _, b ->
            if (b){
                progessCnt++
                if (progessCnt == boxNumber){Toast.makeText(this,"Complete!!!",Toast.LENGTH_SHORT).show()}

            }
            else {
                if(progessCnt > 0) {progessCnt--}
            }
        }
        view.setHolder.addView(checkbox)
    }

    private fun addPopup(r_name: String){
        val popup = Dialog(this)
        popup.requestWindowFeature(Window.FEATURE_NO_TITLE)
        popup.setContentView(R.layout.exercise_popup_view)
        popup.setCancelable(true)
        popup.window?.setBackgroundDrawableResource(android.R.color.transparent)
        popup.show()
        val cancelBtn = popup.findViewById<Button>(R.id.ePopupCancel)
        val submitBtn = popup.findViewById<Button>(R.id.ePopupSubmit)
        val nameInput = popup.findViewById<TextView>(R.id.ePopupName).text
        val setInput = popup.findViewById<TextView>(R.id.ePopupSetNum).text
        val repInput = popup.findViewById<TextView>(R.id.ePopupRepNum).text
        val weightInput = popup.findViewById<TextView>(R.id.ePopupWeight).text

        cancelBtn.setOnClickListener {
            popup.dismiss()
        }
        submitBtn.setOnClickListener {
            if (nameInput.isEmpty() || setInput.isEmpty() || repInput.isEmpty() || weightInput.isEmpty()){
                Toast.makeText(this,"One of the fields is empty",Toast.LENGTH_SHORT).show()
            }
            else if (setInput.toString().toInt() > 4){
                Toast.makeText(this,"Cannot add more than 4 sets",Toast.LENGTH_SHORT).show()
            }
            else{
                val newExerciseModel = ExerciseModel(r_name = r_name, e_name = nameInput.toString(),
                    repNum = repInput.toString(), setNum = setInput.toString().toInt(), weight = weightInput.toString())
//                Toast.makeText(this,r_name,Toast.LENGTH_SHORT).show()
                sqLiteManager.insertExercise(newExerciseModel)
                addExerciseView(newExerciseModel)
                popup.dismiss()
            }

        }
    }
    private fun addExerciseView(eModel: ExerciseModel) {
        val newExerciseView = ExerciseView(this)
        newExerciseView.name.text = eModel.e_name
        newExerciseView.reps.text = eModel.repNum
        newExerciseView.weight.text = eModel.weight
        newExerciseView.weightType.text = getString(R.string.lb)
        for (x in 0 until eModel.setNum){
            boxNumber++
            addCheckbox(newExerciseView)
        }
        newExerciseView.dots.setOnClickListener{
            val dropDownMenu = PopupMenu(this,newExerciseView.dots)
            initMenu(dropDownMenu,newExerciseView,eModel)
            dropDownMenu.show()
        }
//        Toast.makeText(this,"$boxNumber",Toast.LENGTH_SHORT).show()
        mainLinearLayout.addView(newExerciseView)
    }

    private fun initMenu (menu: PopupMenu, view: ExerciseView, eModel: ExerciseModel){
        menu.inflate(R.menu.drop_down_menu)
        menu.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.item1 -> {
                    addEditPopup(view,eModel)
                    true
                }
                R.id.item2 -> {
                    deleteExercise(view,eModel)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
    private fun deleteExercise(view: ExerciseView, eModel: ExerciseModel){
        sqLiteManager.deleteExercise(eModel.id)
        mainLinearLayout.removeView(view)
    }

    private fun initMainView(){
        addButton = findViewById(R.id.imageButton)
        mainLinearLayout = findViewById(R.id.act2LinearLayout)

        val name = intent.getStringExtra("Title")
        title = findViewById(R.id.act2Title)
        title.text = name

        addButton.setOnClickListener{
            addPopup(title.text.toString())
        }
        sqLiteManager = SQLiteManager(this)

        val exerciseList = sqLiteManager.getAllExercises()
//        Toast.makeText(this,"${exerciseList.size}",Toast.LENGTH_SHORT).show()
        for (x in 0 until exerciseList.size){
            if (exerciseList[x].r_name == name){
                addExerciseView(exerciseList[x])
            }
        }
    }
}