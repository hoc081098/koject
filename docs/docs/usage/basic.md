# Start Koject
To start using Koject, simply add the `@Provides` annotation to the classes you want to make available for injection.

Koject uses the [primary constructor](https://kotlinlang.org/docs/classes.html#constructors) to instantiate objects, so it's important to make sure that all parameter types in the primary constructor are annotated with `@Provides`.

```kotlin
@Provides
class Repository

@Provides
class Controller(
    private val repository: Repository
)
```

Once you have called `Koject.start()`, you can obtain an instance of a class by using the inject method.

```kotlin
fun main() {
    Koject.start()

    val controller = inject<Controller>()
}
```

In this case, `inject<Controller>()` will return an instance of the `Controller` class that has been instantiated by Koject using its primary constructor and any `@Provides` annotated dependencies. 

## Provide from functions
You can use the `@Provides` annotation in top-level functions to provide any types. 
This can be particularly useful for providing dependencies that are shared across multiple modules.

```kotlin
@Provides
fun provideDBConfig(): DBConfig {
    return DBConfig.Builder().build()
}

@Provides
fun provideDB(dbConfig: DBConfig): DB {
    return DB.create(dbConfig)
}
```

You can also provide types by using functions defined in objects or companion objects. 

```kotlin
object DBFactory {
    @Provides
    fun create(): DB {
        return DB.create()
    }
}
```
```kotlin
class DB {
    companion object {
        @Provides
        fun create(): DB {
            return DB()
        }
    }
}
```

## Singleton Scope
You can use the `@Singleton` annotation to indicate that an instance should be created only once and reused throughout the application.
This can be particularly useful for dependencies that are expensive to create or that need to be shared across multiple classes.

```kotlin
@Singleton
@Provides
class Api

@Singleton
@Provides
fun provideDB(): DB {
    return DB.create()
}

@Singleton
@Provids
class Repository(
    private val api: Api,
    private val db: DB,
)
```

In this case, all three classes (`Api`, `DB`, and `Repository`) are annotated with `@Singleton`, which means that Koject will create only one instance of each and reuse them throughout the application.

It's important to note that you can't inject a normally-scoped type into a singleton-scoped type.

```kotlin
@Provides
class NormalScope

@Singleton
@Provides
class SingletonScope(
    // Cannot inject NormalScope into SingletonScope!
    private val normal: NormalScope
)
```