package com.example.dineshareandroid.ui.report

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dineshareandroid.R
import com.example.dineshareandroid.utils.LoadingDialog
import kotlinx.android.synthetic.main.activity_report_user.*

class ReportActivity : AppCompatActivity() {
    private val model: ReportViewModel by viewModels()
    lateinit var loader: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_user)
        loader = LoadingDialog(this)

        val remoteUserId = intent.getStringExtra("remoteUserId").toString()
        model.getReport(remoteUserId)

        report_yes.setOnClickListener {
            model.userReport.observe(this) { report ->
                model.reportUser(remoteUserId, report)
            }
            model.isReported.observe(this) { isSuccess ->
                loader.show()
                if (isSuccess) {
                    loader.dismiss()
                    onBackPressed();
                    Toast.makeText(applicationContext, "User has been reported!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Failed to report user, something went wrong", Toast.LENGTH_SHORT).show()
                    loader.dismiss()
                }
            }
        }
        report_cancel.setOnClickListener {
            onBackPressed();
        }
    }
}