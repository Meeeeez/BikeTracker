package com.example.a20210624_biketracker_basic

import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout

object TabLayoutUtils {
    fun enableTabs(tabLayout: TabLayout?, enable: Boolean?) {
        val viewGroup = getTabViewGroup(tabLayout)
        if (viewGroup != null) for (childIndex in 0 until viewGroup.childCount) {
            if (enable != null) {
                viewGroup.getChildAt(childIndex)?.setEnabled(enable)
            }
        }
    }

    private fun getTabViewGroup(tabLayout: TabLayout?): ViewGroup? {
        var viewGroup: ViewGroup? = null
        if (tabLayout != null && tabLayout.childCount > 0) {
            val view: View? = tabLayout.getChildAt(0)
            if (view != null && view is ViewGroup) viewGroup = view
        }
        return viewGroup
    }
}