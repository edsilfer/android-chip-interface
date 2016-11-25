package br.com.edsilfer.android.chipinterface.presenter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.ImageSpan
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import br.com.edsilfer.android.chipinterface.R
import br.com.edsilfer.android.chipinterface.model.Chip
import br.com.edsilfer.android.chipinterface.model.ChipControl
import br.com.edsilfer.kotlin_support.extensions.getDrawable
import br.com.edsilfer.kotlin_support.extensions.log
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rsc_item_chip.view.*


/**
 * Created by User on 24/11/2016.
 */

class ChipEditText : EditText, ChipControl {

    enum class TVDrawable(val mValue: Int) {
        DRAWABLE_LEFT(0),
        DRAWABLE_TOP(1),
        DRAWABLE_RIGHT(2),
        DRAWABLE_BOTTOM(3);
    }

    companion object {
        private var mCallback : CustomCallback? = null
        var isAddingChip = false
        var count = 0
    }


    private var mThumbnail: CircularImageView? = null
    private var mRootView : View? = null
    private var mLabel : TextView? = null


    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
    }


    // PUBLIC INTERFACE
    override fun addChip(context: Context, chip: Chip, replaceable : String) {
        if (!ChipEditText.isAddingChip) {
            ChipEditText.isAddingChip = true
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            mRootView = inflater.inflate(R.layout.rsc_item_chip, null)

            mLabel = mRootView!!.findViewById(R.id.label) as TextView
            mThumbnail = mRootView!!.findViewById(R.id.thumbnail) as CircularImageView

            mLabel!!.text = chip.getTitle()
            mLabel!!.textSize = context.resources.getDimension(R.dimen.dim_chip_item_text_size)
            mCallback = CustomCallback(mRootView!!, chip, this, replaceable)

            Picasso.with(context).load(chip.getThumbnail()).into(mThumbnail, mCallback)
        }
    }

    override fun removeChip(chip: Chip) {
    }

    private fun onDeleteChipClicked(chip: Chip, title: TextView) {
        label.setOnTouchListener {
            view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (motionEvent.rawX >= (label.right - label.compoundDrawables[TVDrawable.DRAWABLE_RIGHT.mValue].bounds.width())) {
                    eraseChipFromTextView(chip)
                }
            }
            true
        }
    }

    private fun eraseChipFromTextView(chip: Chip) {
        setText("")
        val parts = text.split(" ")
        parts.filter { it != chip.getTitle() }.forEach { setText("${text} $it ") }
    }

    private class CustomCallback(val mRootView : View, val mChip : Chip, val mInput : EditText, var mReplaceable : String) : Callback {
        override fun onError() {
            Log.e(this.javaClass.simpleName, "Unable to load thumbnail")
        }

        override fun onSuccess() {
            println("replaceable: $mReplaceable")
            val bd = mRootView.getDrawable()
            bd.setBounds(0, 0, bd.intrinsicWidth, bd.intrinsicHeight)
            val sb = SpannableStringBuilder(TextUtils.concat(mInput.text, " "))
            sb.setSpan(ImageSpan(bd), mInput.text.length - mReplaceable.length, mInput.text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            println("currently mInput.text is: ${mInput.text}")
            mInput.text = sb
            mInput.setSelection(mInput.text.length)
            println("input after including span: ${mInput.text}")
            ChipEditText.isAddingChip = false
        }
    }
}
