package com.example.dineshareandroid.backend

data class RTM_TOKEN(
    val rtmToken: String,
)

data class MATCHING_RESULT(
    val state: String,
    val channelName: String = "",
    val token: String="",
    val otherUser: String=""
)