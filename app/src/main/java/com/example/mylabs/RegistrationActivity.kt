package com.example.mylabs

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

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

        val loginPrompt = findViewById<TextView>(R.id.loginPrompt)
        val text = "Уже есть аккаунт? Войти"
        val spannableString = SpannableString(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = LoginActivity.newIntent(this@RegistrationActivity)
                val options = ActivityOptions.makeCustomAnimation(
                    this@RegistrationActivity,
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )
                startActivity(intent, options.toBundle())
            }
        }

        spannableString.setSpan(
            clickableSpan,
            text.indexOf("Войти"),
            text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        loginPrompt.text = spannableString
        loginPrompt.movementMethod = LinkMovementMethod.getInstance()

        val privacyTextView = findViewById<TextView>(R.id.privacyTextView)
        val privacyText = "Нажимая на кнопку, вы соглашаетесь с политикой конфиденциальности и пользовательским соглашением"
        val privacySpannable = SpannableString(privacyText)

        val privacyClick = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@RegistrationActivity,
                    "Полиика конфиденциальности",
                    Toast.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = Color.BLUE
                ds.isUnderlineText = false
            }
        }

        val termsClick = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@RegistrationActivity,
                    "Пользовательское соглашение",
                    Toast.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = Color.BLUE
                ds.isUnderlineText = false
            }
        }
        privacySpannable.setSpan(
            privacyClick,
            privacyText.indexOf("политикой конфиденциальности"),
            privacyText.indexOf("политикой конфиденциальности") + "политикой конфиденциальности".length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        privacySpannable.setSpan(
            termsClick,
            privacyText.indexOf("пользовательским соглашением"),
            privacyText.indexOf("пользовательским соглашением") + "пользовательским соглашением".length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        privacyTextView.text = privacySpannable
        privacyTextView.movementMethod = LinkMovementMethod.getInstance()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RegistrationActivity::class.java)
        }
    }
}