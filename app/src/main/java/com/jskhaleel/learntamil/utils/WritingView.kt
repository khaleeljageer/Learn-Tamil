package com.jskhaleel.learntamil.utils

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

class WritingView : View {
    private var params: ViewGroup.LayoutParams? = null
    private var mpath: Path? = null
    private var mpaint: Paint? = null
    private var canvas: Canvas? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init()
    }

    private fun init() {
        isDrawingCacheEnabled = true
        drawingCacheQuality = DRAWING_CACHE_QUALITY_HIGH
        mpaint = Paint()
        mpaint!!.isAntiAlias = true
        mpaint!!.color = Color.BLACK
        mpaint!!.style = Paint.Style.STROKE
        mpaint!!.strokeJoin = Paint.Join.MITER
        mpaint!!.strokeWidth = 20f
        mpath = Path()
        params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        val pointx = event.x
        val pointy = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mpath!!.moveTo(pointx, pointy)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                mpath!!.lineTo(pointx, pointy)
                performClick()
                invalidate()
                return true
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(mpath!!, mpaint!!)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bm)
    }

    fun clearCanvas() {
        mpath!!.reset()
        postInvalidate()
    }
}