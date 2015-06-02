package com.net.unuse

/**
 * Created by tianhaowei on 15-5-31.
 */

import java.nio.channels.spi.SelectorProvider

abstract class Loop extends Thread {


  val selector = SelectorProvider.provider().openSelector()
  var isrunning = false
  var maxSleep :Long = 0
  protected val loopThread : Thread = Thread.currentThread()

  override def run() = {
    var numSelected = 0
    while(isrunning){
      try{
        numSelected = this.selector.select(this.maxSleep)
        if (this.isrunning){
          this.maxSleep = 0
          go()
        }
      }catch {
        case e  :Exception => e.printStackTrace()
      }
    }
  }

  def go()

  def stopLoop() ={
    this.isrunning = false
    try{
      this.selector.close()
    }catch {
      case e :Exception => e.printStackTrace()
    }
  }

}
