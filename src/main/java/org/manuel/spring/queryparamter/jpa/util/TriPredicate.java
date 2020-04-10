package org.manuel.spring.queryparamter.jpa.util;

@FunctionalInterface
public interface TriPredicate<T, U, V> {

  boolean test(T t, U u, V v);

}
