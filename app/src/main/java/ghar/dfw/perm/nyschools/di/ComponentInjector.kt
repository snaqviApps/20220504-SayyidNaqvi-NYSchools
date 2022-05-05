package ghar.dfw.perm.nyschools.di

import dagger.Component
import ghar.dfw.perm.nyschools.ui.viewmodel.SchoolsViewModel

@Component(modules = [
    NetworkModule::class
])

interface ComponentInjector {

    fun inject(schoolsViewModel : SchoolsViewModel)

    @Component.Builder
    interface Builder {
        fun networkModule(schoolsModule : NetworkModule): Builder
        fun build(): ComponentInjector
    }

}