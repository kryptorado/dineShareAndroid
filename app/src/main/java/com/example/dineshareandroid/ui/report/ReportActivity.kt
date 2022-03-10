package com.example.dineshareandroid.ui.report

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dineshareandroid.R
import kotlinx.android.synthetic.main.activity_report_user.*

class ReportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_user)

        report_yes.setOnClickListener {
            //startActivity(Intent(this, LoginActivity::class.java))
        }
        report_cancel.setOnClickListener {
            onBackPressed();
        }
    }
}