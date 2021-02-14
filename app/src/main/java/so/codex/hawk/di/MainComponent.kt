package so.codex.hawk.di

import dagger.Component
import so.codex.hawk.di.modules.MainModule
import so.codex.hawk.notification.domain.NotificationContainer
import so.codex.hawk.ui.login.LoginViewModel
import so.codex.hawk.ui.main.MainViewModel
import so.codex.hawk.ui.splash.SplashViewModel
import javax.inject.Singleton

/**
 * Component that need to declare all modules and places where you need to implement dependencies
 */
@Singleton
@Component(
    modules = [
        MainModule::class
    ],
)
interface MainComponent {
    fun inject(viewModel: MainViewModel)
    fun inject(viewModel: SplashViewModel)
    fun inject(container: NotificationContainer)
    fun inject(viewModel: LoginViewModel)
}