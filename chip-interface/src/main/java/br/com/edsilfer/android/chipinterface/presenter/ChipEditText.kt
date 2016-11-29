package br.com.edsilfer.android.chipinterface.presenter

import android.app.Dialog
import android.content.Context
import android.support.v7.widget.CardView
import android.text.InputType
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.*
import br.com.edsilfer.android.chipinterface.R
import br.com.edsilfer.android.chipinterface.model.Chip
import br.com.edsilfer.android.chipinterface.model.ChipControl
import br.com.edsilfer.android.chipinterface.model.ChipPalette
import br.com.edsilfer.kotlin_support.extensions.getDrawable
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.jetbrains.anko.onClick


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
        private var mCallback: CustomCallback? = null
        private var isAddingChip = false
        private var mPosX = -1f
        private var mPosY = -1f
    }

    private var mPalette: ChipPalette? = null

    // CONSTUCTORS
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    fun init() {
        inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
    }

    // PUBLIC INTERFACE
    override fun addChip(chip: Chip, replaceable: String) {
        mPalette ?: throw IllegalArgumentException("Before request to add a chip you need to provide a style")
        if (!ChipEditText.isAddingChip) {
            ChipEditText.isAddingChip = true
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.rsc_chip, null)
            val thumbnail = view.findViewById(R.id.thumbnail) as CircularImageView
            (view.findViewById(R.id.container) as RelativeLayout).addView(getCardView(chip))
            mCallback = CustomCallback(view, chip, this, replaceable, mPalette!!)
            Picasso.with(context).load(chip.getThumbnail()).into(thumbnail, mCallback)
        }
    }

    private fun getCardView(chip: Chip) : CardView {
        val wrapper = CardView(context)
        val params = RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
        wrapper.layoutParams = params
        wrapper.addView(getCollapsedHeader(chip.getHeader()))
        wrapper.setCardBackgroundColor(context.resources.getColor(mPalette!!.collapsedBackground))
        wrapper.radius = 50f
        return wrapper
    }

    private fun getCollapsedHeader(content: String): LinearLayout {
        val wrapper = LinearLayout (context)
        wrapper.layoutParams =  LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        wrapper.gravity = Gravity.CENTER_VERTICAL

        val collapsedHeader = TextView(ContextThemeWrapper(context, mPalette!!.collapsedHeader), null, 0)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        params.setMargins(
                context.resources.getDimension(R.dimen.dim_chip_item_label_left_margin).toInt(),
                context.resources.getDimension(R.dimen.dim_chip_item_label_top_bottom_margin).toInt(),
                context.resources.getDimension(R.dimen.dim_chip_item_label_right_margin).toInt(),
                context.resources.getDimension(R.dimen.dim_chip_item_label_top_bottom_margin).toInt()
        )

        collapsedHeader.layoutParams = params
        collapsedHeader.text = content

        wrapper.addView(collapsedHeader)
        return wrapper
    }

    override fun removeChip(chip: Chip) {
    }

    override fun setChipStyle(style: ChipPalette) {
        mPalette = style
    }

    //===
    /* private fun onDeleteChipClicked(chip: Chip, title: TextView) {
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
         parts.filter { it != chip.getHeader() }.forEach { setText("${text} $it ") }
     }*/

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mPosX = event.x
        mPosY = event.y
        return super.onTouchEvent(event)
    }

    private class CustomCallback(
            val mRootView: View,
            val mChip: Chip,
            val mInput: EditText,
            var mReplaceable: String,
            val mPalette: ChipPalette
    ) : Callback {
        override fun onError() {
            Log.e(this.javaClass.simpleName, "Unable to load thumbnail")
        }

        override fun onSuccess() {
            val bd = mRootView.getDrawable()
            bd.setBounds(0, 0, bd.intrinsicWidth, bd.intrinsicHeight)
            val sb = SpannableStringBuilder(mInput.text)

            val clickableSpannable = object : ClickableSpan() {
                override fun onClick(view: View) {
                    shopPopup(view)
                }
            }

            sb.setSpan(ImageSpan(bd), mInput.text.length - mReplaceable.length, mInput.text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            sb.setSpan(clickableSpannable, mInput.text.length - mReplaceable.length, mInput.text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            mInput.setText(TextUtils.concat(sb, " "))
            mInput.setSelection(mInput.text.length)
            mInput.movementMethod = LinkMovementMethod.getInstance()

            ChipEditText.isAddingChip = false
        }

        private fun shopPopup(view: View) {
            val dialog = Dialog(view.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window.setDimAmount(0f)
            dialog.setContentView(R.layout.rsc_chip_details)
            setDialogInitialPosition(dialog)
            setLayout(dialog, view)
            dialog.show()
        }

        private fun setLayout(dialog: Dialog, view: View) {
            val infoWrapper = dialog.findViewById(R.id.info_wrapper) as LinearLayout
            infoWrapper.addView(getExpandedTextView(dialog.context, mChip.getHeader(), mPalette.expandedHeader))
            infoWrapper.addView(getExpandedTextView(dialog.context, mChip.getSubheader(), mPalette.expandedSubheader))
            val thumbnail = dialog.findViewById(R.id.thumbnail) as ImageView
            val close = dialog.findViewById(R.id.close) as ImageButton
            setBackgroundCollor(dialog)

            close.onClick {
                dialog.dismiss()
            }

            Picasso.with(view.context).load(mChip.getThumbnail()).fit().centerCrop().into(thumbnail)
        }

        private fun setBackgroundCollor(dialog: Dialog) {
            val upperBackground = dialog.findViewById(R.id.upper_background)
            val bottomBackground = dialog.findViewById(R.id.bottom_background)
            upperBackground.setBackgroundColor(dialog.context.resources.getColor(mPalette.upperBackground))
            bottomBackground.setBackgroundColor(dialog.context.resources.getColor(mPalette.bottomBackground))
        }

        private fun getExpandedTextView(context: Context, content: String, style: Int): TextView {
            val collapsedHeader = TextView(ContextThemeWrapper(context, style), null, 0)
            collapsedHeader.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            collapsedHeader.text = content
            return collapsedHeader
        }

        private fun setDialogInitialPosition(dialog: Dialog) {
            val wmlp = dialog.window.attributes
            wmlp.gravity = Gravity.TOP or Gravity.LEFT
            wmlp.x = mPosX.toInt()
            wmlp.y = mPosY.toInt()
        }
    }
}

