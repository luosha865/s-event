package com.net.core

import java.net.InetSocketAddress
import rx.lang.scala.{Observable, Observer}
import java.nio.ByteBuffer
import java.nio.channels.SocketChannel

object NIOServer {
  def main(args: Array[String]) {
    val port = 9090
    val server = new NIOServer(port)
    server.start()
  }
}

class NIOServer(port: Int) {
  val selector = new NioSelector
  NioListener(selector,new InetSocketAddress(port)){
    observable =>
      observable.subscribe(new Observer[Event] {
        override def onNext(event: Event) {
          event.channel.asInstanceOf[SocketChannel].write(ByteBuffer.wrap("Hello, World!\n".getBytes("UTF-8")))
          event.channel.close()
        }
      })
  }
  def start() {
    selector.run()
  }
}