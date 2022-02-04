package com.pluang.stockapp.ui.home.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.pluang.stockapp.R
import com.pluang.stockapp.databinding.FragmentProfileBinding
import com.pluang.stockapp.ui.login_signup.LoginActivity


class ProfileFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance();

        checkUser()


        binding.btnLogout.setOnClickListener {
            signOut()
        }


        return binding.root
    }


    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            val email = firebaseUser.email
            binding.tvEmail.text = email
        } else {
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }


    fun signOut() {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(resources.getString(R.string.sign_out))
            .setMessage(resources.getString(R.string.sign_out_confirmation))
            .setNegativeButton(resources.getString(R.string.no)) { dialog, which ->
                dialog.dismiss();
            }
            .setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->
                firebaseAuth.signOut()
                checkUser()
            }
            .show()
    }
}