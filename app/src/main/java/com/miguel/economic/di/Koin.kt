import android.app.Application
import com.miguel.economic.data.di.koinModuleData
import com.miguel.economic.domain.di.koinDomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

internal fun Application.initKoin() {
    startKoin {
        androidLogger()
        androidContext(this@initKoin)
        modules(
            koinModuleData(),
            koinDomainModule()
        )
    }
}