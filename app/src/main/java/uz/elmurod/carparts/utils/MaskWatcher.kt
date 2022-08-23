package uz.elmurod.carparts.utils

import android.text.Editable
import android.text.TextWatcher


class MaskWatcher(
    maskString: String?,
    private val prefix: String,
    private val types: MaskTypes
) : TextWatcher {

    private var isRunning = false
    private var isDeleting = false
    private var mask: String? = null


    init {
        this.mask = maskString
    }

    fun getMaxLength() = mask?.length ?: 0

    override fun beforeTextChanged(
        charSequence: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {
        isDeleting = count > after
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(editable: Editable?) {
        if (isRunning || isDeleting) {
            return
        }

        val editableLength: Int = editable?.length ?: 0


        if (editableLength < prefix.length) {
            val temp = editable.toString()
            editable?.clear()
            editable?.append("$prefix $temp")
            return
        }

        if (editableLength >= mask!!.length) {
            return
        }

        isRunning = true

        if (editableLength < mask!!.length) {
            if (mask!![editableLength] != '#') {
                editable?.append(mask!![editableLength])
            } else if (mask!![editableLength - 1] != '#') {
                editable?.insert(editableLength - 1, mask, editableLength - 1, editableLength)
            }
        }

        if (editableLength > mask!!.length) {
            val temp = editable.toString()
            editable?.clear()
            editable?.append(temp)
            return
        }

        isRunning = false
    }

    companion object {


        fun buildUzbPhoneNumberWatcher(prefix: String): MaskWatcher {
            return MaskWatcher("$prefix ## ### ## ##", prefix, MaskTypes.PHONE_NUMBER)
        }

    }
}