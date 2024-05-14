package com.github.berkayertan

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


class FormattedEditText : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {

        addTextChangedListener(object : TextWatcher {
            private val MAX_LENGTH = 15

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (s.isNotEmpty()) {
                        val lastChar = s[s.length - 1]

                        if (!(Character.isDigit(lastChar) || lastChar == ',')) {
                            setText(s.substring(0, s.length - 1))
                            setSelection(s.length - 1)
                            return
                        }
                        if (s.length > MAX_LENGTH) {
                            removeTextChangedListener(this)
                            setText(s.subSequence(0, MAX_LENGTH))
                            setSelection(MAX_LENGTH)
                            addTextChangedListener(this)
                            return
                        }

                        if (s.count { it == ',' } > 1) {
                            val indexOfLastComma = s.lastIndexOf(',')
                            setText(s.replaceRange(indexOfLastComma, indexOfLastComma + 1, ""))
                            setSelection(indexOfLastComma)
                            return
                        }

                        if (s.any { Character.isDigit(it) } && lastChar == ',') {
                            return
                        }
                        if (s.contains(',')) {
                            val indexOfComma = s.indexOf(',')
                            val digitsAfterComma = s.substring(indexOfComma + 1).length
                            if (digitsAfterComma > 2) {
                                setText(s.substring(0, indexOfComma + 3))
                                setSelection(s.length - 1)
                                return
                            }
                        }
                        val sanitizedText = s.toString().replace("[^\\d,]".toRegex(), "")
                            .replace(",", ".")
                        val money = sanitizedText.toBigDecimalOrNull()
                        val formattedText = formattedMoney(money)
                        if (formattedText != text.toString()) {
                            removeTextChangedListener(this)
                            setText(formattedText)
                            setSelection(formattedText.length)
                            addTextChangedListener(this)
                        }
                    }
                }
            }
        })
    }
    private fun formattedMoney(money: BigDecimal?): String {
        if (money == null) return ""

        val unusualSymbols = DecimalFormatSymbols()
        unusualSymbols.decimalSeparator = ','
        unusualSymbols.groupingSeparator = '.'

        val formatter = DecimalFormat("#,##0.##", unusualSymbols)
        formatter.groupingSize = 3
        return formatter.format(money)
    }
}