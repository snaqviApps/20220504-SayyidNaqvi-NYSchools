package ghar.dfw.perm.nyschools.utils

import androidx.core.text.isDigitsOnly

fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun <R : Any> safeLet(p1: String?, p2: String?, p3: String?, block: (String, String, String) -> R?): R? {
        return if (
          ( p1.toString().isNotEmpty() && p1!!.isDigitsOnly()) &&
          ( p2.toString().isNotEmpty() && p2!!.isDigitsOnly()) &&
          ( p3.toString().isNotEmpty() && p3!!.isDigitsOnly())
        ) block(p1, p2, p3)

        else null
    }

