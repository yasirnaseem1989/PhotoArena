package com.code.challenge.photoarena.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import timber.log.Timber

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private val classTag = this.javaClass.simpleName

    private var _bindingInflater: VB? = null
    protected val _binding: VB? get() = _bindingInflater

    open fun initializeView() {}
    open fun observeViewModel() {}

    init {
        Timber.d("[FRAGMENT] $classTag initialized")
    }

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _bindingInflater = bindingInflater(inflater, container, false)
        return _bindingInflater?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("[FRAGMENT] View $classTag created")
        initializeView()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingInflater = null
        Timber.d("[FRAGMENT] View $classTag destroyed")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("[FRAGMENT] $classTag destroyed")
    }
}