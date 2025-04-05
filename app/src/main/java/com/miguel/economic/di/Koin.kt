import android.app.Application
import com.miguel.economic.data.di.koinDataModule
import com.miguel.economic.domain.di.koinDomainModule
import com.miguel.economic.gallery.di.koinGalleryModule
import com.miguel.economic.receipt.di.koinReceiptModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

internal fun Application.initKoin() {
    startKoin {
        androidLogger()
        androidContext(this@initKoin)
        modules(
            koinDataModule(),
            koinDomainModule(),
            koinGalleryModule(),
            koinReceiptModule()
        )
    }
}