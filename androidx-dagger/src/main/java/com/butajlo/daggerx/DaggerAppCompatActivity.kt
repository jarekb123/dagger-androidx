package com.butajlo.daggerx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import javax.inject.Inject

abstract class DaggerAppCompatActivity : AppCompatActivity(), HasFragmentInjector, HasAndroidXFragmentInjector {

    @Inject
    lateinit var androidXFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun androidXFragmentInjector() = androidXFragmentInjector

    override fun fragmentInjector() = frameworkFragmentInjector
}