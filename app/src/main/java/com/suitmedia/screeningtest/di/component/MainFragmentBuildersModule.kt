package com.suitmedia.screeningtest.di.component

import com.suitmedia.screeningtest.features.screenone.HomeFragment
import com.suitmedia.screeningtest.features.screentwo.DashboardFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class MainFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment(): DashboardFragment
}