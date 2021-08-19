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

  // * => * = { x | is takes in a parameter that is a type }
  //        = { List, Option, Task, Future, ... }
  // essentially a type constructor is a type level function!
}
