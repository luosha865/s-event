package com.net.core

/**
 * Created by tianhaowei on 15-5-31.
 */

import scala.collection.mutable.{ListBuffer, Queue}


class TimeoutLoop extends Thread {

  protected val timeouts : Queue[Elem] = null
  protected val newTimeouts :ListBuffer[Elem] =null
  var curtime : Long = 0


  protected def go() ={
    this.curtime = System.nanoTime()
    handleTimeouts()
    handleNewTimeouts()
  }

  def handleTimeouts() : Int ={
    var count :Int = 0
    while(this.timeouts.size > 0){
      var timeout = this.timeouts.front
      if (this.curtime >= timeout.time) {
        try {
          //timeout.ev.go(this)
        } catch {
          case e : Exception =>
        }
        this.timeouts.dequeue()
        count = count + 1
        if (timeout.interval) { // return intervals to queue
          //timeout.time = this.curtime + (timeout.timeout*1000000)
          this.timeouts.enqueue(timeout)
        }
      } else {
      }
    }
    count
  }

  def handleNewTimeouts() ={

  }

  case class Elem(id:Int,time : Long,timeout:Long,interval:Boolean)
  def ElemOrdering = new Ordering[Elem] {
    def compare(a : Elem, b : Elem) = a.time.compare(b.time)
  }


}
