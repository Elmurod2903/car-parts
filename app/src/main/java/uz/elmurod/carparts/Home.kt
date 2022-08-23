package uz.elmurod.carparts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import uz.elmurod.carparts.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private val homeFragment = HomeFragment.newInstance()
    private val profileFragment = ProfileFragment.newInstance()
    private val cartFragment = CartFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replacefragment(homeFragment)


        binding.bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.main -> {
                    replacefragment(homeFragment)
                binding.tvTitleNav.text=resources.getString(R.string.main)
                }
                R.id.cart -> {
                    replacefragment(cartFragment)
                    binding.tvTitleNav.text=resources.getString(R.string.cart)

                }
                R.id.profile -> {
                    replacefragment(profileFragment)
                    binding.tvTitleNav.text=resources.getString(R.string.profile)
                }
            }
            return@setOnItemSelectedListener true

        }
    }

    private fun replacefragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, fragment)
            transaction.commit()
        }

    }
}