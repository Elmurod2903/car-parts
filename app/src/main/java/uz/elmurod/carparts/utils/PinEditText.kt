package uz.elmurod.carparts.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import uz.elmurod.carparts.R


class PinEditText(context: Context) : AppCompatEditText(context) {

    private var space = 8f

    private var charSize = 0f
    private var numChars = 6f
    private var lineSpacing = 16f

    private val maxLength = 6

    private val viewShadowRadius = 20f

    private var mClickListener: OnClickListener? = null

    private var mLineStroke = 1f

    private var mLineStrokeSelected = 2f

    private var mLinesPaint: Paint? = null

    init {
        val multi = context.resources.displayMetrics.density
        mLineStroke *= multi
        mLineStrokeSelected *= multi
        mLinesPaint = Paint(paint)
        mLinesPaint?.setShadowLayer(
            viewShadowRadius,
            4f,
            4f,
            ContextCompat.getColor(context, R.color.lavender_blue)
        )
        mLinesPaint?.strokeWidth = mLineStroke

        setBackgroundResource(0)
        space *= multi
        lineSpacing *= multi
        numChars = maxLength.toFloat()

        super.setCustomSelectionActionModeCallback(object : android.view.ActionMode.Callback {
            override fun onCreateActionMode(mode: android.view.ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onPrepareActionMode(mode: android.view.ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(
                mode: android.view.ActionMode?,
                item: MenuItem?
            ): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: android.view.ActionMode?) {}
        })

        super.setOnClickListener { v ->
            setSelection(text!!.length)
            mClickListener?.onClick(v)
        }

        this.isCursorVisible = false

    }

    override fun setOnClickListener(l: OnClickListener?) {
        mClickListener = l
    }

    override fun onDraw(canvas: Canvas?) {
        setLayerType(LAYER_TYPE_SOFTWARE, paint)

        val availableWidth = width - viewShadowRadius - paddingRight - paddingLeft
        charSize = if (space < 0) {
            availableWidth / (numChars * 2 - 1)
        } else {
            (availableWidth - space * (numChars - 1)) / numChars
        }

        var startX = paddingLeft
        val bottom = height - paddingBottom

        val text = text
        val textLength = text!!.length
        val textWidths = FloatArray(textLength)
        paint.getTextWidths(getText(), 0, textLength, textWidths)
        mLinesPaint?.color = ContextCompat.getColor(context, R.color.white)

        for (i in 0 until numChars.toInt()) {
            canvas!!.drawRoundRect(
                startX.toFloat(),
                (bottom + paddingBottom).toFloat() - 16f,
                startX + charSize,
                (bottom + paddingBottom).toFloat() - 16f - charSize,
                context.dp(8f),
                context.dp(8f),
                mLinesPaint!!
            )
            if (getText()!!.length > i) {
                val middle: Float = startX + charSize / 2
                canvas.drawText(
                    text, i, i + 1, middle - textWidths[0] / 2, bottom - lineSpacing,
                    paint
                )
            }
            startX += if (space < 0) {
                (charSize * 2).toInt()
            } else {
                (charSize + space).toInt()
            }
        }
    }
}