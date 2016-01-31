package functional.tests

class ThingHelper {

    Thing create(String name) {
        Thing.withTransaction {
            def t = new Thing(name: name)
            t.save()
            t
        }
    }

    Thing find(String name) {
        Thing.withTransaction {
            Thing.findByName name
        }
    }
}
