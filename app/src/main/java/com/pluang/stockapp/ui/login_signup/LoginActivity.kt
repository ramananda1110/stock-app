package com.pluang.stockapp.ui.login_signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.pluang.stockapp.R
import com.pluang.stockapp.databinding.ActivityLoginBinding
import com.pluang.stockapp.ui.home.view.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: AuthViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading
        val register = binding.textViewRegister

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(AuthViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.status().observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            login.visibility = View.VISIBLE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }


        })

        username.afterTextChanged {
            loginViewModel.inputDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }


        password.apply {
            afterTextChanged {
                loginViewModel.inputDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }



            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                login.visibility = View.GONE;
                loginViewModel.login(username.text.toString(), password.text.toString())


            }

            register.setOnClickListener {
                val intent = Intent(context, RegisterActivity::class.java)
                context.startActivity(intent)
            }

        }
    }


    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.email
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun TextInputEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}