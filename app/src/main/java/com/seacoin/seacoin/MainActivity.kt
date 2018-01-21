package com.seacoin.seacoin

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.nitrico.lastadapter.LastAdapter
import com.seacoin.seacoin.databinding.ItemCoinBinding
import com.seacoin.seacoin.models.CoinData
import com.seacoin.seacoin.util.NetworkHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_coin.view.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var pref: SharedPreferences
    private val coinItems = ArrayList<CoinData>()
    val prices = ArrayList<Int>()
    val counts = ArrayList<Int>()
    var isRefreshed = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pref = getSharedPreferences("user", Context.MODE_PRIVATE)
        toolbar.setTitleTextColor(Color.BLACK)
        toolbar.title = "Sea Coin"
        setSupportActionBar(toolbar)
        setChart()
        setLastAdapter()
    }

    private fun setLastAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        LastAdapter(coinItems, BR.item)
                .map<CoinData, ItemCoinBinding>(R.layout.item_coin) {
                    onBind {
                        val position = it.adapterPosition
                        it.itemView.container.setBackgroundColor(
                                if (position % 2 == 0) Color.parseColor("#eeeeee")
                                else Color.parseColor("#ffffff"))
                    }
                    onClick {
                        val coin = when (it.binding.item?.title) {
                            "동해코인" -> "ETC"
                            "서해코인" -> "WTC"
                            "남해코인" -> "STC"
                            else -> ""
                        }
                        CreditDialog(this@MainActivity, coin, object : CreditDialog.PayCallback {
                            override fun onPayFinish() {
                                isRefreshed = false
                                var count = 0;
                                val timer = Timer()
                                timer.schedule(object : TimerTask() {
                                    override fun run() {
                                        if (count++ >= 6)
                                            timer.cancel()
                                        else
                                            setCoinItems()
                                    }

                                }, 0, 2000)
                                setCoinItems()
                            }

                        }, true).show()
                    }
                    onLongClick {
                        val coin = when (it.binding.item?.title) {
                            "동해코인" -> "ETC"
                            "서해코인" -> "WTC"
                            "남해코인" -> "STC"
                            else -> ""
                        }
                        CreditDialog(this@MainActivity, coin, object : CreditDialog.PayCallback {
                            override fun onPayFinish() {
                                isRefreshed = false
                                var count = 0;
                                val timer = Timer()
                                timer.schedule(object : TimerTask() {
                                    override fun run() {
                                        if (count++ >= 3)
                                            timer.cancel()
                                        else
                                            setCoinItems()
                                    }

                                }, 0, 3000)
                                setCoinItems()
                            }

                        }, false).show()
                    }
                }
                .into(recyclerView)
    }

    private fun setCoinItems() {

        NetworkHelper.networkInstance.getCounts(pref.getString("id", "")).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val json = JSONObject(response?.body()?.string())
                if (json.getInt("result") == 1) {
                    counts.clear()
                    counts.add(json.getJSONObject("coin").getInt("ETC"))
                    counts.add(json.getJSONObject("coin").getInt("WTC"))
                    counts.add(json.getJSONObject("coin").getInt("STC"))
                    coinItems.clear()
                    coinItems.add(CoinData("동해코인", applicationContext.resources.getColor(R.color.colorEast), prices[0].toString(), counts[0].toString()))
                    coinItems.add(CoinData("서해코인", applicationContext.resources.getColor(R.color.colorWest), prices[1].toString(), counts[1].toString()))
                    coinItems.add(CoinData("남해코인", applicationContext.resources.getColor(R.color.colorSouth), prices[2].toString(), counts[2].toString()))
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
            }

        })

    }

    private fun setChart() {
        lineChart.run {
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.gridColor = Color.parseColor("#88ababab")
            axisLeft.gridColor = Color.parseColor("#88ababab")
            axisRight.isEnabled = false
            data = makeLineData()
            setBorderColor(Color.parseColor("#FFcccccc"))
        }
    }

    private fun makeLineData(): LineData {
        val values = ArrayList<LineDataSet>()
        NetworkHelper.networkInstance.getCoins().enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val json = JSONObject(response?.body()?.string())

                values.add(makeLineDataSet(json.getJSONArray("ETC"), "ETC"))
                values.add(makeLineDataSet(json.getJSONArray("WTC"), "WTC"))
                values.add(makeLineDataSet(json.getJSONArray("STC"), "STC"))

                lineChart.data = LineData(values.toList())

                prices.add(values[0].getEntryForIndex(values[0].entryCount - 1).y.toInt())
                prices.add(values[1].getEntryForIndex(values[1].entryCount - 1).y.toInt())
                prices.add(values[2].getEntryForIndex(values[2].entryCount - 1).y.toInt())
                setCoinItems()
            }

        })
        return LineData(values.toList())
    }

    private fun makeLineDataSet(jsonArray: JSONArray, title: String): LineDataSet {
        val values = ArrayList<Entry>()
        (0 until jsonArray.length()).forEach {
            val y = jsonArray.getJSONObject(it).getDouble("signifiWaveHeight")
            var x = jsonArray.getJSONObject(it).getString("obsTime")
            x = x.substring(x.length - 5)
            val str = x.split(":")
            values.add(Entry((str[0] + str[1]).toFloat(), y.toFloat() * 1000))
        }
        return LineDataSet(values, title).apply {
            setDrawCircles(true)
            circleRadius = 4f
            lineWidth = 2.5f
            when (title) {
                "ETC" -> {
                    color = Color.parseColor("#0072BB")
                    setCircleColor(Color.parseColor("#0072BB"))
                }
                "WTC" -> {
                    color = Color.parseColor("#FF4C3B")
                    setCircleColor(Color.parseColor("#FF4C3B"))
                }
                "STC" -> {
                    color = Color.parseColor("#FFD034")
                    setCircleColor(Color.parseColor("#FFD034"))
                }
            }
            setDrawValues(false)
        }
    }
}
