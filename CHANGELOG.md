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

# 1.4.0 - Update to Java 10

 - Add `org.aperlambda.lambdacommon.Optional#stream`
 - Removes `Pair::fromJavaFX`
 - Add `module-info`
 - Use `var` keyword.
 - Gradle: Use of the experimental jigsaw plugin.
 
# 1.4.1

 - Add `LambdaReflection::getFirstFieldOfType`
 - Add `LambdaReflection::getLastFieldOfType`
 
# 1.4.2

 - Renamed `Position#getDimension()` to `Position#getDimensions()`.
 
# 1.4.4

 - Documentation time!
 - Add `Optional#filter(Predicate)`.
 
# 1.4.5

 - Add `PairFunction`.
 - Add mapping functions to Pair.
 
# 1.4.6

 - Add `ResourceNameable`