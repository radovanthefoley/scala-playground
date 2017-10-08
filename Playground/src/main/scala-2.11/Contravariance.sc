class GarbageCan[-A] {

  // compiles because of object private scope
  private[this] var items: List[A] = List.empty

  def put(item: A): Unit = this.items :+= item

  def putAll(items: List[A]): Unit = this.items ++= items

  def itemsCount: Int = this.items.size

}

// SUBTYPING
trait Trash
class Plastic extends Trash

val g: GarbageCan[Plastic] = new GarbageCan[Trash]
// following does not compile
//val g2: GarbageCan[Trash] = new GarbageCan[Plastic]