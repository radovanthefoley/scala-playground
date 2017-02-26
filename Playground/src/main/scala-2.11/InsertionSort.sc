// N * N worst case

def isort(xs: List[Int]): List[Int] = xs match {
  case List() => List()
  case y :: ys => insert(y, isort(ys))
}

def insert(x: Int, xs: List[Int]): List[Int] = xs match {
  case List() => List(x)
  case y :: ys => {
    if(y >= x) x :: xs
    else y :: insert(x, ys)
  }
}

isort(List(3,1,6,9,2))
isort(List(5,4,3,2,1))