# FormattedEditText

The FormattedEditText groups numbers into sets of three digits, automatically insert a dot after every third digit and let you type maximum 2 digits after comma.

## Let's Get Started

First, add the dependency below to your **module**'s `build.gradle` file:

```gradle
dependencies {
    implementation "com.github.berkayertan:FormattedEditText:1.0.0"
}
```

## Usage
Add dependency below to your `settings.gradle` file:

```gradle
dependencyResolutionManagement {
     maven { url = uri("https://jitpack.io") }
}
```

#### FormattedEditText in layout

```gradle
            <com.github.berkayertan.FormattedEditText
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:digits=",0123456789"
                android:inputType="numberDecimal"/>
```

<div align="center"> <!-- Aligning the content within a div -->
  <img src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExZml6MjEwM2h0ZnFjampnZG50NWNmc2h1dGR5Z2Fza3lwN3lnNWJjcCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/83rlCMct9orABCSq1V/giphy.gif" alt="FormattedEditText GIF">
</div>
