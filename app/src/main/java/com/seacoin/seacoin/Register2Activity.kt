package com.seacoin.seacoin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.seacoin.seacoin.util.NetworkHelper
import kotlinx.android.synthetic.main.activity_register2.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register2Activity : AppCompatActivity() {
    lateinit var pref: SharedPreferences
    lateinit var pref_edit: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)
        pref = getSharedPreferences("user", Context.MODE_PRIVATE)
        pref_edit = pref.edit()
        registerBtn.setOnClickListener {
            val id = intent.getStringExtra("id")
            val password = intent.getStringExtra("password")
            val birth = intent.getStringExtra("birth")
            val name = intent.getStringExtra("name")
            val cardNum = cardNumEdit.text.toString()
            val cardPassword = cardPassEdit.text.toString()
            val cardExpiry = yearEdit.text.toString() + monthEdit.text.toString()
//            startActivity(Intent(this@Register2Activity, MainActivity::class.java))
            NetworkHelper.networkInstance.register(id, password, name, birth, cardNum, cardPassword, cardExpiry)
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                            Toast.makeText(this@Register2Activity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                            val json = JSONObject(response?.body()?.string())
                            val result = json.getInt("result")
                            if (result == 1) {
                                finishAffinity()
                                startActivity(Intent(this@Register2Activity, LoginActivity::class.java))
                                pref_edit.putString("id", id)
                                pref_edit.putString("birth", birth)
                                pref_edit.putString("name", name)
                                pref_edit.putString("cardNum", cardNum)
                                pref_edit.putString("cardPassword", cardPassword)
                                pref_edit.putString("cardExpiry", cardExpiry)
                                pref_edit.commit()
                            } else Toast.makeText(this@Register2Activity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        }

                    })
        }
    }
}
