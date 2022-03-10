package com.example.dineshareandroid.ui.callLog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.datastore.generated.model.CallLog
import com.amplifyframework.datastore.generated.model.Interest
import com.example.dineshareandroid.backend.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CallLogViewModel: ViewModel() {
    val createSuccess = MutableLiveData<Boolean>()
    val callLogList = MutableLiveData<List<CallLog>>()


//    fun createCallLog() {
//        viewModelScope.launch {
//            val user = withContext(Dispatchers.IO) {
//                UserData.getDynamoUser()
//            }
//            val isSuccess = withContext(Dispatchers.IO) {
//                UserData.createCallLog(user)
//            }
//
//            createSuccess.value = isSuccess
//        }
//    }

    fun getCallLog() {
        viewModelScope.launch  {
            val callLog = withContext(Dispatchers.IO) {
                UserData.getCallLog()
            }
            callLogList.value = callLog
        }
    }

    fun deleteCallLog(callLog: CallLog) {
        viewModelScope.launch  {
            val callLog = withContext(Dispatchers.IO) {
                UserData.deleteCallLog(callLog)
            }
        }
    }


    fun updateCallLog(callLog: MutableList<CallLog>) {
        viewModelScope.launch  {
            val isSuccess = withContext(Dispatchers.IO) {
                UserData.updateCallLog(callLog)
            }
        }
    }
}