import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

internal fun Application.initKoin() {
    startKoin {
        androidLogger()
        androidContext(this@initKoin)
        modules()
    }
}