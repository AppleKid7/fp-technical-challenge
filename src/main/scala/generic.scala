object generic {
  // A VALUE is information, stored as bits, encoded in a computer's memory (RAM), which exists at runtime, for purposes of
  // computation (including communication).

  // A TYPE is a description of a set of value, informed by a language's type system, which exists at compile time, inside
  // the compiler, for purposes of ensuring that programs don't "go wrong" at runtime.

  // A program being sound vs unsound. A good compiler will not allow you to compile an UNSOUND program, because an
  // UNSOUND program might blow up at runtime. This doesn't always happen in C, sometimes things just get corrupted.
  // But on the JVM it always blows up at some point down the line.

  // every type ascription you make is trying to prove to the compiler that a statement is correct.

  // List is not a type. List[String] is a type.
  // List is a type constructor.

  // kind annotation language
  // * = { x | x is a type }
  //   = { String, Int, Boolean, List[String], List[Int], ... }
  // List does not belong in this set, but List[String], List[Int], List[Boolean], etc, does

  // * => { the set of all types }
  // * => * = { x | is takes in a parameter that is a type }
  //        = { List, Option, Task, Future, ... }
  // there is also
  // (* => *) => * 
  trait Functor[F[_]]
  // essentially a type constructor is a type level function!


  // subtyping
  // extends used to denote relationship between Dog and Animal
  trait Animal {
    def eat(): Unit = println("eat food")
    def name(): String
  }
  trait Dog extends Animal {
    override def eat() = println("eat dog food!")
    override def name() = "Dog"
    def bark() = ???
  }
  val scotty = new Dog {
    override def name() = "scotty"
  }
  def produce(): Animal = scotty
  produce() // <--- widening (going from dog to Animal)
  // NOTE:
  // produce().bark() won't work!!!!
  // produce().eat() will print "eat dog food!" despite being typed Animal


  // Question: UIO(throw new Error) // this is actually ZIO.succeed(throw new Error)
  //  what is Chunk in ZIO?
  // it is a safe array for FP. Like array doesn't box primitive.
  //  effectful tail recursion?

  
}
