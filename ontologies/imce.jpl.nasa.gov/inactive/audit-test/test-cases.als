module ontologies/imce_jpl_nasa_gov/test/test_cases

sig ReificationClass { }

sig ObjectProperty {
	reificationClass: lone ReificationClass,
	directMapping: lone ObjectProperty
}

pred bad_both_reified_and_mapped(p: ObjectProperty) {
	one p.reificationClass and one p.directMapping
}