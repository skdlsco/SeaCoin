package com.seacoin.seacoin

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import com.seacoin.seacoin.util.NetworkHelper
import kotlinx.android.synthetic.main.dialog_credit.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by eka on 2018. 1. 21..
 */
class CreditDialog(context: Context, private val coin: String, var payCallback: PayCallback? = null, private var isBuy: Boolean) : Dialog(context) {

    private lateinit var pref: SharedPreferences
    private var isPaying = false
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_credit)
        pref = context.getSharedPreferences("user", Context.MODE_PRIVATE)
        val id = pref.getString("id", "")

        countEdit.hint = if (isBuy) "구매 개수" else "판매 개수"
        title.text = if (isBuy) "$coin 구매하기" else "$coin 판매하기"
        ok.setOnClickListener {
            if (!isPaying) {
                isPaying = true
                if (isBuy) {
                    NetworkHelper.networkInstance.pay(id, countEdit.text.toString().toInt(), coin).enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                            Toast.makeText(context, "결제 성공", Toast.LENGTH_SHORT).show()
                            payCallback?.onPayFinish()
                            dismiss()
                        }

                        override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                            Toast.makeText(context, "결제 실패", Toast.LENGTH_SHORT).show()
                            dismiss()
                        }

                    })
                } else {
                    NetworkHelper.networkInstance.sale(id, countEdit.text.toString().toInt(), coin).enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                            Toast.makeText(context, "판매 성공", Toast.LENGTH_SHORT).show()
                            payCallback?.onPayFinish()
                            dismiss()
                        }

                        override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                            Toast.makeText(context, "판매 실패", Toast.LENGTH_SHORT).show()
                            dismiss()
                        }

                    })
                }
            }
        }
        cancel.setOnClickListener {
            dismiss()
        }

    }

    interface PayCallback {
        fun onPayFinish()
    }
}