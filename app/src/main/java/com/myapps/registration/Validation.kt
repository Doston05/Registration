package com.myapps.registration

import android.content.Context
import android.widget.Toast
import com.myapps.registration.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Validation {

    fun validiatePassword(context: Context,pass: String,confirmPass:String): Boolean {



        if (!pass.matches(".*\\d.*".toRegex())) {
            Toast.makeText(context, "Password should contain at least one digit(0-9) ", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!pass.matches(".*[A-Z].*".toRegex())) {
            Toast.makeText(context, "Password should contain at least one uppercase letter(a-z)", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!pass.matches(".*[a-z].*".toRegex())) {
            Toast.makeText(context, "Password should contain at least one lowercase letter(a-z)", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!pass.matches(".*[!@#$%^&*+=?-].*".toRegex())) {
            Toast.makeText(context, "Password should contain at least one special character ( @, #, %, &, !, \$, etc…)", Toast.LENGTH_SHORT).show()

            return false
        }
        if (pass.length < 8 || pass.length > 15) {
            Toast.makeText(context, "Password length should be between 8 to 15 characters.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (pass.contains(" ")) {
            Toast.makeText(context, "Password should not contain any space. ", Toast.LENGTH_SHORT).show()

            return false
        }
        if (pass!=confirmPass){
            Toast.makeText(context, "Password doesn`t matches with confirm password", Toast.LENGTH_SHORT).show()
            return false

        }
        Toast.makeText(context, "You`re successfully created new account please login", Toast.LENGTH_SHORT).show()

        return true
    }
    fun validateUsername(context: Context,username:String):Boolean{
        val db= AppDatabase
        val userDao = db.getInstance(context)
        var listUser:List<User> = ArrayList()
        CoroutineScope(Dispatchers.IO).launch {
             listUser = userDao.userDao().getAll()
        }
            if (listUser.isNotEmpty()) {
                for (i in listUser.indices) {
                    if (listUser[i].username == username) {
                        Toast.makeText(
                            context,
                            "This username is already taken ",
                            Toast.LENGTH_SHORT
                        ).show()
                        return false
                    }


                }

        }
        if (username.length < 3 || username.length > 15) {
            Toast.makeText(context, "username too short or too long", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

}