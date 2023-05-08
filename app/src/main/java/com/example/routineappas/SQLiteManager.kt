package com.example.routineappas

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class SQLiteManager(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "Routines.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_EXERCISES = "Exercises"
        private const val TABLE_ROUTINES = "Routines"
        private const val ID = "id"
        private const val R_NAME = "r_name"
        private const val E_NAME = "e_name"
        private const val REP_NUM = "repNum"
        private const val SET_NUM = "setNum"
        private const val WEIGHT = "weight"

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val createTblRoutine = ("CREATE TABLE " + TABLE_ROUTINES + " (" +
                ID + " INTEGER PRIMARY KEY," +
                R_NAME + " TEXT" + " )")
        val createTblExercises = ("CREATE TABLE " + TABLE_EXERCISES + " (" +
                ID + " INTEGER PRIMARY KEY," +
                R_NAME + " TEXT," +
                E_NAME + " TEXT," +
                REP_NUM + " TEXT," +
                SET_NUM + " INTEGER," +
                WEIGHT + " TEXT" + " )")
        p0?.execSQL(createTblRoutine)
        p0?.execSQL(createTblExercises)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS $TABLE_ROUTINES")
        p0.execSQL("DROP TABLE IF EXISTS $TABLE_EXERCISES")
        onCreate(p0)
    }
    fun insertRoutine(rtn: RoutineModel): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID,rtn.id)
        contentValues.put(R_NAME, rtn.r_name)
        val success = db.insert(TABLE_ROUTINES,null,contentValues)
        db.close()
        return success
    }
    fun updateRoutine(rtn: RoutineModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID,rtn.id)
        contentValues.put(R_NAME,rtn.r_name)
        val success = db.update(TABLE_ROUTINES,contentValues,"id="+ rtn.id,null)
        db.close()
        return success
    }
    fun deleteRoutine(col_id: Int): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID,col_id)
        val success = db.delete(TABLE_ROUTINES,"id=$col_id",null)
        db.close()
        return success
    }

    fun insertExercise(exrc: ExerciseModel): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID,exrc.id)
        contentValues.put(R_NAME, exrc.r_name)
        contentValues.put(E_NAME,exrc.e_name)
        contentValues.put(REP_NUM,exrc.repNum)
        contentValues.put(SET_NUM,exrc.setNum)
        contentValues.put(WEIGHT,exrc.weight)
        val success = db.insert(TABLE_EXERCISES,null,contentValues)
        db.close()
        return success
    }

    fun updateExercise(exrc: ExerciseModel): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID,exrc.id)
        contentValues.put(R_NAME, exrc.r_name)
        contentValues.put(E_NAME,exrc.e_name)
        contentValues.put(REP_NUM,exrc.repNum)
        contentValues.put(SET_NUM,exrc.setNum)
        contentValues.put(WEIGHT,exrc.weight)
        val success = db.update(TABLE_EXERCISES,contentValues,"id="+ exrc.id,null)
        db.close()
        return success
    }
    fun deleteExercise(col_id: Int): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID,col_id)
        val success = db.delete(TABLE_EXERCISES,"id=$col_id",null)
        db.close()
        return success
    }

    fun getAllRoutines():ArrayList<RoutineModel>{
        val rtnList: ArrayList<RoutineModel> = ArrayList()
        val cursor: Cursor?
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_ROUTINES"
        try {
            cursor = db.rawQuery(selectQuery,null)
        }catch (e: java.lang.Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var r_name: String

        if (cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                r_name = cursor.getString(cursor.getColumnIndexOrThrow("r_name"))

                val rtn = RoutineModel(id = id,r_name = r_name)
                rtnList.add(rtn)
            }while (cursor.moveToNext())
        }
        return rtnList
    }

    fun getAllExercises():ArrayList<ExerciseModel>{
        val exrcList: ArrayList<ExerciseModel> = ArrayList()
        val cursor: Cursor?
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_EXERCISES"
        try {
            cursor = db.rawQuery(selectQuery,null)
        }catch (e: java.lang.Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var r_name: String
        var e_name: String
        var repNum: String
        var setNum: Int
        var weight: String

        if (cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                r_name = cursor.getString(cursor.getColumnIndexOrThrow("r_name"))
                e_name = cursor.getString(cursor.getColumnIndexOrThrow("e_name"))
                repNum = cursor.getString(cursor.getColumnIndexOrThrow("repNum"))
                setNum = cursor.getInt(cursor.getColumnIndexOrThrow("setNum"))
                weight = cursor.getString(cursor.getColumnIndexOrThrow("weight"))

                val exrc = ExerciseModel(id = id,r_name = r_name,e_name = e_name, repNum = repNum, setNum = setNum, weight = weight)
                exrcList.add(exrc)
            }while (cursor.moveToNext())
        }
        return exrcList
    }
}