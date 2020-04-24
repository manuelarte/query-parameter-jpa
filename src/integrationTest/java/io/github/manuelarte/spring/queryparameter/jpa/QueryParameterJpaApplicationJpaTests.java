package io.github.manuelarte.spring.queryparameter.jpa;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.manuelarte.spring.queryparameter.jpa.config.JpaQueryParamConfig;
import io.github.manuelarte.spring.queryparameter.jpa.model.QueryCriteriaJpaSpecification;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.OperatorPredicate;
import io.github.manuelarte.spring.queryparameter.model.TypeTransformerProvider;
import io.github.manuelarte.spring.queryparameter.model.TypeTransformerRegistry;
import io.github.manuelarte.spring.queryparameter.operators.EqualsOperator;
import io.github.manuelarte.spring.queryparameter.operators.InOperator;
import io.github.manuelarte.spring.queryparameter.operators.NotInOperator;
import io.github.manuelarte.spring.queryparameter.operators.queryprovider.OperatorQueryProvider;
import io.github.manuelarte.spring.queryparameter.query.QueryCriteria;
import io.github.manuelarte.spring.queryparameter.query.QueryCriterion;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootTest(classes = QueryParameterJpaApplicationJpaTests.class)
@EnableWebMvc // needed for conversion service
@EnableAutoConfiguration
@Import({TypeTransformerRegistry.class, JpaQueryParamConfig.class})
class QueryParameterJpaApplicationJpaTests {

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private TypeTransformerProvider typeTransformerProvider;

  @Autowired
  private OperatorQueryProvider<OperatorPredicate<Object>, Predicate> operatorPredicateProvider;

  @Test
  @Transactional
  void testEqualsPredicate() {
    final ParentEntity oneEntity = createAndPersistParentEntity("Manuel", "Doncel", 33);
    createAndPersistParentEntity("Antonio", "Doncel", 23);
    entityManager.flush();

    final QueryCriteria queryCriteria = QueryCriteria.fromCriterion("firstName",
        new EqualsOperator(), "Manuel");
    final Specification<ParentEntity> specification = new QueryCriteriaJpaSpecification<>(
        ParentEntity.class, queryCriteria, typeTransformerProvider,
        operatorPredicateProvider);

    final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    final CriteriaQuery<ParentEntity> criteria = builder.createQuery(ParentEntity.class);
    final Root<ParentEntity> from = criteria.from(ParentEntity.class);

    final Predicate predicate = specification.toPredicate(from, criteria, builder);
    criteria.where(predicate);
    final List<ParentEntity> result = entityManager.createQuery(criteria).getResultList();
    assertEquals(1, result.size());
    assertEquals(oneEntity, result.get(0));
  }

  @Test
  @Transactional
  void testInPredicate() {
    final ParentEntity oneEntity = createAndPersistParentEntity("Manuel", "Doncel", 33);
    final ParentEntity twoEntity = createAndPersistParentEntity("Antonio", "Doncel", 23);
    createAndPersistParentEntity("Test", "Surname", 40);
    entityManager.flush();

    final QueryCriteria queryCriteria = new QueryCriteria(new QueryCriterion<>("firstName",
        new InOperator(), Arrays.asList("Manuel", "Antonio")));
    final Specification<ParentEntity> specification = new QueryCriteriaJpaSpecification<>(
        ParentEntity.class, queryCriteria, typeTransformerProvider,
        operatorPredicateProvider);

    final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    final CriteriaQuery<ParentEntity> criteria = builder.createQuery(ParentEntity.class);
    final Root<ParentEntity> from = criteria.from(ParentEntity.class);

    final Predicate predicate = specification.toPredicate(from, criteria, builder);
    criteria.where(predicate);
    final List<ParentEntity> actual = entityManager.createQuery(criteria).getResultList();
    assertEquals(2, actual.size());
    assertThat(actual, containsInAnyOrder(oneEntity, twoEntity));
  }

  @Test
  @Transactional
  void testNotInPredicate() {
    final ParentEntity oneEntity = createAndPersistParentEntity("Manuel", "Doncel", 33);
    final ParentEntity twoEntity = createAndPersistParentEntity("Antonio", "Doncel", 23);
    final ParentEntity testEntity = createAndPersistParentEntity("Test", "Surname", 40);
    entityManager.flush();

    final QueryCriteria queryCriteria = new QueryCriteria(new QueryCriterion<>("firstName",
        new NotInOperator(new InOperator()), Arrays.asList("Manuel", "Antonio")));
    final Specification<ParentEntity> specification = new QueryCriteriaJpaSpecification<>(
        ParentEntity.class, queryCriteria, typeTransformerProvider,
        operatorPredicateProvider);

    final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    final CriteriaQuery<ParentEntity> criteria = builder.createQuery(ParentEntity.class);
    final Root<ParentEntity> from = criteria.from(ParentEntity.class);

    final Predicate predicate = specification.toPredicate(from, criteria, builder);
    criteria.where(predicate);
    final List<ParentEntity> actual = entityManager.createQuery(criteria).getResultList();
    assertEquals(1, actual.size());
    assertEquals(testEntity, actual.get(0));
  }

  private ParentEntity createAndPersistParentEntity(final String firstName, final String lastName,
      final int age) {
    final ParentEntity parentEntity = new ParentEntity();
    parentEntity.firstName = firstName;
    parentEntity.lastName = lastName;
    parentEntity.age = age;
    entityManager.persist(parentEntity);
    return parentEntity;
  }

  @Entity
  @Table(name = "parent")
  public static class ParentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private int age;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      ParentEntity that = (ParentEntity) o;
      return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
      return Objects.hash(id);
    }
  }

}
