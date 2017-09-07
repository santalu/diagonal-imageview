# Diagonal ImageView

[![](https://jitpack.io/v/santalu/diagonal-imageview.svg)](https://jitpack.io/#santalu/diagonal-imageview) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Diagonal%20ImageView-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6040)

A simple imageview which allows you to create diagonal cut views easily

## Samples

<table >
  <tr>
    <td align="left"><img src="https://github.com/santalu/diagonal-imageview/blob/master/screens/rv.png"/></td>
    <td align="right"><img src="https://github.com/santalu/diagonal-imageview/blob/master/screens/cv.png"/></td>
  </tr>
  <tr>
    <td align="left"><img src="https://github.com/santalu/diagonal-imageview/blob/master/screens/gl.png"/></td>
    <td align="right"><img src="https://github.com/santalu/diagonal-imageview/blob/master/screens/ct.png"/></td>
  </tr>
</table>

## Usage

### Gradle
```
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```
```
dependencies {
  compile 'com.github.santalu:diagonal-imageview:1.0.1'
}
```

### XML
```xml
<com.santalu.diagonalimageview.DiagonalImageView
    android:id="@+id/image"
    android:layout_width="120dp"
    android:layout_height="120dp"
    android:scaleType="centerCrop"
    android:src="@drawable/demo"
    app:di_borderColor="#FF5722"
    app:di_borderEnabled="false"
    app:di_direction="bottom"
    app:di_overlap="56dp"
    app:di_position="right"/>
```

## Attributes

| Name        | Description           | Value  |
| ------------- |:-------------:| -----:|
| di_position      | position of clip path | top, right, bottom, left |
| di_direction     | direction of clip path      | top, right, bottom, left |
| di_overlap | size of clip path      |   dimen |
| di_borderSize | size of border      |   dimen |
| di_borderColor | color of border      |   color |
| di_borderEnabled | visibility of border      |   true, false |

## Position & Direction Relation

| Position        | Description           | 
| ------------- |:-------------:|
| TOP      | LEFT /  RIGHT | 
| BOTTOM     | LEFT /  RIGHT    | 
| LEFT | TOP  /  BOTTOM     |   
| RIGHT | TOP  /  BOTTOM     |  

## Notes

* Set position NONE to disable masking

## License
```
Copyright 2017 Fatih Santalu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```




