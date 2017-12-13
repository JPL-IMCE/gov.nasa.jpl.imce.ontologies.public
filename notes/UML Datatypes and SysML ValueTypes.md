- `SysML:ValueType` is now an OML Aspect with two specializations:
  - `SysML:ScalarValueType` adds support for specifying metrological characteristics:
     - `SysML:a_scalarValueType_unit` (OML Unreified Property)
     - `SysML:a_scalarValueType_quantityKind` (OML Unreified Property)
     - several OML EntityScalarDatatypeProperties for all compatible datatypes in the OWL2-DL Datatype map
       (`SysML:real.value`, `SysML:rational.value`, `SysML:decimal.value`, ...)
  - `SysML:StructuredValueType` This OML Aspect facilitates defining data structures but no unit/quantity kind.
     (unit and quantity kind makes sense only for scalar value types)
     
- `SysML:A_valueType_quantityKind` is now `SysML:a_scalarValueType_quantityKind` (i.e., it is no longer reified)
  
  `QUDV:A_quantityKind_measurementUnit` is deleted, it is subsumed by `SysML:a_scalarValueType_quantityKind`
  `QUDV:A_noQuantityKind_prefixedUnit` is deleted, it is unecessary.

- `SysML:A_valueType_unit` is now `SysML:a_scalarValueType_unit` (i.e., it is no longer reified)

- `SysML:A_quantityKind_unit` is now `SysML:a_quantityKind_unit` (i.e., it is no longer reified)

- `QUDV:Rational` is replaced by `owl:rational`

  This means refactoring the following into `owl:DatatypeProperty` with range `owl:rational`:
  
  - `QUDV:Prefix.factor`
  - `QUDV:QuantityKindFactor.exponent`
  - `QUDV:UnitFactor.exponent`
  
  This means deleting the following:
  - `QUDV:Rational.denominator`
  - `QUDV:Rational.numerator`
  
- `QUDV:Number` is replaced by `owl:real`

  This means refactoring the following into `owl:DatatypeProperty` with range `owl:real`:
  - `QUDV:AffineConversionUnit.factor`
  - `QUDV:AffineConversionUnit.offset`
  - `QUDV:LinearConversionUnit.factor`