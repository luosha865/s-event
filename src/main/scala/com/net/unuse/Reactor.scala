package com.net.unuse

/**
 * Created by tianhaowei on 15-6-2.
 */

import com.net.core.NioSelector

class Reactor extends Runnable {

  val selector = new NioSelector
  val timeout = 60
  var running = true

  def callLater(time : Int,body: => Unit): Unit ={

  }

  def run(): Unit = {
    while(running){
      selector.selectOnce(timeout)
    }
  }

}

