package com.seacoin.seacoin

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setChart()
    }

    fun setLastAdapter() {

    }

    fun setChart() {
        val values = ArrayList<Entry>()
        values.add(Entry(0f, 1.0f))
        values.add(Entry(1f, 2.5f))
        values.add(Entry(2f, 1.5f))

        val items = ArrayList<LineDataSet>()
        items.add(makeDataSet(1))
        items.add(makeDataSet(2))
        items.add(makeDataSet(3))

        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.xAxis.gridColor = Color.parseColor("#88ababab")
        lineChart.axisLeft.gridColor = Color.parseColor("#88ababab")
        lineChart.axisRight.isEnabled = false
        lineChart.data = LineData(items.toList())
        lineChart.setBorderColor(Color.parseColor("#FFcccccc"))
        lineChart.
//        lineChart.setDrawBorders(true)
    }

    private fun makeDataSet(position: Int): LineDataSet {
        val values = ArrayList<Entry>()
        (1..5).forEach {
            values.add(Entry(it.toFloat(), Random().nextInt(10 - 1) - 1.toFloat()))
        }

        val dataSet = LineDataSet(values, "$position").apply {
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
        return dataSet
    }
}
