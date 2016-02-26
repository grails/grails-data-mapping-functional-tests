package functional.tests

import grails.test.mixin.TestMixin
import grails.test.mixin.gorm.Domain
import grails.test.mixin.hibernate.HibernateTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Domain(Book)
@TestMixin(HibernateTestMixin)
class BookSpec extends Specification {
  void "Test count people"() {
    expect: "Test execute Hibernate count query"
    Book.count() == 0
    sessionFactory != null
    transactionManager != null
    hibernateSession != null
  }
}