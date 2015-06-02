package com.net.core

/**
 * Created by tianhaowei on 15-6-2.
 */

import java.nio.channels.SelectableChannel

import rx.lang.scala.Observable

trait SelectService {
def select(channel: SelectableChannel, interestOps: Int): Observable[Event]
}

case class Event(service: SelectService, channel: SelectableChannel,flag : String)
