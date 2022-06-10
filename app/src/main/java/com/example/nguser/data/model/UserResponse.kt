package com.example.nguser.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("customers")
    val user: MutableList<User>
)
