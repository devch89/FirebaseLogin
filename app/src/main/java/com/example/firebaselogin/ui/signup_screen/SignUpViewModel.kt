package com.example.firebaselogin.ui.signup_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaselogin.data.repository.AuthRepository
import com.example.firebaselogin.ui.login_screen.SignInState
import com.example.firebaselogin.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    /* We use channel here to send information between two Coroutines */
    val _signUpState = Channel<SignInState>() // sending some info inside this channel
    val signUpState = _signUpState.receiveAsFlow() // receiving something in this channel

    fun createUser(email: String, password:String) =
        viewModelScope.launch {
            repository.registerUser(email,password).collect{ result ->
                // we need to collect all the states
                when(result){
                    is Resource.Success -> {
                        _signUpState.send(SignInState(isSuccess = "User Created Successful"))

                    }

                    is Resource.Loading -> {
                        _signUpState.send(SignInState(isLoading = true))

                    }

                    is Resource.Error ->{
                        _signUpState.send(SignInState(isError = result.message))

                    }
                }
            }
        }
}
