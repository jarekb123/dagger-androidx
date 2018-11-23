package com.butajlo.daggerx

import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector

/** Provides an [AndroidInjector] of [Fragment]s. */
interface HasAndroidXFragmentInjector {

    /** Returns an [AndroidInjector] of [Fragment]s. */
    fun androidXFragmentInjector(): AndroidInjector<in Fragment>

}
