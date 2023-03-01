package com.example.firebaselogin.ui.signup_screen

/* This data class manages all of our states to the UI */
data class SignUpState(

    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)