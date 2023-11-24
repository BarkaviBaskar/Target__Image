package com.example.customdesign

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class TargetOverlay: View {
    private val shapeList: MutableList<OverlayShape>
    private var selectedShapeIndex: Int = 0

    constructor(context: Context) : super(context) {
        shapeList = mutableListOf()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        shapeList = mutableListOf()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int =0) : super(context, attrs, defStyleAttr) {
        shapeList = mutableListOf()
    }

    fun addCircle(centerX: Float, centerY: Float, circleRadius: Float,
                  rectangleWidth: Float, rectangleHeight: Float, spaceBelowCircle: Float) {
        val circle = OverlayCircle(centerX, centerY, circleRadius, rectangleWidth, rectangleHeight, spaceBelowCircle)
        shapeList.add(circle)
        invalidate()
    }

    fun addRectangle(centerX: Float, centerY: Float, rectWidth: Float, rectHeight: Float,
                     cutCornerSize: Float, spaceBelowRect: Float) {
        val rectangle = OverlayRect(centerX, centerY, rectWidth, rectHeight, cutCornerSize, spaceBelowRect)
        shapeList.add(rectangle)
        invalidate()
    }

    fun addSquare(centerX: Float, centerY: Float, squareSize: Float) {
        val square = OverlaySquare(centerX, centerY, squareSize)
        shapeList.add(square)
        invalidate()
    }
    fun clearShapes() {
        shapeList.clear()
        invalidate()
    }


    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        val shapeToDisplay = shapeList.getOrNull(selectedShapeIndex)

        shapeToDisplay?.let { overlayShape ->
            when (overlayShape) {
                is OverlayCircle -> {
                    drawCircle(canvas, overlayShape)
                }

                is OverlayRect -> {
                    drawRect(canvas, overlayShape)
                }

                is OverlaySquare -> {
                    drawSquare(canvas, overlayShape)
                }
            }
        }
    }



    private fun drawCircle(canvas: Canvas, overlayCircle: OverlayCircle) {
        val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.color = Color.RED
        paint.alpha = 100
        paint.style = Paint.Style.FILL

        canvas.drawCircle(overlayCircle.centerX, overlayCircle.centerY, overlayCircle.circleRadius, paint)
        paint.style = Paint.Style.STROKE
        paint.color = Color.RED
        paint.alpha = 100

        paint.style = Paint.Style.FILL
        paint.color = Color.RED
        paint.alpha = 100

        val horizontalRadius = overlayCircle.circleRadius * 3
        val verticalRadius = overlayCircle.circleRadius * 3

        val rectF = RectF(
            overlayCircle.centerX - overlayCircle.rectangleWidth / 2,
            overlayCircle.centerY + overlayCircle.spaceBelowCircle,
            overlayCircle.centerX + overlayCircle.rectangleWidth / 2,
            overlayCircle.centerY + overlayCircle.spaceBelowCircle + overlayCircle.rectangleHeight
        )
        canvas.drawRoundRect(rectF, horizontalRadius, verticalRadius, paint)
    }

    private fun drawRect(canvas: Canvas, overlayRect: OverlayRect) {
        val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.style = Paint.Style.FILL
        paint.color = Color.RED
        paint.alpha = 100

        val rectF = RectF(
            overlayRect.centerX - overlayRect.rectWidth / 2,
            overlayRect.centerY + overlayRect.spaceBelowRect,
            overlayRect.centerX + overlayRect.rectWidth / 2,
            overlayRect.centerY + overlayRect.spaceBelowRect + overlayRect.rectHeight
        )

        drawCutCornerRect(canvas, rectF, overlayRect.cutCornerSize, paint)
    }

    private fun drawCutCornerRect(canvas: Canvas, rectF: RectF, cutCornerSize: Float, paint: Paint) {
        val path = Path()

        val topRectHeightAbove = cutCornerSize / 2
        val topRectWidthAbove = cutCornerSize * 2
        val topRectLeftAbove = rectF.centerX() - topRectWidthAbove / 2
        val topRectRightAbove = rectF.centerX() + topRectWidthAbove / 2
        val topRectTopAbove = rectF.top - topRectHeightAbove
        val topRectBottomAbove = rectF.top
        canvas.drawRect(
            topRectLeftAbove,
            topRectTopAbove,
            topRectRightAbove,
            topRectBottomAbove,
            paint
        )

        path.moveTo(rectF.left + cutCornerSize, rectF.top)
        path.lineTo(rectF.right - cutCornerSize, rectF.top)


        path.lineTo(rectF.right, rectF.top + cutCornerSize)

        path.lineTo(rectF.right, rectF.bottom - cutCornerSize)


        val bottomRectHeightBelow = cutCornerSize / 2
        val bottomRectWidthBelow = cutCornerSize * 2
        val bottomRectLeftBelow = rectF.centerX() - bottomRectWidthBelow / 2
        val bottomRectRightBelow = rectF.centerX() + bottomRectWidthBelow / 2
        val bottomRectTopBelow = rectF.bottom
        val bottomRectBottomBelow = rectF.bottom + bottomRectHeightBelow
        canvas.drawRect(
            bottomRectLeftBelow,
            bottomRectTopBelow,
            bottomRectRightBelow,
            bottomRectBottomBelow,
            paint
        )

        path.lineTo(rectF.right - cutCornerSize, rectF.bottom)
        path.lineTo(rectF.left + cutCornerSize, rectF.bottom)
        path.lineTo(rectF.left, rectF.bottom - cutCornerSize)
        path.lineTo(rectF.left, rectF.top + cutCornerSize)
        path.close()

        canvas.drawPath(path, paint)
    }

    private fun drawSquare(canvas: Canvas, overlaySquare: OverlaySquare) {
        val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5F

        val gap = 100f

        drawRotatedSquare(
            canvas,
            overlaySquare.centerX - overlaySquare.squareSize - gap,
            overlaySquare.centerY - overlaySquare.squareSize - gap,
            overlaySquare.squareSize,
            45f
        )
        drawRotatedSquare(
            canvas,
            overlaySquare.centerX + gap,
            overlaySquare.centerY - overlaySquare.squareSize - gap,
            overlaySquare.squareSize,
            45f
        )

        drawRotatedSquare(
            canvas,
            overlaySquare.centerX - overlaySquare.squareSize / 2,
            overlaySquare.centerY - overlaySquare.squareSize / 2,
            overlaySquare.squareSize,
            45f
        )

        drawRotatedSquare(
            canvas,
            overlaySquare.centerX - overlaySquare.squareSize - gap,
            overlaySquare.centerY + gap,
            overlaySquare.squareSize,
            45f
        )
        drawRotatedSquare(
            canvas,
            overlaySquare.centerX + gap,
            overlaySquare.centerY + gap,
            overlaySquare.squareSize,
            45f
        )
    }

    private fun drawRotatedSquare(
        canvas: Canvas,
        left: Float,
        top: Float,
        size: Float,
        degrees: Float
    ) {
        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#40FF0000")

        canvas.save()
        canvas.rotate(degrees, left + size / 2, top + size / 2)
        drawSquare(canvas, left, top, size, paint)
        canvas.restore()
    }

    private fun drawSquare(canvas: Canvas, left: Float, top: Float, size: Float, paint: Paint) {
        val path = Path()
        path.addRect(left, top, left + size, top + size, Path.Direction.CW)

        paint.style = Paint.Style.FILL
        canvas.drawPath(path, paint)

        paint.style = Paint.Style.STROKE
        paint.color = Color.RED
        paint.alpha = 150
        canvas.drawPath(path, paint)
    }
}

open class OverlayShape(val centerX: Float = 0f, val centerY: Float = 0f)

open class OverlayCircle(
    centerX: Float,
    centerY: Float,
    val circleRadius: Float,
    val rectangleWidth: Float,
    val rectangleHeight: Float, val spaceBelowCircle:Float
) : OverlayShape(centerX, centerY)

open class OverlayRect(
    centerX: Float,
    centerY: Float,
    val rectWidth: Float,
    val rectHeight: Float,
    val cutCornerSize: Float,
    val spaceBelowRect:Float
) : OverlayShape(centerX, centerY)

open class OverlaySquare(centerX: Float, centerY: Float, val squareSize: Float) : OverlayShape(centerX, centerY)