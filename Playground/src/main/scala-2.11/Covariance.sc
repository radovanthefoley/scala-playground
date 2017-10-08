class SoftDrink
class Cola extends SoftDrink
class TonicWater extends SoftDrink

class VendingMachine[+A](val currentItem: Option[A], items: List[A]) {

  def this(items: List[A]) = this(None, items)

  def dispenseNext(): VendingMachine[A] =
    items match {
      case Nil => {
        if (currentItem.isDefined)
          new VendingMachine(None, Nil)
        else
          this
      }
      case t :: ts => {
        new VendingMachine(Some(t), ts)
      }
    }

  def addAll[B >: A](newItems: List[B]): VendingMachine[B] =
    new VendingMachine(items ++ newItems)

}

val colasVM: VendingMachine[Cola] =
  new VendingMachine(List(new Cola, new Cola))

// IMPORTANT !!!
// following would not be possible in java, only scala
// offers covariant param as method argument (with given
// restriction), same goes for contravariance...

val softDrinksVM: VendingMachine[SoftDrink] =
  colasVM.addAll(List(new TonicWater))

// covariant params can be used as method return types
// only in java

//List<? extends Number> numCovariant = new ArrayList<Integer>();
//numCovariant.add(3);      //compilation error
//Number num = numCovariant.get(0);

// contravariant parameters can be meaningfully used
// as method arguments only in java

//List<? super Integer> numContravariant = new ArrayList<Number>();
//numContravariant.add(3);
//Object obj = numContravariant.get(0);    //only as Object, impossible to infer other type


// java offers use-site variance only (as seen above), there
// is no declaration-site variance as in scala

// scala use-site variance syntax:

def install(softDrinkVM: VendingMachine[_ <: SoftDrink]): Unit = {
  // Installs soft drink vending machine
}


// SUBTYPING
val v: VendingMachine[SoftDrink]
  = new VendingMachine[Cola](None, Nil)

// following wil not compile
//val v2: VendingMachine[Cola]
//= new VendingMachine[SoftDrink](None, Nil)