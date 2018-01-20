package com.seacoin.seacoin

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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_coin.view.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private val coinItems = ArrayList<CoinData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setChart()
        setCoinItems()
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
                }
                .into(recyclerView)
    }

    private fun setCoinItems() {
        coinItems.clear()
        coinItems.add(CoinData("동해코인", applicationContext.resources.getColor(R.color.colorEast), "1700(+ 2.7%)", "7,324,120"))
        coinItems.add(CoinData("서해코인", applicationContext.resources.getColor(R.color.colorWest), "800(- 3.7%)", "4,213,210"))
        coinItems.add(CoinData("남해코인", applicationContext.resources.getColor(R.color.colorSouth), "950(- 1.2%)", "2,112,270"))
    }

    private fun setChart() {
        val values = ArrayList<Entry>()
        values.add(Entry(0f, 1.0f))
        values.add(Entry(1f, 2.5f))
        values.add(Entry(2f, 1.5f))

        val items = ArrayList<LineDataSet>()
        items.add(makeDataSet(1))
        items.add(makeDataSet(2))
        items.add(makeDataSet(3))

        lineChart.run {
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.gridColor = Color.parseColor("#88ababab")
            axisLeft.gridColor = Color.parseColor("#88ababab")
            axisRight.isEnabled = false
            data = LineData(items.toList())
            setBorderColor(Color.parseColor("#FFcccccc"))
        }
    }

    private fun makeDataSet(position: Int): LineDataSet {
        val values = ArrayList<Entry>()
        (1..5).forEach {
            values.add(Entry(it.toFloat(), Random().nextInt(10 - 1) - 1.toFloat()))
        }

        return LineDataSet(values, "$position").apply {
            setDrawCircles(true)
            circleRadius = 4f
            lineWidth = 2.5f
            when (position) {
                1 -> {
                    color = Color.parseColor("#0072BB")
                    setCircleColor(Color.parseColor("#0072BB"))
                }
                2 -> {
                    color = Color.parseColor("#FF4C3B")
                    setCircleColor(Color.parseColor("#FF4C3B"))
                }
                3 -> {
                    color = Color.parseColor("#FFD034")
                    setCircleColor(Color.parseColor("#FFD034"))
                }
            }
            setDrawValues(false)
        }
    }
}
