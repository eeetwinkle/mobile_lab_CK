package com.example.mylabs

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var oldPasswordInput: TextInputEditText
    private lateinit var newPasswordInput: TextInputEditText
    private lateinit var confirmPasswordInput: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        oldPasswordInput = (findViewById<TextInputLayout>(R.id.oldPasswordInput).editText as TextInputEditText)
        newPasswordInput = (findViewById<TextInputLayout>(R.id.newPasswordInput).editText as TextInputEditText)
        confirmPasswordInput = (findViewById<TextInputLayout>(R.id.confirmPasswordInput).editText as TextInputEditText)

        setupBackButton()
        setupSaveButton()
    }

    private fun setupBackButton() {
        findViewById<View>(R.id.backButton).setOnClickListener {
            navigateBackToProfile()
        }
    }

    private fun setupSaveButton() {
        findViewById<View>(R.id.saveButton).setOnClickListener {
            if (validatePasswords()) {
                savePasswordAndFinish()
            }
        }
    }

    private fun validatePasswords(): Boolean {
        val newPass = newPasswordInput.text.toString()
        val confirmPass = confirmPasswordInput.text.toString()

        if (newPass != confirmPass) {
            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun savePasswordAndFinish() {

        Toast.makeText(this, "Пароль успешно изменен", Toast.LENGTH_SHORT).show()
        navigateBackToProfile()
    }

    private fun navigateBackToProfile() {
        val intent = Intent(this, ProfileActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )

        startActivity(intent, options.toBundle())
        finish()
    }

}