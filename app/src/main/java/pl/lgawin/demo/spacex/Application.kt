package pl.lgawin.demo.spacex

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import pl.lgawin.demo.spacex.api.apiModule

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // https://github.com/InsertKoinIO/koin/issues/1188#issuecomment-970240532 issue workaround
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@Application)
            modules(appModule + apiModule)
        }
    }
}

val appModule = module {
    viewModel { LaunchpadListViewModel(get()) }
}
