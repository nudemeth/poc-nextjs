package nudemeth.poc.ordering.util.event

import scala.collection.mutable.ArrayBuffer

class Event[T]() {
  private var invocationList: ArrayBuffer[(AnyRef, T) => Unit] = new ArrayBuffer[(AnyRef, T) => Unit]()

  def apply(caller: AnyRef, args: T) {
    for (invoker <- invocationList) {
      invoker(caller, args)
    }
  }

  def +=(invoker: (AnyRef, T) => Unit) {
    invocationList += invoker
  }

  def -=(invoker: (AnyRef, T) => Unit) {
    invocationList.remove(invocationList.indexOf(invoker))
  }
}
