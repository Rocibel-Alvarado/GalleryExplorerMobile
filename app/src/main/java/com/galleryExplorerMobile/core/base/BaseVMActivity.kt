package com.galleryExplorerMobile.core.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class BaseVMActivity<VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {

    val binding: VB by lazy { inicializarViewBinding() }
    protected abstract fun inicializarViewBinding(): VB
    protected abstract fun onViewBindingCreated(savedInstanceState: Bundle?)

    protected lateinit var viewModel: VM

    @Suppress("UNCHECKED_CAST")
    private fun getViewModelClass(): Class<VM> {
        var clazz: Class<*> = this.javaClass
        var type: Type? = clazz.genericSuperclass

        while (type is Class<*>) {
            clazz = type
            type = clazz.genericSuperclass
        }

        val parameterizedType = type as ParameterizedType
        return parameterizedType.actualTypeArguments[0] as Class<VM>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        onViewBindingCreated(savedInstanceState)

        viewModel = ViewModelProvider(this)[getViewModelClass()]

    }

    protected fun applyBottomInset(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                v.paddingLeft,
                v.paddingTop,
                v.paddingRight,
                systemBars.bottom
            )
            insets
        }
    }
}