package br.com.coupledev.bookpedia

import android.app.Application
import br.com.coupledev.bookpedia.di.appModule
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class BookpediaApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BookpediaApp)
            androidLogger()
            modules(appModule)
        }
    }
}