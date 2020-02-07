package com.santalu.diagonalimageview

/**
 * Created by fatih.santalu on 7/24/2018.
 */

enum class Direction {
  NONE,
  LEFT,
  TOP,
  RIGHT,
  BOTTOM;

  companion object {

    fun get(ordinal: Int): Direction = values().find { it.ordinal == ordinal } ?: NONE
  }
}