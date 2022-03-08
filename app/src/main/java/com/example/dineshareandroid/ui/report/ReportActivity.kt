package com.example.dineshareandroid.ui.report

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_report_user.*

class ReportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)

        report_yes.setOnClickListener {
            //startActivity(Intent(this, LoginActivity::class.java))
        }
        report_cancel.setOnClickListener {
            onBackPressed();
        }
    }
}