package com.example.parsegit

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parsegit.network.NetworkApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerFragment : Fragment() {

    private val NOTIFICATION_ID = 5532
    private val NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"

    private val recyclerAdapter = RecyclerAdapter()
    private lateinit var mRecycler : RecyclerView
    private lateinit var mRequestButton : Button
    private lateinit var mLoginEditText : EditText

    fun newInstance() : RecyclerFragment{
        return RecyclerFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRequestButton = view.findViewById(R.id.bt_request)
        mLoginEditText = view.findViewById(R.id.et_login)
        mRecycler = view.findViewById(R.id.recycler)

        mRequestButton.setOnClickListener {
            val useLogin : String = mLoginEditText.text.toString()
            if(useLogin.isNotEmpty()){
                if(isInternetAvailable(activity as Context)){
                    makeRequest(useLogin)
                }else{
                    showMessage(getString(R.string.check_internet_connection))
                }
            }else{
                showMessage(getString(R.string.enter_user_login))
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mRecycler.layoutManager = LinearLayoutManager(activity)
        mRecycler.adapter = recyclerAdapter

        val itemTouchHelper = ItemTouchHelper(SwipeToDelete(recyclerAdapter))
        itemTouchHelper.attachToRecyclerView(mRecycler)
    }

    private fun makeRequest(loginUser : String){
        NetworkApi.getJsonApi()
            .getUser(loginUser)
            .enqueue(object : Callback<User>{
                override fun onFailure(call: Call<User>, t: Throwable) {
                    showMessage(getString(R.string.request_failed))
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if(response.isSuccessful){
                        val user = response.body()
                        user?.let { recyclerAdapter.addUser(it) }
                        mRecycler.scrollToPosition(recyclerAdapter.itemCount)
                        createNotification(user?.login)
                    }
                }
            })
    }

    private fun createNotification(userLogin : String?){
        createNotificationChannel()
        var builder = NotificationCompat.Builder(activity as Context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.user_loaded))
            .setContentText(userLogin)
        with(NotificationManagerCompat.from(activity as Context)){
            notify(NOTIFICATION_ID, builder.build())
        }
    }


    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "My Channel",
                NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager : NotificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun isInternetAvailable(context : Context) : Boolean{
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    private fun showMessage(msg : String){
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }
}