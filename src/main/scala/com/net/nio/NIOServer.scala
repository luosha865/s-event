package com.net.nio

import java.net.InetAddress

object NIOServer {
  def main(args: Array[String]) {
    val hostAddr: InetAddress = InetAddress.getByName("localhost")
    val port = 9090
    val server = new NIOServer(hostAddr, port)
    server.start()
  }
}

class NIOServer(hostAddr: InetAddress, port: Int) {
  val selector = new NioSelector
  val listener = new NioListener(selector, hostAddr, port)

  def start() {
    listener.start(true)
    selector.run()
  }
}