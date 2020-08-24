package com.luthfi.responsi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.user_layout.userName

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val user = intent.getParcelableExtra<User>("user")
        userName.text = "Name: ${user?.name}"
        userUsername.text = "Username: ${user?.username}"
        userEmail.text = "Email: ${user?.email}"
        userPhone.text = "Phone: ${user?.phone}"
        userWebsite.text = "Website: ${user?.website}"
    }
}