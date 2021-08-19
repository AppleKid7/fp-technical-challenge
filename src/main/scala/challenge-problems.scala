package net.degoes

import scala.annotation.tailrec

object challenge1 {
  sealed trait Tree[+A]
  object Tree {
    case object Empty extends Tree[Nothing]
    final case class Leaf[A](value: A) extends Tree[A]
    final case class Fork[A](left: Tree[A], right: Tree[A]) extends Tree[A]
  }

  /**
    * CHALLENGE
    * 
    * Define a lazy, stack-safe iterator for `Tree[A]`.
    */
  def toIterable[A](initial: Tree[A]): Iterable[A] = 
    new Iterable[A] {
      import Tree._
      
      // Step 1: Find simplest solution that is not stack-safe
      // Step 2: Find the core recursive function here and make it
      // tail-recursive.
      // override def foreach[U](f: A => U): Unit = {
      //   def loop(node: Tree[A]): Unit =
      //     node match {
      //       case Empty =>
      //       case Leaf(value) => f(value)
      //       case Fork(left, right) =>
      //         loop(left) // not tailrecursive because after calling loop, we're doing something else.
      //         loop(right)
      //     }
      //   loop(initial)
      // }

      // override def iterator: Iterator[A] =
      //   new Iterator[A] {
      //     override def hasNext: Boolean = ???
      //     override def next(): A = ???
      //   }

      // fixed Depth-First solution:
      override def foreach[U](f: A => U): Unit = {
        @tailrec
        def loop(node: Tree[A], todo: List[Tree[A]]): Unit = { // this is O(n) where n is the number of nodes in the tree
          node match {
            case Empty =>
              todo match {
                case Nil => ()
                case node :: todo => loop(node, todo)
              }

            case Leaf(value) => 
              f(value)
              todo match {
                case Nil => ()
                case node :: todo => loop(node, todo)
              }

            case Fork(left, right) => loop(left, right :: todo)
          }
        }
        loop(initial, Nil)
      }

      override def iterator: Iterator[A] =
        new Iterator[A] {
          var _todo: List[Tree[A]] = List(initial)
          var _next: Option[A] = None

          advancedNext()

          override def hasNext: Boolean = _next.nonEmpty

          @tailrec
          def getNext(todo: List[Tree[A]]): Option[(A, List[Tree[A]])] = { // this is O(n) where n is the number of nodes in the tree
            todo match {
              case Nil => None

              case Empty :: todo => getNext(todo)

              case Leaf(value) :: todo => Some((value, todo))

              case Fork(left, right) :: todo => getNext(left :: right :: todo)
            }
          }

          def advancedNext(): Unit = {
            getNext(_todo) match {
              case None =>
                _todo = Nil
                _next = None
              case Some(a, todo) =>
                _todo = todo
                _next = Some(a)
            }
          }

          override def next(): A = {
            val a = _next.get
            advancedNext()
            a
          }
        }
    }

  // 1, 2, 3
  val tree = Tree.Fork(Tree.Leaf(1), Tree.Fork(Tree.Leaf(2), Tree.Leaf(3)))

  val iterator = toIterable(tree).iterator

  List(1, 2, 3).foreach(println(_))
  val it = List(1, 2, 3).iterator 

  it.next()

  iterator.hasNext // true 
  iterator.next()  // 1
  iterator.next()  // 2
  iterator.next()  // 3

}

object challenge2 {
  /**
    * CHALLENGE 
    * 
    * Determine if the specified strings can be laid out end to end such that the final letter
    * of one string is equal to the initial letter of the next string.
    * 
    * "foo", "bar", "orb", "rob"
    * "foo", "orb", "bar", "rob"
    * 
    * "a" -> ('a', 'a')
    * "" -> we just discard these
    * 
    * (Char, Char)
    *
    */
  def circular(strings: List[String]): Boolean = {
    simplified(strings.map(_.toList).collect {
      case head :: Nil => (head, head) 
      case head :: (tail @ (_ :: _)) =>  (head, tail.last)
    })
  }

  // private def toTuple(string: String): (Char, Char) = (string.charAt(0), string.charAt(string.length - 1))

  // we can throw away all the middle characters. 
  def simplified[A](pairs: List[(A, A)]): Boolean = {
    type Solution = List[(A, A)]

    def findSolution(current: (A, A), workingSet: List[(A, A)]): List[Solution] = {
      if (workingSet.isEmpty) List(current :: Nil)
      else {
        val target = current._2
        val (matches, remainder) = workingSet.partition(_._1 == target)

        val prefixSolutions = matches.map(m => current :: m :: Nil)

        ???
      }
    }

    pairs match {
      case current :: rest => findSolution(current, rest).nonEmpty
      case Nil => true
    }
  }
}