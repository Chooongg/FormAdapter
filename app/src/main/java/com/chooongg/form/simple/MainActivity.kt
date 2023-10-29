package com.chooongg.form.simple

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chooongg.form.simple.databinding.ActivityMainBinding
import com.chooongg.form.simple.fragment.AdvancedFragment
import com.chooongg.form.simple.fragment.BasicFragment
import com.chooongg.form.simple.fragment.StyleFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val fragments = arrayListOf(
        BasicFragment(), StyleFragment(), AdvancedFragment()
    )

    private val adapter by lazy { PageAdapter(this, fragments) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = adapter
        (binding.navigationView as NavigationBarView).apply {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.basic -> binding.viewPager.setCurrentItem(0, false)
                    R.id.style -> binding.viewPager.setCurrentItem(1, false)
                    R.id.advanced -> binding.viewPager.setCurrentItem(2, false)
                    else -> return@setOnItemSelectedListener false
                }
                true
            }
            binding.viewPager.currentItem = when (selectedItemId) {
                R.id.style -> 1
                R.id.advanced -> 2
                else -> 0
            }
        }

    }

    private class PageAdapter(activity: MainActivity, val fragments: MutableList<Fragment>) :
        FragmentStateAdapter(activity) {
        override fun getItemCount() = fragments.size
        override fun createFragment(position: Int) = fragments[position]
    }
}