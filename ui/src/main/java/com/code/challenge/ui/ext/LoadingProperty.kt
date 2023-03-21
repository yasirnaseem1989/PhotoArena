package com.code.challenge.ui.ext

import androidx.fragment.app.Fragment
import com.code.challenge.ui.loading.LoadingHandler
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class LoadingProperty : ReadOnlyProperty<Fragment, LoadingHandler> {

    private var loadingHandler: LoadingHandler? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): LoadingHandler {

        if (loadingHandler == null) {
            loadingHandler =
                thisRef.inject<LoadingHandler> { parametersOf(thisRef.requireContext()) }
                    .getValue(thisRef, property)
        }
        val handler = requireNotNull(loadingHandler)

        thisRef.viewLifecycleOwner.lifecycle.addObserver(handler)
        return handler
    }
}
