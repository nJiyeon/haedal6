package com.team6.haedal6.model

class Place (
    val name: String,
    val address: String,
    val imageUrl: String? = null,
    val spring: Boolean,
    val summer: Boolean,
    val fall: Boolean,
    val winter: Boolean
)