package com.luthfi.responsi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.show()

        val users = ArrayList<User>()
        val adapter = Adapter(this, users)

        load(users, adapter)
        lv.adapter = adapter

        lv.setOnItemClickListener { adapterView, view, pos, id ->
            val item = adapterView.getItemAtPosition(pos) as Parcelable
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("user", item)
            }
            startActivity(intent)
        }
    }

    private fun load(users: ArrayList<User>, adapter: Adapter) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://jsonplaceholder.typicode.com/users"

        val stringReq = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val dataArray = JSONArray(response)
                for (i in 0 until dataArray.length()) {
                    val data = JSONObject(dataArray[i].toString())
                    val u = User(
                        data["id"].toString().toInt(),
                        data["name"].toString(),
                        data["username"].toString(),
                        data["email"].toString(),
                        data["phone"].toString(),
                        data["website"].toString()
                    )
                    users.add(u)
                }
                adapter.notifyDataSetChanged()
            },
            Response.ErrorListener { Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show() })
        queue.add(stringReq)
    }
}