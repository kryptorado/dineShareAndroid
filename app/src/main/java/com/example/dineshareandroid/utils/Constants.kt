package com.example.dineshareandroid.utils

import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Interest

object Constants {
    val sports = Interest.builder()
        .name("sports")
        .strength(1)
        .interestId(1)
        .build()

    val music = Interest.builder()
        .name("music")
        .strength(1)
        .interestId(2)
        .build()

    val cooking = Interest.builder()
        .name("cooking")
        .strength(1)
        .interestId(3)
        .build()

    val videoGames = Interest.builder()
        .name("video games")
        .strength(1)
        .interestId(4)
        .build()

    val arts = Interest.builder()
        .name("arts & crafts")
        .strength(1)
        .interestId(5)
        .build()

    val interestNames = listOf<String>("sports", "music", "cooking", "video games", "arts & crafts")
//    val interests = listOf<Interest>(sports, music, cooking, videoGames, arts)

}