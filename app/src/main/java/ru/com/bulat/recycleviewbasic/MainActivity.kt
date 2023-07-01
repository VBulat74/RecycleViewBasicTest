package ru.com.bulat.recycleviewbasic

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.com.bulat.recycleviewbasic.databinding.ActivityMainBinding
import ru.com.bulat.recycleviewbasic.model.User
import ru.com.bulat.recycleviewbasic.model.UserListener
import ru.com.bulat.recycleviewbasic.model.UsersService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UsersAdapter

    private val usersService: UsersService
        get() = (applicationContext as App).usersService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UsersAdapter(object : UserActionListener{
            override fun onUserMove(user: User, moveBy: Int) {
                usersService.moveUser(user,-moveBy)
            }

            override fun onUserDelete(user: User) {
                usersService.deleteUser(user)
            }

            override fun onUserDetails(user: User) {
                Toast.makeText(this@MainActivity, "User: ${user.name}", Toast.LENGTH_SHORT).show()
            }

        })
        val linearLayoutManager = LinearLayoutManager(this)
        binding.recycleView.layoutManager = linearLayoutManager
        binding.recycleView.adapter = adapter

        usersService.addListener(userListener)
    }

    private val userListener:UserListener = {
        adapter.users = it
    }

    override fun onDestroy() {
        super.onDestroy()
        usersService.removeListener(userListener)
    }
}