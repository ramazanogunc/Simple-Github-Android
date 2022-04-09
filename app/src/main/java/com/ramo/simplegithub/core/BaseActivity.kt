package com.ramo.simplegithub.core

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.ramo.simplegithub.core.ext.findGenericWithType
import com.ramo.simplegithub.core.ext.observe
import com.ramo.simplegithub.core.state.DialogEvent
import com.ramo.simplegithub.customview.LoadingDialog

abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutId: Int
) : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    protected lateinit var viewModel: VM

    private var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        initViewModel()
        initObserver()
    }

    private fun initObserver() {
        observe(viewModel.isLoading) { isLoading ->
            if (loadingDialog == null) loadingDialog = LoadingDialog(this)
            if (isLoading) showLoading() else dismissLoading()

        }
        observe(viewModel.dialogEvent) { dialogEvent ->
            dismissAlert()
            dismissError()
            if (dialogEvent is DialogEvent.Alert)
                showAlert(dialogEvent.messageResId, dialogEvent.cancelable)
            else if (dialogEvent is DialogEvent.Error)
                showError(dialogEvent.throwable, dialogEvent.cancelable)

        }
    }

    protected open fun showError(throwable: Throwable, cancelable: Boolean) {
        Toast.makeText(this, throwable.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    protected open fun showAlert(messageResId: Int, cancelable: Boolean) {
        Toast.makeText(this, getString(messageResId), Toast.LENGTH_SHORT).show()
    }

    protected open fun dismissError() {

    }

    protected open fun dismissAlert() {

    }

    protected open fun showLoading() {
        loadingDialog?.show()
    }

    private fun dismissLoading() {
        loadingDialog?.dismiss()
    }

    private fun initDataBinding() {
        _binding = DataBindingUtil.setContentView(this, layoutId)
    }

    private fun initViewModel() {
        val vmClass = javaClass.findGenericWithType<VM>(1)
        viewModel = ViewModelProvider(this)[vmClass]
    }

    protected fun withVB(block: VB.() -> Unit) = with(binding, block)
}