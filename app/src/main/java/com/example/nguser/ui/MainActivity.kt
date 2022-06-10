package com.example.nguser.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.nguser.R
import com.example.nguser.adapter.UserAdapter
import com.example.nguser.data.base.Status
import com.example.nguser.data.model.User
import com.example.nguser.util.Coroutines
import com.example.nguser.util.UtilExtensions.myToast
import com.example.nguser.util.UtilExtensions.openActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var userAdapter: UserAdapter
    private val viewModel: UserViewModel by viewModels()
    private var isDelete = false

    companion object {
        const val USER_DATA = "USER_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init(){

        viewModel.getAllUser().observe(this) { it ->
            if (it.isEmpty() && !isDelete){ // if data local empty and delete state false fetch from server
                viewModel.getUser().observe(this){
                    when (it.status) {
                        Status.LOADING -> {
                            progress_circular.visibility = View.VISIBLE
                            listview_users.visibility = View.GONE
                        }
                        Status.SUCCESS -> {
                            progress_circular.visibility = View.GONE
                            listview_users.visibility = View.VISIBLE
                            Coroutines.main {
                                viewModel.addAllUser(it.data!!.user).also {
                                    print("success")
                                }
                            }

                            userAdapter = UserAdapter(this, it.data!!.user, object : UserAdapter.OnItemClickListener{
                                override fun onItemClick(user: User) {
                                    openActivity(AddUserActivity::class.java) {
                                        putParcelable(USER_DATA, user)
                                    }
                                }
                            })
                            listview_users.adapter = userAdapter
                        }
                        Status.FAIL -> {
                            progress_circular.visibility = View.GONE
                            myToast(it.message.toString())
                        }
                    }
                }
            }else{ // if data local not empty get from local dao
                text_delete_all.visibility = View.VISIBLE
                userAdapter = UserAdapter(this, it.toMutableList(), object : UserAdapter.OnItemClickListener{
                    override fun onItemClick(user: User) {
                        openActivity(AddUserActivity::class.java) {
                            putParcelable(USER_DATA, user)
                        }
                    }
                })
                listview_users.adapter = userAdapter
            }
        }

    }

    fun addUser(view: View) {
        openActivity(AddUserActivity::class.java)
    }

    fun deleteAll(view: View) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(getString(R.string.delete_message))
            .setCancelable(true)
            .setPositiveButton(getString(R.string.delete_ok)) { dialog, id ->
                Coroutines.main {
                    viewModel.deleteAllUser()
                    myToast(getString(R.string.success_delete_all))
                    isDelete = true
                }
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.delete_no)
            ) { _, _ -> }

        val alert = dialogBuilder.create()
        alert.setTitle(getString(R.string.delete_title))
        alert.show()
    }

}