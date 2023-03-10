@file:OptIn(ExperimentalKojectApi::class)

package com.moriatsushi.koject.integrationtest.app.component

import com.moriatsushi.koject.ExperimentalKojectApi
import com.moriatsushi.koject.Koject
import com.moriatsushi.koject.inject
import com.moriatsushi.koject.integrationtest.app.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ComponentExtrasWithNamedTest {
    @Test
    fun successInject() = Koject.runTest {
        val extras = WithNamedComponentExtras()
        val value = inject<WithNamedComponentClass>(
            componentExtras = extras,
        )
        assertEquals("named-constructor", value.constructorValue)
        assertEquals("named-property", value.propertyValue)
        assertEquals("named-getter", value.getterValue)
    }
}