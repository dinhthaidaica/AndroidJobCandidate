package app.storytel.candidate.com

import android.app.Application
import app.storytel.candidate.com.di.applicationModule
import app.storytel.candidate.com.di.detailActivityModule
import app.storytel.candidate.com.di.mainActivityModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
        }
        loadKoinModules(listOf(applicationModule, mainActivityModule, detailActivityModule))
        setupTimber()
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

}