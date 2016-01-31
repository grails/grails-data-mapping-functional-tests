package functional.tests

import grails.test.mixin.integration.Integration
import grails.transaction.*
import static grails.web.http.HttpHeaders.*
import static org.springframework.http.HttpStatus.*
import spock.lang.*
import geb.spock.*
import grails.plugins.rest.client.RestBuilder
/**
 * See http://www.gebish.org/manual/current/ for more instructions
 */
@Integration
@Rollback
class ThingSpec extends GebSpec {


    def cleanup() {
    }

    void "Test that a direct call without a service works"() {
        when:"A direct GORM interaction that uses withTransaction is called"
            def resp = restBuilder().get("$baseUrl/test/createDirect?name=thing1")

        then:"The response is correct"
            resp.status == OK.value()
            resp.headers[CONTENT_TYPE] == ['application/json;charset=UTF-8']
            resp.json.name == 'thing1'
    }


    void "Test that a direct call that uses a dynamic finder works"() {
        given:
            new Thing(name:"Fred").save(flush:true)
        when:"A direct GORM interaction that uses withTransaction is called"
            def resp = restBuilder().get("$baseUrl/test/findDirect?name=Fred")

        then:"The response is correct"
            resp.status == OK.value()
            resp.headers[CONTENT_TYPE] == ['application/json;charset=UTF-8']
            resp.json.name == 'Fred'
    }

    void "Test create with service that runs in a different thread"() {
        when:"A service is called that creates an instance in a different thread"
        def resp = restBuilder().get("$baseUrl/test/createWithService?name=thing2")

        then:"The response is correct"
            resp.status == OK.value()
            resp.headers[CONTENT_TYPE] == ['application/json;charset=UTF-8']
            resp.json.name == 'thing2'        
    }

    void "Test that when an entity is found  by a service in a different thread it works"() {
        given:
        new Thing(name:"thing2").save(flush:true)
        when:"A service is called that finds an instance in a different thread"
        def resp = restBuilder().get("$baseUrl/test/findWithService?name=thing2")

        then:"The response is correct"
            resp.status == OK.value()
            resp.headers[CONTENT_TYPE] == ['application/json;charset=UTF-8']
            resp.json.name == 'thing2'          
    }

    void "Test create with a helper class that uses withTransaction that runs in a different thread"() {
        when:"A service is called that creates an instance in a different thread"
        def resp = restBuilder().get("$baseUrl/test/createWithHelper?name=thing2")

        then:"The response is correct"
            resp.status == OK.value()
            resp.headers[CONTENT_TYPE] == ['application/json;charset=UTF-8']
            resp.json.name == 'thing2'        
    }

    void "Test that when an entity is found  by a helper class that uses withTransaction in a different thread it works"() {
        given:
        new Thing(name:"thing2").save(flush:true)
        when:"A service is called that finds an instance in a different thread"
        def resp = restBuilder().get("$baseUrl/test/findWithHelper?name=thing2")

        then:"The response is correct"
            resp.status == OK.value()
            resp.headers[CONTENT_TYPE] == ['application/json;charset=UTF-8']
            resp.json.name == 'thing2'          
    }

    RestBuilder restBuilder() {
        new RestBuilder()
    }
}
