package io.github.manuelarte.spring.queryparameter.jpa.util;

@FunctionalInterface
public interface TriPredicate<T, U, V> {

  boolean test(T t, U u, V v);

}
