package com.seacoin.seacoin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        toolbar.title = "회원가입"
        toolbar.setTitleTextColor(Color.BLACK)
        setSupportActionBar(toolbar)

        nextBtn.setOnClickListener {
            val intent = Intent(this@RegisterActivity
                    , Register2Activity::class.java).apply {
                putExtra("birth", birthEdit.text.toString())
                putExtra("name", nameEdit.text.toString())
                putExtra("id", idEdit.text.toString())
                putExtra("password", passwordEdit.text.toString())
            }
            startActivity(intent)
        }
    }
}
