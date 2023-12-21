package com.ingridentify.components

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.ingridentify.R

class PasswordEditText : TextInputEditText {
    private var minLength: Int = 8
    
    constructor(context: Context) : super(context) {
        init(null)
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText)
        minLength = typedArray.getInteger(R.styleable.PasswordEditText_min_length, 8)
        typedArray.recycle()
        
        inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) { return }
                if (s.toString().length >= minLength) { return }

                error = context.getString(R.string.password_must_be_at_least_n_characters, minLength)
            }
            override fun afterTextChanged(s: android.text.Editable?) { }
        })
    }
}