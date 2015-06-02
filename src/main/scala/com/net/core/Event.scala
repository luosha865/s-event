package com.net.core

/**
 * Created by tianhaowei on 15-6-2.
 */

import java.nio.channels.SelectableChannel

import rx.lang.scala.Observable

trait SelectService {
def select(channel: SelectableChannel, interestOps: Int): Observable[Event]
}

sealed trait Event {
  val channel: SelectableChannel
  val service: SelectService
}
case class ReadEvent(service: SelectService, channel: SelectableChannel) extends Event
case class WriteEvent(service: SelectService, channel: SelectableChannel) extends Event
case class ConnectEvent(service: SelectService, channel: SelectableChannel) extends Event
case class AcceptEvent(service: SelectService, channel: SelectableChannel) extends Event
