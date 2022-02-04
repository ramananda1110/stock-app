package com.pluang.stockapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.pluang.stockapp.R
import com.pluang.stockapp.data.model.LoggedInUser
import com.pluang.stockapp.ui.login_signup.LoggedInUserView
import com.pluang.stockapp.ui.login_signup.LoginResult

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class AuthRepository() {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }


    private val _result = MutableLiveData<LoginResult>()
    val authResult: LiveData<LoginResult> = _result


    fun login(username: String, password: String): MutableLiveData<LoginResult?> {
        val result: MutableLiveData<LoginResult?> =
            MutableLiveData<LoginResult?>()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            username,
            password
        )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val data =
                        LoginResult(success = LoggedInUserView(email = username));
                    result.postValue(data);

                    _result.value =
                        LoginResult(success = LoggedInUserView(email = username));

                } else {
                    val data =
                        LoginResult(error = R.string.login_failed)
                    result.postValue(data);
                }
            }


        return result
    }


    fun register(username: String, password: String): MutableLiveData<LoginResult?> {
        val result: MutableLiveData<LoginResult?> =
            MutableLiveData<LoginResult?>()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            username,
            password
        )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    LoggedInUser(isLogin = true, email = username)

                } else {
                    LoggedInUser(isLogin = false, email = username)
                }
            }

        return result;
    }
}