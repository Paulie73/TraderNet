package com.example.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KtorHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KtorWebSocketClient