package com.net.core

import java.nio.channels.{SelectableChannel, SelectionKey}
import java.nio.channels.spi.SelectorProvider

import rx.lang.scala.{Observable, Observer, Subscription}

import scala.collection.JavaConversions

/** Handle nio channel selection. */
class NioSelector extends SelectService{ //with Runnable

  val selector = SelectorProvider.provider.openSelector()
  var running = true

  def select(channel: SelectableChannel,op: Int): Observable[Event] ={
    val key = channel.register(selector,op)
    Observable.create(
      observer => {
        val oldObserver = Option(key.attach(observer).asInstanceOf[Observer[Event]])
        oldObserver.foreach(_.onCompleted())
        new Subscription{
          override def unsubscribe() {
            key.cancel()
          }
        }
      })
  }

  def run() {
    selectLoop(running)
  }

  def selectLoop(continueProcessing: => Boolean) {
    while (continueProcessing) {
      selectOnce(0)
    }
  }

  def selectOnce(timeout: Long) {
    selector.select(timeout)
    val set: java.util.Set[SelectionKey] = selector.selectedKeys
    val iter = set.iterator()
    //val iter = JavaConversions.asScalaSet(jKeys).iterator
    selector.selectedKeys.clear()
    while (iter.hasNext){
      val key = iter.next()
      val observer = Option(key.attachment().asInstanceOf[Observer[Event]])
      if (key.isAcceptable) {
        observer.foreach(_.onNext(AcceptEvent(this, key.channel())))
      }
      if (key.isConnectable) {
        observer.foreach(_.onNext(ConnectEvent(this, key.channel())))
      }
      if (key.isReadable) {
        observer.foreach(_.onNext(ReadEvent(this, key.channel())))
      }
      if (key.isWritable) {
        observer.foreach(_.onNext(WriteEvent(this, key.channel())))
      }
    }
  }
}