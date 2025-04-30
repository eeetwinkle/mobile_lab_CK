package com.example.mylabs

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
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

        val registerPrompt = findViewById<TextView>(R.id.registerPrompt)
        val text = "Еще нет аккаунта? Зарегистрироваться"
        val spannableString = SpannableString(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = RegistrationActivity.newIntent(this@LoginActivity)
                val options = ActivityOptions.makeCustomAnimation(
                    this@LoginActivity,
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )
                startActivity(intent, options.toBundle())
            }
        }

        spannableString.setSpan(
            clickableSpan,
            text.indexOf("Зарегистрироваться"),
            text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        registerPrompt.text = spannableString
        registerPrompt.movementMethod = LinkMovementMethod.getInstance()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}