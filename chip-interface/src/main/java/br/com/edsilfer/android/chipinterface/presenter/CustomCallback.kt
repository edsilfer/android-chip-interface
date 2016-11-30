package br.com.edsilfer.android.chipinterface.presenter

import android.app.Dialog
import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.*
import br.com.edsilfer.android.chipinterface.R
import br.com.edsilfer.android.chipinterface.model.ChipPalette
import br.com.edsilfer.android.chipinterface.model.Chip
import br.com.edsilfer.kotlin_support.extensions.getDrawable
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
        val mPalette: ChipPalette,
        var mPosX: Int,
        var mPosY: Int
) : Callback {

    override fun onError() {
        Log.e(this.javaClass.simpleName, mRootView.context.getString(R.string.str_chip_interface_thumbnail_loading_error))
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

        val start = mInput.text.length - mReplaceable.length
        val end = mInput.text.length

        sb.setSpan(ImageSpan(bd), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        sb.setSpan(clickableSpannable, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        ChipEditText.mSpans.add(ChipEditText.Identifier(
                start,
                end,
                mChip.chipId
        ))

        mInput.setText(TextUtils.concat(sb, " "))
        mInput.setSelection(mInput.text.length)
        mInput.movementMethod = LinkMovementMethod.getInstance()

        ChipEditText.isAddingChip = false
    }

    private fun shopPopup(view: View) {
        val dialog = Dialog(view.context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // Prevent dialog to show blacked dimmed background
        // TODO: add Dialog extension on android-kotlin lib
        dialog.window.setDimAmount(0f)
        dialog.setContentView(mPalette.style.getLayout())
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
        setBackgroundColor(dialog)

        close.onClick {
            mInput.removeChip(mChip)
            dialog.dismiss()
        }

        Picasso.with(view.context).load(mChip.getThumbnail()).fit().centerCrop().into(thumbnail)
    }

    private fun setBackgroundColor(dialog: Dialog) {
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
        // println("received (x, y): ($mPosX, $mPosY)")
        wmlp.x = mPosX
        wmlp.y = mPosY
        dialog.window.attributes = wmlp
    }
}