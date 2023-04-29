package org.clevercastle.saas.model


enum class AppType {
    Alpha,
    Gamma,
}

class AppTypeConverter : EnumConverter<AppType>(AppType::class.java)
