# Diagonal ImageView

[![](https://jitpack.io/v/santalu/diagonal-imageview.svg)](https://jitpack.io/#santalu/diagonal-imageview)

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
  compile 'com.github.santalu:diagonal-imageview:1.0'
}
```

### XML
```
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

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details





