# Diagonal ImageView

[![](https://jitpack.io/v/santalu/diagonal-imageview.svg)](https://jitpack.io/#santalu/diagonal-imageview) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Diagonal%20ImageView-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6040) [![](https://img.shields.io/badge/AndroidWeekly-%23278-blue.svg)](http://androidweekly.net/issues/issue-278) [![Build Status](https://travis-ci.org/santalu/diagonal-imageview.svg?branch=master)](https://travis-ci.org/santalu/diagonal-imageview)

A simple imageview which allows you to create diagonal cut views easily

## Samples

<table >
  <tr>
    <td align="left"><img src="https://github.com/santalu/diagonal-imageview/blob/master/media/rv.webp"/></td>
    <td align="right"><img src="https://github.com/santalu/diagonal-imageview/blob/master/media/cv.webp"/></td>
    <td align="left"><img src="https://github.com/santalu/diagonal-imageview/blob/master/media/gl.webp"/></td>
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
  implementation 'com.github.santalu:diagonal-imageview:1.1.0'
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
    app:di_borderColor="@color/colorAccent"
    app:di_borderEnabled="true"
    app:di_borderSize="8dp"
    app:di_distance="56dp"
    app:di_end="left"
    app:di_start="bottom"/>
```

## Attributes

| Name              | Value |
| -------------     | -----:|
| di_start          | top, right, bottom, left |
| di_end            | top, right, bottom, left |
| di_distance       | dimen |
| di_borderSize     | dimen |
| di_borderColor    | color |
| di_borderEnabled  | true, false |

## Notes

* Set start `NONE` to disable masking
* After changing attributes call `invalidate()` or `postInvalidate()` to immediately apply changes

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




