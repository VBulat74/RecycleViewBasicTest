package ru.com.bulat.recycleviewbasic

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import ru.com.bulat.recycleviewbasic.databinding.ActivityMainBinding
import ru.com.bulat.recycleviewbasic.model.User
import ru.com.bulat.recycleviewbasic.model.UsersListener
import ru.com.bulat.recycleviewbasic.model.UsersService
import ru.com.bulat.recycleviewbasic.screens.UserDetailsFragment
import ru.com.bulat.recycleviewbasic.screens.UserListFragment
import java.util.function.ToIntBiFunction

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, UserListFragment())
                .commit()
        }
    }

    override fun showDetails(user: User) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, UserDetailsFragment.newInstance(user.id))
            .commit()
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun toast(messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }
}