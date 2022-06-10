package com.example.nguser.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.nguser.R
import com.example.nguser.data.model.User
import com.example.nguser.util.Coroutines
import com.example.nguser.util.UtilExtensions.myToast
import com.example.nguser.util.UtilExtensions.setTextEditable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_user.*

@AndroidEntryPoint
class AddUserActivity : AppCompatActivity() {

    private val viewModel by viewModels<UserViewModel>()
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        initToolbar()

        user = intent.extras?.getParcelable(MainActivity.USER_DATA)
        init()

    }

    private fun initToolbar() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    @SuppressLint("SetTextI18n")
    private fun init(){

        if (user != null){
            text_title.text = getString(R.string.text_update_user)
            edit_name.setTextEditable(user?.name ?: "")
            edit_email.setTextEditable(user?.email ?: "")
            edit_phone.setTextEditable(user?.mobile ?: "")
            btn_delete.visibility = View.VISIBLE
        }else{
            text_title.text = getString(R.string.text_add_user)
        }

    }

    private fun saveData() {

        val id = if (user != null) user?.id else null
        val name = edit_name.text.toString().trim()
        val email = edit_email.text.toString().trim()
        val phone = edit_phone.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            myToast(getString(R.string.form_empty))
            return
        }

        val user = User(id = id, name = name, email = email, mobile = phone)
        Coroutines.main {
            if (id != null) { // for update user
                viewModel.updateUser(user).also {
                    myToast(getString(R.string.success_update))
                    finish()
                }
            } else { //for insert note
                viewModel.addUser(user).also {
                    myToast(getString(R.string.success_save))
                    finish()
                }
            }
        }
    }

    fun submit(view: View) {
        saveData()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    fun delete(view: View) {
        Coroutines.main {
            viewModel.deleteUser(user!!)
            myToast(getString(R.string.success_delete))
            finish()
        }
    }


}