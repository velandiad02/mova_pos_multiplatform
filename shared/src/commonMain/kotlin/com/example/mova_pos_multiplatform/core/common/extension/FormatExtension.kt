package com.example.mova_pos_multiplatform.core.common.extension

import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Money
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Date
import java.util.Locale

fun Money.formatCurrency(): String {
    val amountFormatted = this.amountInMinimumUnit / 100

    return try {
        val format = NumberFormat.getCurrencyInstance().apply {
            currency = Currency.getInstance(this@formatCurrency.currency)
        }

        format.format(amountFormatted)
    } catch (_: Exception) {
        "${this.currency} $amountFormatted"
    }
}

fun Long.formatDate(): String {
    val formatter = SimpleDateFormat(
        "dd MMM yyyy, HH:mm",
        Locale.getDefault()
    )
    return formatter.format(Date(this))
}