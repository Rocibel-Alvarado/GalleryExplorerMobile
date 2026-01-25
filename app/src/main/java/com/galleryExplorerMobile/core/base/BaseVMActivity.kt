package com.galleryExplorerMobile.core.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import javax.inject.Inject

abstract class BaseVMActivity<VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected abstract val bindingInflater: (layoutInflater: LayoutInflater) -> VB
    protected lateinit var binding: VB

    protected abstract val viewModelClass: Class<VM>
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[viewModelClass]

        setupUI()
        setupObservers()
    }

    protected abstract fun setupUI()
    protected abstract fun setupObservers()
}