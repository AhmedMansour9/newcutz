package com.gazr.Activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gazr.Adapter.Sizes_Adapter
import com.gazr.Model.AllProducts_Response
import com.gazr.R
import kotlinx.android.synthetic.main.activity_riviews.*

class Riviews : AppCompatActivity() {

    lateinit var detailsFavourits: AllProducts_Response.Data.Data
    private lateinit var DataSaver: SharedPreferences
    var UserToken: String?= String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riviews)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(this)
        UserToken = DataSaver.getString("token", null)

        T_Leave.setOnClickListener(){
            if(UserToken!=null){
                var intent= Intent(this,Add_Review::class.java)
                intent.putExtra("id",detailsFavourits.id.toString())
                startActivity(intent)

            }else {
                var intent= Intent(this,Login::class.java)
                startActivity(intent)
                finish()
            }
        }

        getReviwes()
    }

    fun getReviwes(){
        detailsFavourits=intent.getParcelableExtra("list")!!

        val listAdapter  = Sizes_Adapter(this,
            detailsFavourits.reviews
        )
        recycler_Addetional.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
        recycler_Addetional.setAdapter(listAdapter)


    }
}