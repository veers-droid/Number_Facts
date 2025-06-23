package com.example.numberfacts.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.numberfacts.ui.FactsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactoryDI) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FactsViewModel::class)
    abstract fun bindFactsViewModel(model: FactsViewModel) : ViewModel

}