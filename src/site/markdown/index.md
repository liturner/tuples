# Measures

The Measures package consists of three core concepts. Units, Measures and 
Conversion. In our package, a Unit is considered a single instance of an agreed 
measure, such as a "Metre" or a "Gram". These units are generally globally 
agreed upon, we use the definitions as per [UCUM](https://ucum.org/). A Measure
is some quantity of a Unit. For example where "cm" is a Unit, "1.5cm" is a 
Measure. It is possible to convert between Measures and Units. For example, 
100cm == 1m.

Our toolset is expandable. You can define new Units and create conversions 
between them. 

Our toolset enables accuracy. You can define Conversions in multiple methods.

Our toolset supports edge cases. You can solve problems with limitations of the 
Java language by providing complex implementations of conversion functions.

# Supported Units

Check out the Unit class in our [Javadoc](apidocs/de.turnertech.measures/de/turnertech/measures/Unit.html).
Every supported unit is listed there as a public static constant.

# Examples

**Convert from metres to kilometres using the Measures API:**

```java
Measure myMetres = new Measure(Unit.METRE, 1.85);
Measure myCentimetres = myMetres.convertTo(Unit.CENTIMETRE);
myCentimetres.getQuantity(); // 185.0
```

<hr/>

**Create a new Unit and convert to it using the Unit API:**

```java
// A Unit is defined with a "Base Unit" and conversion functions too and from 
// your Unit definition and the base unit.
Unit MILLIMETRE = new Unit("mm", Unit.METRE, (millimetre) -> millimetre * 0.001, (metre) -> metre * 1000.0);
Measure myMillimetres = new Measure(MILLIMETRE, 1337);

// This conversion is possible as the custom unit shares the same base unit.
Measure myCentimetres = myMillimetres.convertTo(Unit.CENTIMETRE);
myCentimetres.getQuantity(); // 133.7
```

<hr/>

**Create an optimised conversion between units to prevent issues with java double
limitations:**

By default, Unit Conversions go to the base unit. This is a problem in the case
below, as lotsOfKilometers.convertTo(Unit.METRE) would have to return 
Double.POSITIVE_INFINITY due to an overrun of the Java double type. To resolve 
this, we can add a specialised conversion which goes directly from kilometres to 
nautical miles.

```java
Measure lotsOfKilometers = new Measure(Unit.KILOMETER, Double.MAX_VALUE);
lotsOfKilometers.convertTo(Unit.NAUTICAL_MILE); // Throws an exception.

UnitConverter.putScalar(Unit.NAUTICAL_MILE, Unit.KILOMETRE, 1.852);
lotsOfKilometers.convertTo(Unit.NAUTICAL_MILE); // Succeeds
```

While the above example is an extreme case, it highlights the same limitation
which can cause innacuracies to be introduced. Even with a small quantity of
kilometers, converting first to metres will essentially erase the last three
digits of your double. The next conversion to nautical mile is then not as 
accurate.

Where possible, there should allways be an optimised conversion scalar between
units.

<hr/>

**Add a completely custom conversion with any logic and math you desire.**

There is also a putFunction feature for more complicated conversions. This is
intended for conversions using which must ensure that the limitation of doubles 
on computers is overcome.

```java
Unit MY_CRAZY_UNIT = new Unit();
DoubleUnaryOperator myCrazyFunction = (seconds) -> {
    if(seconds < 0) {
        return 0;
    }
    else if(seconds % 7 == 0) {
        return 21;
    }
    else {
        return seconds * System.currentTimeMillis();
    }
};
UnitConverter.putFunction(Unit.SECOND, MY_CRAZY_UNIT, myCrazyFunction);
```