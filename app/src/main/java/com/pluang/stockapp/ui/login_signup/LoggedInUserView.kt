package com.pluang.stockapp.ui.login_signup

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val email: String
    //... other data fields that may be accessible to the UI
)