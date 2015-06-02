package com.net.core

import java.net.InetSocketAddress
import java.nio.channels.{SelectionKey, ServerSocketChannel}

import com.net.core.NioListener.ClientHandler
import rx.lang.scala.{Observable, Observer}

object NioListener {

  type ClientHandler = (Observable[Event]) => Unit
  def apply(selector: NioSelector, address: InetSocketAddress, backlog: Int = 0)(factory: ClientHandler): NioListener = {
    val channel = ServerSocketChannel.open().bind(address, backlog)
    new NioListener(selector, channel, factory)
  }

}

class NioListener(selector: NioSelector, channel: ServerSocketChannel,clientPipeline: ClientHandler) {
  channel.configureBlocking(false)
  private val acceptObserver = new Observer[Event] {
    override def onNext(event: Event) {
      val channel = event.channel.asInstanceOf[ServerSocketChannel].accept()
      channel.configureBlocking(false)
      val observable = event.service.select(channel, SelectionKey.OP_READ)
      observable.subscribe()
      clientPipeline(observable)
    }
  }
  val subscription = selector.select(channel, SelectionKey.OP_ACCEPT).subscribe(acceptObserver)
}