package com.pedro.environmentreader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.pedro.environmentreader.databinding.ActivityMainBinding
import com.pedro.environmentreader.requestpermission.RequestPermissionFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showRequestPermissionFragment()
    }

    private fun showRequestPermissionFragment() {
        supportFragmentManager.commit {
            replace(binding.fragmentContainer.id, RequestPermissionFragment())
        }
    }
}