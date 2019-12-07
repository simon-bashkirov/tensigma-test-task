package com.testtask.ui.livedata

import androidx.lifecycle.MutableLiveData

/**
 * Accepts new va;ue only if it is different form previous only if
 */
class ValueChangedLiveData<T> : MutableLiveData<T>() {
    override fun setValue(value: T) {
        if (getValue() != value) {
            super.setValue(value)
        }
    }

    override fun postValue(value: T) {
        if (getValue() != value) {
            super.postValue(value)
        }
    }
}