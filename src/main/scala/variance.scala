package net.zio

object tutoring {
  // Invariance:      A != B ==> F[A] ~!~ F[B]
  // Covariance:      A <: B ==> F[A] <: F[B]
  // Contravariance:  A <: B ==> F[A] >: F[B]
  
  sealed trait List[+A] { self => 
    def :: [A1 >: A](a1: A1): List[A1] = tutoring.::(a1, self)

    def map[B](f: A => B): List[B] = 
      self match {
        case Nil() => Nil() 
        case tutoring.::(head, tail) => f(head) :: tail.map(f)
      }

    def foreach(f: A => Unit): Unit = self match {
      case Nil() => () 
      case tutoring.::(head, tail) => f(head); tail.foreach(f)
    }

    def widen[B >: A]: List[B] = self.map(a => a : B)
  }
  object List {
    def apply[A](as: A*): List[A] = 
      as.headOption match {
        case Some(a) => a :: List(as.tail: _*)
        case None => Nil()
      }
  }
  final case class Nil[A]() extends List[A]
  final case class ::[A](head: A, tail: List[A]) extends List[A]

  trait Animal {
    def pet: Unit 
  }
  trait Dog extends Animal {
    def pet = println("[drool]")

    def bark = println("Woof!")
  }
  trait Cat extends Animal {
    def pet = println("[purr]")

    def meow = println("Meow!")
  }

  def petAnimal(animal: Animal): Unit = 
    animal.pet 

  petAnimal(new Dog{}: Dog)
  petAnimal(new Cat{}: Cat)
  
  trait Scotty extends Dog 
  val scotty: Scotty = new Scotty{}
  

  def petAllAnimals(list: List[Animal]): Unit = 
    list.foreach(_.pet)

  val dogs = List(new Dog{}, new Dog{}, new Dog{})
  val cats = List(new Cat{}, new Cat{}, new Cat{})

  petAllAnimals(dogs)

  new Cat{} :: dogs 

  // A == B ==> A <: B && B <: A

  // INVARIANCE: 
  // A =!= B ==> List[A] <!< List[B] && List[B] <!< List[A]

  // COVARIANCE: A <: B ==> List[A] <: List[B]
  // Dog <: Animal ==> List[Dog] <: List[Animal]
  
  trait Hotel[-A] {
    def book(guest: A): Unit
    
    def narrow[A1 <: A]: Hotel[A1] = this
  }
  
  class DogHotel extends Hotel[Dog] {
    def book(guest: Dog): Unit = println(s"Booking the dog ${guest} at hotel")
  }
  object DogHotel extends DogHotel
  
  DogHotel.book(scotty) // Expects: Dog, Gets: Scotty. Compiler: OK!!!!
  
  def bookScotty(hotel: Hotel[Scotty]) = 
    hotel.book(scotty)
  
  // Contravariant: Scotty <: Dog ==> Hotel[Dog] <: Hotel[Scotty]
  
  bookScotty(DogHotel)
  
  
  
  
}