package so.codex.hawk.di.modules

import dagger.Module

/**
 * Common modules that used in application
 */
@Module(
    includes = [
        NetworkModule::class,
        WorkspaceModule::class,
        ProjectModule::class,
        AppModule::class,
        LoginModule::class,
        UserModule::class
    ]
)
interface MainModule