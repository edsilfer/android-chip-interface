package br.com.edsilfer.android.chipinterface.presenter

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import br.com.edsilfer.android.chipinterface.R
import br.com.edsilfer.android.chipinterface.model.Chip
import br.com.edsilfer.android.chipinterface.model.ChipEvents
import br.com.edsilfer.android.chipinterface.model.xml.State
import br.com.edsilfer.kotlin_support.extensions.*
import br.com.edsilfer.kotlin_support.model.xml.Text
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.jetbrains.anko.onClick


/**
 * Created by efernandes on 29/11/16.
 */
class CustomCallback(
        val mRootView: View,
        val mChip: Chip,
        val mInput: ChipEditText,
        var mReplaceable: String,
        var expandedState: State
) : Callback {

    override fun onError() {
        Log.e(this.javaClass.simpleName, mRootView.context.getString(R.string.str_chip_interface_thumbnail_loading_error))
    }

    override fun onSuccess() {
        val bd = mRootView.getBitmapDrawable()
        bd.setBounds(0, 0, bd.intrinsicWidth, bd.intrinsicHeight)
        val sb = SpannableStringBuilder(mInput.text)

        val clickableSpannable = object : ClickableSpan() {
            override fun onClick(view: View) {
                showPopup(view)
            }
        }

        val start = mInput.text.length - mReplaceable.length
        val end = mInput.text.length

        sb.setSpan(ImageSpan(bd), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        sb.setSpan(clickableSpannable, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        mChip.range = Pair(start, end)
        ChipEditText.mChips.add(mChip)
        notifySubscribers(ChipEvents.CHIP_ADDED, mChip)

        mInput.setText(TextUtils.concat(sb, " "))
        mInput.setSelection(mInput.text.length)
        mInput.movementMethod = LinkMovementMethod.getInstance()

        ChipEditText.isAddingChip = false
    }

    // TODO: ADD EFFECT INTO PALETTE
    private fun showPopup(view: View) {
        val rootView = LayoutInflater.from(view.context).inflate(R.layout.rsc_chip_details_circular_thumbnail, null, false)
        rootView.animation = AnimationUtils.loadAnimation(view.context, android.R.anim.slide_in_left)
        val popup = PopupWindow(
                rootView,
                view.context.resources.getDimension(R.dimen.dim_chip_item_details_min_width).toInt(),
                view.context.resources.getDimension(R.dimen.dim_chip_item_details_min_height).toInt(),
                false
        )
        popup.isTouchable = true
        popup.isFocusable = true
        popup.isOutsideTouchable = true
        setLayout(popup, rootView)
        val location = view.locateView()
        popup.showAtLocation(view, Gravity.TOP or Gravity.LEFT, location!!.left, location.bottom)
    }

    private fun setLayout(popup: PopupWindow, view: View) {
        val infoWrapper = popup.contentView.findViewById(R.id.info_wrapper) as LinearLayout
        infoWrapper.addView(getExpandedTextView(popup.contentView.context, mChip.getHeader(), expandedState.text[0]))
        infoWrapper.addView(getExpandedTextView(popup.contentView.context, mChip.getSubheader(), expandedState.text[0]))
        val thumbnail = popup.contentView.findViewById(R.id.thumbnail) as ImageView
        val close = popup.contentView.findViewById(R.id.close) as ImageButton
        setBackgroundColor(popup)
        close.onClick {
            mInput.removeChip(mChip)
            popup.dismiss()
        }
        Picasso.with(view.context).load(mChip.getThumbnail()).fit().centerCrop().into(thumbnail)
    }

    private fun setBackgroundColor(popup: PopupWindow) {
        val topBackground = popup.contentView.findViewById(R.id.upper_background)
        val bottomBackground = popup.contentView.findViewById(R.id.bottom_background)

        val topColor = expandedState.background["top"]
        val bottomColor = expandedState.background["bottom"]

        log("retrieved top color: $topColor")
        log("retrieved bottom color: $bottomColor")

        topBackground.setBackgroundColor(Color.parseColor(topColor))
        bottomBackground.setBackgroundColor(Color.parseColor(bottomColor))
    }


    private fun getExpandedTextView(context: Context, content: String, style: Text): TextView {
        val tv = TextView(context)
        tv.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        tv.text = content
        tv.gravity = Gravity.LEFT
        tv.setStyle(style)
        return tv
    }
}