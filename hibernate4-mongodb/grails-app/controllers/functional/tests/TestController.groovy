package functional.tests

import grails.converters.JSON

class TestController {

    def thingHelper
    def thingService

    // http://localhost:8080/test/createDirect?name=thing1
    def createDirect(String name) {
        def thing = new Thing(name: name)
        Thing.withTransaction {
            thing.save()
        }
        render(thing as JSON)
    }

    // http://localhost:8080/test/findDirect?name=thing1
    def findDirect(String name) {
        def thing = Thing.findByName(name)
        render thing ? thing as JSON : 'not found'
    }

    // http://localhost:8080/test/createWithService?name=thing2
    def createWithService(String name) {
        def thing
        Thread.start {
            Thing.withNewSession { session ->
                thing = thingService.create(name)   
            }
            
        }.join()
        render thing ? thing as JSON : 'ERROR: service returned null'
    }

    // http://localhost:8080/test/findWithService?name=thing2
    def findWithService(String name) {
        def thing
        Thread.start {
            thing = thingService.find(name)
        }.join()
        render thing ? thing as JSON : 'not found'
    }

    // http://localhost:8080/test/createWithHelper?name=thing3
    def createWithHelper(String name) {
        def thing
        Thread.start {
            thing = thingHelper.create(name)
        }.join()
        render thing ? thing as JSON : 'ERROR: helper returned null'
    }

    // http://localhost:8080/test/findWithHelper?name=thing3
    def findWithHelper(String name) {
        def thing
        Thread.start {
            thing = thingHelper.find(name)
        }.join()
        render thing ? thing as JSON : 'not found'
    }
}
