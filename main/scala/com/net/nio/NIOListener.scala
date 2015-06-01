package com.net.nio

import java.net.InetAddress
import java.net.InetSocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel

import scala.util.continuations.reset
import scala.util.continuations.shift
import scala.util.continuations.suspendable

class NioListener(selector: NioSelector, hostAddr: InetAddress, port: Int) {

  val serverChannel = ServerSocketChannel.open()
  serverChannel.configureBlocking(false)
  val isa = new InetSocketAddress(hostAddr, port)
  serverChannel.socket.bind(isa)

  def start(continueListening: => Boolean): Unit = {
    reset {
      while (continueListening) {
        val socket = accept()
        NioConnection.newConnection(selector, socket)
      }
    }
  }

  private def accept(): SocketChannel @suspendable = {
    //k(conn) will be called as the call back function
    //fix type of k
    shift { k : (SocketChannel => SocketChannel) =>
      selector.register(serverChannel, SelectionKey.OP_ACCEPT, {
        val conn = serverChannel.accept()
        conn.configureBlocking(false)
        k(conn)
      })
    }
  }
}