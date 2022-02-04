package com.pluang.stockapp.data

import com.google.firebase.auth.FirebaseAuth
import com.pluang.stockapp.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        lateinit var result: LoggedInUser
        try {

            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                username,
                password
            )
                .addOnCompleteListener { task ->
                    result = if (task.isSuccessful) {
                        LoggedInUser(isLogin = true, email = username)

                    }else {
                        LoggedInUser(isLogin = false, email = username)
                    }

                    if(result.isLogin){
                        Result.Success(result);
                    }
                }


            return Result.Success(result)

        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }


    fun register(username: String, password: String): Result<LoggedInUser> {
        lateinit var result: LoggedInUser;
        try {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                username,
                password
            )
                .addOnCompleteListener { task ->
                    result = if (task.isSuccessful) {
                        LoggedInUser(isLogin = true, email = username)

                    } else {
                        LoggedInUser(isLogin = false, email = username)
                    }
                }

            return Result.Success(result)

        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }


    fun logout() {

    }
}