# Koject  [🚧 Work in progress 🚧]
Koject is a DI Container libreary for Kotlin Multiplatform.

```kotlin
fun main() {
    Koject.start()

    val controller = inject<Controller>()
}

@Provides
class Api

@Provides
class Repository(
    private val api: Api
)

@Provides
class Controller(
    private val repository: Repository
)
```

## Features

* Support [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
* Easy provide with annotations
* Code generation with [KSP](https://github.com/google/ksp)
* Support multi-module project

## Setup
### Multiplatform

You'll need to enable KSP.

<details open><summary>build.gradle.kts</summary>

```diff
plugins {
    kotlin("multiplatform")
+    id("com.google.devtools.ksp") version "<ksp-version>"
}

kotlin {
    jvm()
    js(IR) {
        nodejs()
        browser()
    }
    ios()

    sourceSets {
        val commonMain by getting {
            dependencies {
+                implementation("com.moriatsushi.koject:koject-core:1.0.0-alpha01")
            }
        }
    }
}

dependencies {
    // Add it according to your targets.
+    val processor = "com.moriatsushi.koject:koject-processor-app:1.0.0-alpha01"
+    add("kspJvm", processor)
+    add("kspJs", processor)
+    add("kspIosX64", processor)
+    add("kspIosArm64", processor)
}
```
</details>

### Single platform

You can use it in single module.

<details><summary>build.gradle.kts</summary>

```diff
plugins {
    kotlin("<target>")
+    id("com.google.devtools.ksp") version "<ksp-version>"
}

dependencies {
+    implementation("com.moriatsushi.koject:koject-core:1.0.0-alpha01")
+    ksp("com.moriatsushi.koject:koject-processor-app:1.0.0-alpha01")
}
```

</details>

### Library module
Use `koject-processor-lib` to avoid generating `Container` in library modules.

<details open><summary>build.gradle.kts (Multiplatform)</summary>

```diff
dependencies {
    // Add it according to your targets.
-    val processor = "com.moriatsushi.koject:koject-processor-app:1.0.0-alpha01"
+    val processor = "com.moriatsushi.koject:koject-processor-lib:1.0.0-alpha01"
    add("kspJvm", processor)
    add("kspJs", processor)
    add("kspIosX64", processor)
    add("kspIosArm64", processor)
}
```

</details>

<details><summary>build.gradle.kts (single platform)</summary>

```diff
dependencies {
    implementation("com.moriatsushi.koject:koject-core:1.0.0-alpha01")
-    ksp("com.moriatsushi.koject:koject-processor-app:1.0.0-alpha01")
+    ksp("com.moriatsushi.koject:koject-processor-app:1.0.0-alpha01")
}
```

</details>

## Usage
Add `@Provides` annotation to the class you want to provide.

```kotlin
@Provides
class Api

@Provides
class Repository(
    private val api: Api
)

@Provides
class Controller(
    private val repository: Repository
)
```

You can get an instance using `inject` after calling `Koject.start()`.

```kotlin
fun main() {
    Koject.start()

    val controller = inject<Controller>()
}
```

## TODO
This library is incomplete and the following features will be added later.

- [ ] [Allow provide from function #18](https://github.com/Mori-Atsushi/koject/issues/18)
- [ ] [Support singleton #19](https://github.com/Mori-Atsushi/koject/issues/19)
- [ ] [Allow provide same types #20](https://github.com/Mori-Atsushi/koject/issues/20)
- [ ] [Make type binding easier #21](https://github.com/Mori-Atsushi/koject/issues/21)
- [ ] [Make compile-time error messages easier to understand #22](https://github.com/Mori-Atsushi/koject/issues/22)
- [ ] [Add example projects #29](https://github.com/Mori-Atsushi/koject/issues/29)
- [ ] [Complete documentation #27](https://github.com/Mori-Atsushi/koject/issues/27)
