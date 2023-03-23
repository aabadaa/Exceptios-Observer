# Exceptios-Observer
A library to read exceptions logs when you aren't able to reach your android studio logs
## Setup
### Step 1 Add this in your root build.gradle at the end of repositories:
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
### Step 2 Add the dependency, get the last version from [here](https://jitpack.io/#aabadaa/FLyView)
	dependencies {
	        implementation 'com.github.aabadaa:Exceptios-Observer:<version>'
	}

## Usage
Just be sure to start the service before doing anything that may cause an exception<br>
to start the service :
```kotlin
   loggerService.startLogging(context)
```
