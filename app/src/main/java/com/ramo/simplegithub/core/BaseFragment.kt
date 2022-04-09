package com.ramo.simplegithub.core

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ramo.simplegithub.core.ext.findGenericWithType
import com.ramo.simplegithub.core.ext.observe
import com.ramo.simplegithub.core.ext.safeContext
import com.ramo.simplegithub.core.state.DialogEvent
import com.ramo.simplegithub.customview.LoadingDialog

abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutId: Int
) : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    protected lateinit var viewModel: VM

    open var isSharedViewModel: Boolean = false

    private var loadingDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
        initViewModel()
        initCommonObserver()
        initObserver()
        return binding.root
    }

    protected open fun initObserver() {}

    private fun initCommonObserver() {
        observe(viewModel.isLoading) { isLoading ->
            safeContext { itContext ->
                if (loadingDialog == null) loadingDialog = LoadingDialog(itContext)
                if (isLoading) showLoading() else dismissLoading()
            }
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
        AlertDialog.Builder(context)
            .setTitle("Error")
            .setMessage(throwable.toString()) // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(R.string.yes
            ) { dialog, which -> }
            .setNegativeButton(R.string.no, null)
            .setIcon(R.drawable.ic_dialog_alert)
            .show()
    }

    protected open fun showAlert(messageResId: Int, cancelable: Boolean) {
        Toast.makeText(requireContext(), getString(messageResId), Toast.LENGTH_SHORT).show()
    }

    protected open fun dismissError() {

    }

    protected open fun dismissAlert() {

    }

    protected open fun showLoading() {
        loadingDialog?.show()
    }

    protected open fun dismissLoading() {
        loadingDialog?.dismiss()
    }

    private fun initViewModel() {
        val vmClass = javaClass.findGenericWithType<VM>(1)
        viewModel = ViewModelProvider(
            if (isSharedViewModel) requireActivity() else this
        )[vmClass]
    }

    protected fun withVB(block: VB.() -> Unit) = with(binding, block)
}