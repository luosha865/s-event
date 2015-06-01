package com.net.core

/**
 * Created by tianhaowei on 15-5-31.
 */

import java.nio.channels.Selector
import java.nio.channels.spi.SelectorProvider

class IOLoop {
  var start = false
  val selector = Selector.open()

  def run() ={
    while(start){
      while (this.start) {
        try {
          val numSelected = this.selector.select()
          val selectedKeys = selector.selectedKeys()
          val keyIterator = selectedKeys.iterator()
          while(keyIterator.hasNext()) {
            val key = keyIterator.next()
          }

        } catch {
          case e: Exception =>
        }
      }
    }
  }
}
