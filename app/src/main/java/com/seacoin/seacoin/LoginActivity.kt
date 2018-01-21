package com.seacoin.seacoin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.seacoin.seacoin.util.NetworkHelper
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var pref: SharedPreferences
    lateinit var pref_edit: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        pref = getSharedPreferences("user", Context.MODE_PRIVATE)
        pref_edit = pref.edit()
        NetworkHelper(this)
        toolbar.title = "로그인"
        toolbar.setTitleTextColor(Color.BLACK)
        setSupportActionBar(toolbar)

        registerBtn.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        loginBtn.setOnClickListener {
            NetworkHelper.networkInstance.login(idEdit.text.toString(), passwordEdit.text.toString()).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                    val json = JSONObject(response?.body()?.string())
                    val result = json.getInt("result")
                    if (result == 1) {
                        pref_edit.putString("id", json.getJSONObject("user").getString("id"))
                        pref_edit.commit()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else Toast.makeText(this@LoginActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}
