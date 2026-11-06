package com.shoeadda.app.core.util

import java.text.NumberFormat
import java.util.Locale

private val indiaLocale: Locale = Locale.forLanguageTag("en-IN")

/** Formats rupees the Indian way with western digits, for example 12499 becomes "₹12,499" and 125000 becomes "₹1,25,000". */
fun formatRupees(amount: Int): String =
    "₹" + NumberFormat.getIntegerInstance(indiaLocale).format(amount)
