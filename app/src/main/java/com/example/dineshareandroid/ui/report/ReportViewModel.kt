package com.example.dineshareandroid.ui.report

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.datastore.generated.model.Reported
import com.example.dineshareandroid.backend.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReportViewModel: ViewModel() {
    val isReported = MutableLiveData<Boolean>()
    val userReport = MutableLiveData<Reported?>()

    fun getReport(userId: String) {
        viewModelScope.launch  {
            val report = withContext(Dispatchers.IO) {
                UserData.getReport(userId)
            }
            userReport.value = report
        }
    }

    fun reportUser(userId: String, numReports: Reported?) {
        viewModelScope.launch  {
            val isSuccess = withContext(Dispatchers.IO) {
                UserData.reportUser(userId, numReports)
            }
            isReported.value = isSuccess
        }
    }
}