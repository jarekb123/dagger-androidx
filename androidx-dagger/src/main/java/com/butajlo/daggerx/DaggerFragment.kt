package com.butajlo.daggerx

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

abstract class DaggerFragment : Fragment(), HasAndroidXFragmentInjector {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onAttach(context: Context?) {
        AndroidXInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidXFragmentInjector() = childFragmentInjector

}