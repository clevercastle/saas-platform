package org.clevercastle.saas.base


enum class AppType {
    Alpha,
    Gamma,
}

class AppTypeConverter : EnumConverter<AppType>(AppType::class.java)
