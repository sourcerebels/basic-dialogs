
# basic-dialogs

Helper classes that I use in my projects to display simple Android dialogs:

* AlertDialogFragment
* ConfirmationDialogFragment
* ProgressDialogFragment

## Download

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency at your module's build.gradle:

```groovy
dependencies {
    compile 'com.github.sourcerebels:basic-dialogs:1.0.RC1'
}
```