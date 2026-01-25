package com.galleryExplorerMobile.core.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.galleryExplorerMobile.R
import com.galleryExplorerMobile.databinding.SnackbarCustomErrorBinding
import com.google.android.material.snackbar.Snackbar

class SnackBarManager {
    companion object {
        const val EMPTY_TEXT = ""

        fun showSnackbar(context: Context, model: SnackBarModel, anchorView: View) {
            val snackbar = Snackbar.make(anchorView, EMPTY_TEXT, Snackbar.LENGTH_INDEFINITE)

            val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
            snackbarLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.color_red))

            val binding = SnackbarCustomErrorBinding.inflate(LayoutInflater.from(context))

            when (model.tipo) {
                SnackBarModel.SNACKBAR_TIPO.ERROR -> binding.ivError.setImageResource(R.drawable.ic_error_snackbar)
                else -> binding.ivError.setImageResource(R.drawable.ic_error_snackbar)
            }

            binding.textError.text = model.mensaje ?: EMPTY_TEXT

            binding.ivClose.setOnClickListener { snackbar.dismiss() }

            if (!model.actionText.isNullOrEmpty() && model.actionListener != null) {
                binding.ivClose.setOnClickListener(model.actionListener)
            }

            snackbarLayout.removeAllViews()
            snackbarLayout.addView(binding.root)

            snackbar.setAnchorView(anchorView)

            snackbar.show()
        }
    }
}