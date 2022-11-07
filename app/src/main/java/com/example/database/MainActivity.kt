package com.example.database

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.database.database.DatabaseHelper
import com.example.database.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val context=this
        var db=DatabaseHelper(context)

        binding.save.setOnClickListener {
            val name=binding.namesurname.text.toString()
            val age=binding.age.text.toString()

            if(name.isNotEmpty() && age.isNotEmpty() ){
                val newUser=User(name,age.toInt())
                db.insertData(newUser)
            }
            else{ Toast.makeText(context,"Please Fill The Empty Places",Toast.LENGTH_SHORT).show() }
        }

        binding.read.setOnClickListener {
            var data=db.readData()

           binding.tvResult.text=""
            for(it in data){
                binding.tvResult.append(it.id.toString() +" "+it.name+" "+it.age+"\n")
            }
        }

        binding.update.setOnClickListener {
            db.updateData()
            binding.read.performClick()
        }
        binding.delete.setOnClickListener {
            db.delete()
        }


    }
}