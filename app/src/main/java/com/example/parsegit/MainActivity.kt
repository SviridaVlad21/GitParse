package com.example.parsegit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private val RECYCLER_FRAGMENT_TAG = "RECYCLER_FRAGMENT_TAG"
    lateinit var recyclerFragment : RecyclerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            recyclerFragment = RecyclerFragment().newInstance()
        }else{
            recyclerFragment = supportFragmentManager.findFragmentByTag(RECYCLER_FRAGMENT_TAG) as RecyclerFragment
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fr_container, recyclerFragment, RECYCLER_FRAGMENT_TAG)
            .commit()
    }
}