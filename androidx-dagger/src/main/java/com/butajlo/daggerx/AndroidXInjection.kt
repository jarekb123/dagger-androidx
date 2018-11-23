package com.butajlo.daggerx

import android.util.Log
import android.util.Log.DEBUG
import androidx.fragment.app.Fragment

/** Injects core Android types from AndroidX libraries. */
class AndroidXInjection private constructor() {
    companion object {
        private const val TAG = "daggerandroidx"

        /**
         * Injects `fragment` if an associated [dagger.android.AndroidInjector] implementation
         * can be found, otherwise throws an [IllegalArgumentException].
         *
         * Uses the following algorithm to find the appropriate `AndroidInjector<Fragment>` to
         * use to inject `fragment`:
         *
         * 1. Walks the parent-fragment hierarchy to find the a fragment that implements
         *   [HasAndroidXFragmentInjector], and if none do
         * 2. Uses the `fragment`'s [getActivity()][Fragment] `activity` if it implements
         *       [HasAndroidXFragmentInjector], and if not
         * 3. Uses the [android.app.Application] if it implements [HasAndroidXFragmentInjector].
         *
         * If none of them implement [HasAndroidXFragmentInjector], a
         * [IllegalArgumentException] is thrown.
         *
         * @throws IllegalArgumentException if no parent fragment, activity, or application implements
         *     {@link HasSupportFragmentInjector}.
         */
        fun inject(fragment: Fragment) {
            val hasAndroidXInjector = findHasFragmentInjector(fragment)
            if (Log.isLoggable(TAG, DEBUG)) {
                Log.d(TAG, "And injector for %s was found in %s".format(
                        fragment.javaClass.canonicalName,
                        hasAndroidXInjector.javaClass.canonicalName
                ))
            }
            val fragmentInjector = hasAndroidXInjector.androidXFragmentInjector()
            fragmentInjector.inject(fragment)
        }

        private fun findHasFragmentInjector(fragment: Fragment): HasAndroidXFragmentInjector {
            var parentFragment = fragment.parentFragment
            while (parentFragment != null) {
                if(parentFragment is HasAndroidXFragmentInjector) {
                    return parentFragment
                }
                parentFragment = parentFragment.parentFragment
            }
            val activity = fragment.activity
            if (activity is HasAndroidXFragmentInjector) {
                return activity
            }
            activity?.application?.also {
                if(it is HasAndroidXFragmentInjector) {
                    return it
                }
            }
            throw IllegalArgumentException(
                    "No injector was found for %s".format(fragment.javaClass.canonicalName)
            )
        }
    }
}