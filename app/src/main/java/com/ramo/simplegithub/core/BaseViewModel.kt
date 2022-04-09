package com.ramo.simplegithub.core

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramo.simplegithub.core.state.DialogEvent
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _dialogEvent = MutableLiveData<DialogEvent>()
    val dialogEvent: LiveData<DialogEvent> = _dialogEvent


    protected fun showLoading() {
        _isLoading.value = true
    }

    protected fun hideLoading() {
        _isLoading.value = false
    }

    fun showAlert(@StringRes messageResId: Int, cancelable: Boolean = true) {
        _dialogEvent.value = DialogEvent.Alert(messageResId, cancelable)
    }

    fun showError(exception: Throwable, cancelable: Boolean = false) {
        _dialogEvent.value = DialogEvent.Error(exception, cancelable)
    }

    fun hideDialogs() {
        _dialogEvent.value = DialogEvent.None
    }

    protected fun goRoute() {
        // TODO: Burasına bakılacak
    }

    protected fun safeScope(
        loadingVisible: Boolean = true,
        handleException: ((Exception) -> Unit)? = null,
        block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            var isException = false
            if (loadingVisible) showLoading()
            try {
                block()
            } catch (e: Exception) {
                isException = true
                if (handleException != null)
                    handleException.invoke(e)
                else
                    showError(e)
            }
            if (loadingVisible) hideLoading()
        }
    }

}