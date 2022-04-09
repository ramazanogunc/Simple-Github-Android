package com.ramo.simplegithub.core.ext

import java.lang.reflect.ParameterizedType

fun <CLS> Class<*>.findGenericWithType(genericIndex: Int): Class<CLS> {
    @Suppress("UNCHECKED_CAST")
    return (genericSuperclass as ParameterizedType).actualTypeArguments[genericIndex] as Class<CLS>
}