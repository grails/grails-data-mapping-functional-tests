package functional.tests

import grails.mongodb.geo.Point
import geb.spock.GebSpec
import grails.test.mixin.integration.Integration

@Integration
class GeoJsonSpec extends spock.lang.Specification {

    void "Test save GeoJSON enabled entity"() {
        when:"An entity with GeoJSON is saved"
        Place.withNewSession {
            new Place(name: "London", location: Point.valueOf(10,10)).save(flush:true)    
            it.clear()
        }

        Place p = Place.withNewSession{
            Place.first()
        }

        

        then:"The GeoJSON data is saved"
        p.location != null
    }
}