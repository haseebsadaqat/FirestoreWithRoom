package com.example.task2kotlin
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnAttach
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.task2kotlin.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var db: FirebaseFirestore
    val usernames: MutableMap<String,String> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()
        val adapter = viewPagerAdapter(supportFragmentManager,lifecycle)
        binding.viewpager.adapter = adapter

        //decorate bottom nav menu on swipe(viewpager)
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
            when(position){
            0->binding.bottomNavigation.menu.findItem(R.id.firebase).setChecked(true)
            2->binding.bottomNavigation.menu.findItem(R.id.roomdb).setChecked(true)
            1-> binding.bottomNavigation.menu.findItem(R.id.api).setChecked(true)
            else->binding.bottomNavigation.menu.findItem(R.id.firebase).setChecked(true)
            }
                super.onPageSelected(position)
            }
        })
        //bottom nav
        binding.bottomNavigation.setOnItemSelectedListener {

    when(it.itemId){
        R.id.api -> binding.viewpager.setCurrentItem(1)
        R.id.firebase -> binding.viewpager.setCurrentItem(0)
        R.id.roomdb -> binding.viewpager.setCurrentItem(2)

        else -> FirebaseFragment.newInstance()
    }
    true
}
    }

}

