package com.example.joyous.Homepage


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.joyous.models.StressData


class LineGraphView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint()
    private val dataPoints = mutableListOf<StressData>()

    fun setData(data: List<StressData>) {
        dataPoints.clear()
        dataPoints.addAll(data)
        invalidate() // Redraw the view when data is updated
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (dataPoints.isEmpty()) return

        val width = width.toFloat()
        val height = height.toFloat()
        val stepX = width / (dataPoints.size - 1)

        // Draw grid lines
        drawGrid(canvas, width, height)

        // Draw solid black border
        drawBorder(canvas, width, height)

        // Draw the line graph
        paint.color = Color.BLACK
        paint.strokeWidth = 5f

        for (i in 0 until dataPoints.size - 1) {
            val x1 = i * stepX
            val y1 = height - (dataPoints[i].value * height) // Scale value to fit within view
            val x2 = (i + 1) * stepX
            val y2 = height - (dataPoints[i + 1].value * height)

            // Draw the line
            canvas.drawLine(x1, y1, x2, y2, paint)
        }
    }

    // Draw dark grid lines
    private fun drawGrid(canvas: Canvas, width: Float, height: Float) {
        paint.color = Color.DKGRAY // Dark grid lines
        paint.strokeWidth = 2f // Thicker grid lines for visibility

        // Draw horizontal grid lines
        for (i in 0..10) {
            val y = height - (i * height / 10) // Divide height into 10 sections
            canvas.drawLine(0f, y, width, y, paint)
        }

        // Draw vertical grid lines
        for (i in 0 until dataPoints.size) {
            val x = i * (width / (dataPoints.size - 1))
            canvas.drawLine(x, 0f, x, height, paint)
        }
    }

    // Draw a solid black border around the graph
    private fun drawBorder(canvas: Canvas, width: Float, height: Float) {
        paint.color = Color.BLACK
        paint.strokeWidth = 4f // Solid black border

        // Top border
        canvas.drawLine(0f, 0f, width, 0f, paint)
        // Left border
        canvas.drawLine(0f, 0f, 0f, height, paint)
        // Bottom border
        canvas.drawLine(0f, height, width, height, paint)
        // Right border
        canvas.drawLine(width, 0f, width, height, paint)
    }
}
