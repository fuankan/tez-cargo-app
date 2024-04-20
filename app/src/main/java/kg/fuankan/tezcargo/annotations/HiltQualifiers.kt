package kg.fuankan.tezcargo.annotations

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthCheckOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InterceptedWithTokenCheckerOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InterceptedWithTokenCheckerRetrofit