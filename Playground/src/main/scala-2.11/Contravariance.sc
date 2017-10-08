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


trait MusicInstrument {
  val productionYear: Int
}
case class Guitar(productionYear: Int) extends MusicInstrument
case class Piano(productionYear: Int) extends MusicInstrument

val isVintage: (MusicInstrument => Boolean) = _.productionYear < 1980

//test("should filter vintage guitars") {
  // given
  val guitars: List[Guitar] = List(new Guitar(1966), new Guitar(1988))
  // when
  val vintageGuitars: List[Guitar] = guitars.filter(isVintage)
  // then
  assert(List(new Guitar(1966)) == vintageGuitars)
//}

//test("should filter vintage pianos") {
  // given
  val pianos: List[Piano] = List(new Piano(1975), new Piano(1985))
  // when
  val vintagePianos: List[Piano] = pianos.filter(isVintage)
  // then
  assert(List(new Piano(1975)) == vintagePianos)
//}

// guitars.filter needs p: (Guitar) => Boolean
// that is Function[-T,+R]
// since T is contravariant then the following applies:
// function p: (MusicInstrument) => Boolean
//    <: (is subtype of)
// function p: (Guitar) => Boolean
// and previous compiles

// be advised that in this particular case
// it is the whole function we need to care
// about, when dealing with decision
// what can we pass as a subtype,
// not the single parameters
// example: Ukulele is subtype of guitar, so first
// naive approach would expect the following to compile:

class Ukulele(productionYear: Int) extends Guitar(productionYear: Int)
val isVintage2: (Ukulele => Boolean) = _.productionYear < 1980
//guitars.filter(isVintage2)

// but that is not the case, since
// function p: (Ukulele) => Boolean
//    >: (is supertype of)
// function p: (Guitar) => Boolean
// even if Ukulele <: Guitar