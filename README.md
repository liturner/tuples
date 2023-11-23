# TurnerTech Tuples

A basic java 9+ Module implementing Tuples in both a Typed, and Untyped manner. Zero dependencies, uses Java Modules (as in has a module-info.java).

This implementation treats a Tuple as a Java Collection, applying the rules and restrictions of Tuples as per [my intensive research](https://en.wikipedia.org/wiki/Tuple). The aim is to provide a balance between the common need for a basic typed pair/triple object (etc. for return types), and a Tuple class which follows the rules of immutability etc.

# See Also

- [javatuples](https://github.com/javatuples/javatuples), another very mature tuples implementation. Our implementation specifics differ slightly so just choose the implementation which suits you!