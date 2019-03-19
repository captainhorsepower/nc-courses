package by.courses.java.streamapi.operation;

import by.courses.java.streamapi.entity.UserBase;

import java.util.Collection;

public interface Operation<E extends UserBase> {

    Collection<E> removeWithMaxAge(Collection<E> entities);

    Collection<E> removeAllOlder(Collection<E> entities, int age);

    double getAverageAge(Collection<E> entities);

    E getThirdInCollection(Collection<E> entities);

    Collection<E> getTwoUsersStartingFromSecond(Collection<E> entities);

    boolean isCharacterPresentInAllNames(Collection<E> entities,String character);

    Collection<E> addValueToAllNames(Collection<E> entities,String value);

    Collection<E> sortByNameThanByAge(Collection<E> entities);
}