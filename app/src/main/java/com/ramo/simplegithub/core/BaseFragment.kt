package com.ramo.simplegithub.core

import android.app.Dialog
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
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ramo.simplegithub.core.ext.findGenericWithType
import com.ramo.simplegithub.core.ext.observe
import com.ramo.simplegithub.core.ext.safeContext
import com.ramo.simplegithub.core.state.DialogEvent
import com.ramo.simplegithub.core.state.NavEvent
import com.ramo.simplegithub.customview.LoadingDialog

abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutId: Int
) : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    protected lateinit var viewModel: VM

    open fun isSharedViewModel(): Boolean = false

    private var loadingDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
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
        observe(viewModel.navigationEvent) { navEvent ->
            when (navEvent) {
                NavEvent.GoBack -> findNavController().popBackStack()
                is NavEvent.Navigate -> findNavController().navigate(navEvent.navDirections)
            }
        }
    }

    protected open fun showError(throwable: Throwable, cancelable: Boolean) {
        if (isAdded && view != null)
            Snackbar.make(
                requireView(),
                throwable.localizedMessage?: "Something went worng", Snackbar.LENGTH_LONG
            ).show();
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
            if (isSharedViewModel()) requireActivity() else this
        )[vmClass]
    }

    protected fun withVB(block: VB.() -> Unit) = with(binding, block)
}