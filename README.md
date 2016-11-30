# Android Chip Interface
===
[ ![Download](https://api.bintray.com/packages/edsilfer/maven/chip-interface/images/download.svg) ](https://bintray.com/edsilfer/maven/chip-interface/_latestVersion) - **MIN API 16**

Introduction
-
>Chips represent complex entities in small blocks, such as a contact.
>
> Material Design Manifest - Chips _https://material.google.com/components/chips.html_



How does it work?
-
Android Chip Interface was developed using [Kotlin language](https://kotlinlang.org/). Kotlin is free to use and owned by [Jet Brains](https://www.jetbrains.com/). It adds a lot of cool features, boosting your productiveness while keeping everythying **100% compatible with Java.** 

_For details about technical implementation of this library please refer to the source code._


- <a name="step1">**Step 01: import the module**

Add the library module dependency to your project:
```groovy
compile 'br.com.edsilfer.android:chip-interface:1.0.0'
```

- <a name="step2">**Step 02: add `br.com.edsilfer.android.chipinterface.presenter.ChipEditText` to your layout file**

```xml
  <br.com.edsilfer.android.chipinterface.presenter.ChipEditText
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:textColor="@color/colorAccent" />
```

- <a name="step3">**Step 03: make your POJO extends `Chip` and override the abstract methods**

In order to render the chip correctly, Android Chip Interface requires that the complex object that will have its representation converted into a chip to extend the `Chip` class and implement its abstract methods:

```kotlin
abstract class Chip() {

    companion object {
        private var objCount = -1.toDouble()
    }

    var chipId = 0.toDouble()
        private set

    init {
        chipId = objCount++
    }

    abstract fun getHeader(): String

    abstract fun getSubheader(): String

    abstract fun getThumbnail(): String
}
``` 

- <a name="step4">**Step 04: use `ChipControl` interface to add and remove chips**

`ChipEditTex` implements `ChipControl`, so you can retrieve it inside your code and call the methods:
- `setChipStyle (style : ChipPalette)`: before using the methods below you must call this method in order to specify which design Android Chip Interface will use to render. You may customize your own layout or use some preset provided in `Presets` class:

<p align="center">
  <img src="showcase/ss_layout_explanation.png" align="center" width=450>
  <br /><br />
  <i><b>Figure 01:</b> Android Chip layout explanation</i>
</p>

- `addChip(chip: Chip, replaceable : String)`: adds a chip inside `ChipEditTex`. `replaceable` is the typed text existent on `ChipEditTex` that will be replaced by the chip; 

- `removeChip(chip: Chip)`: removes the prev priviously inserted chip;

    fun setChipStyle (style : ChipPalette)