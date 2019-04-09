# λJcommon - Changelog

## 1.0.2

 - Added `File LambdaSystem::getUserDirectory()`.
 - Removed `ReturnAs`.

# 1.3.0

 - Added configuration API
 - Added `Config`
 - Added `FileConfig`
 - Added `JsonConfig`
 - Added `VirtualJsonConfig`
 - Added `LambdaConstants.GSON`
 - Added `LambdaConstants.GSON_PRETTY`
 - Added `LambdaConstants.JSON_PARSER`
 
## 1.3.1

 - Added `ResourcesManager` with getResource(FromJar), saveResourceFromJava, etc...

## 1.3.2

 - Added getResource with URL argument and InputStream argument in `ResourcesManager`

## 1.3.3

 - Fix the getMethod in LambdaReflection (added setAccessible(true)).
 
## 1.3.4

 - Added `Optional::fromJava(java.util.Optional)`
 
## 1.3.5

 - Fix `JsonConfig#set` and `VirtualJsonConfig#set`

## 1.4.0 - Update to Java 10

 - Add `org.aperlambda.lambdacommon.Optional#stream`
 - Removes `Pair::fromJavaFX`
 - Add `module-info`
 - Use `var` keyword.
 - Gradle: Use of the experimental jigsaw plugin.
 
### 1.4.1

 - Add `LambdaReflection::getFirstFieldOfType`
 - Add `LambdaReflection::getLastFieldOfType`
 
### 1.4.2

 - Renamed `Position#getDimension()` to `Position#getDimensions()`.
 
### 1.4.4

 - Documentation time!
 - Add `Optional#filter(Predicate)`.
 
### 1.4.5

 - Add `PairFunction`.
 - Add mapping functions to Pair.
 
### 1.4.6

 - Add `ResourceNameable`
 
### 1.4.7

 - Add `--add-opens` in compiler arguments to allow the access to `Field.modifiers`.
 
### 1.4.8

 - Undo changes of `1.4.7`
 - Disables the modifiers change in `LambdaReflection#setupField()`: now we can't edit fields with the `final` modifier.
 
### 1.4.9

 - Removes the `Optional` and `OptionalString` because it already exists in Java...
 
### 1.4.10

 - Add `PropertiesConfig`
 - Add Json serializer and deserializer for `Pair`, `ResourceName`
 - Move `JsonConfig` and `VirtualJsonConfig`
 - Add `C getConfig()` in `Config`, C is the type of the root config.
 
## 1.5.0 - Caches!

 - Add `Cache`
 - Add `TimedCache`
 - Add `ChachedObject`
 
### 1.5.3

 - Add keys to caching system.

## 1.6.0 - Breaking update!

 - Changed the style.
 - Switched to snake_case naming.
 - Updated licence.
 
### 1.6.1
 
 - Renamed `ResourceNameable::get_ressource_name()` to `ResourceNameable::get_resource_name()`.
