package functional.tests

import grails.transaction.Transactional

@Transactional
class ThingService {

    Thing create(String name) {
        def t = new Thing(name: name)
        t.save()
        t
    }

    Thing find(String name) {
        Thing.findByName name
    }
}
