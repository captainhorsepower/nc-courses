package by.courses.java.streamapi.operation;

import by.courses.java.streamapi.entity.UserBase;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public class DefaultStream implements Operation<UserBase> {

    @Override
    public Collection<UserBase> removeWithMaxAge(Collection<UserBase> entities) {
        return entities.stream()
                .filter(u ->
                        u.getAge() < entities.stream()
                                .mapToDouble(UserBase::getAge)
                                .max()
                                .orElse(Double.MAX_VALUE))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<UserBase> removeAllOlder(Collection<UserBase> entities, int age) {
        return entities.stream()
                .filter(u -> u.getAge() <= age)
                .collect(Collectors.toList());
    }

    @Override
    public double getAverageAge(Collection<UserBase> entities) {
        return entities.stream()
                .collect(Collectors.averagingDouble(UserBase::getAge));
    }

    @Override
    public UserBase getThirdInCollection(Collection<UserBase> entities) {
        return entities.stream()
                .skip(2)
                .findFirst()
                .get();
    }

    @Override
    public Collection<UserBase> getTwoUsersStartingFromSecond(Collection<UserBase> entities) {
        return entities.stream()
                .skip(1)
                .limit(2)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isCharacterPresentInAllNames(Collection<UserBase> entities, String character) {
        return entities.stream()
                .allMatch(u ->
                        u.getName().toLowerCase()
                                .contains(character.toLowerCase()));
    }

    @Override
    public Collection<UserBase> addValueToAllNames(Collection<UserBase> entities, String value) {
        return entities.stream()
                .map(u -> UserBase.of(u.getName() + value, u.getAge()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<UserBase> sortByNameThanByAge(Collection<UserBase> entities) {
        return entities.stream()
                .sorted(Comparator
                        .comparing(UserBase::getName)
                        .thenComparing(UserBase::getAge))
                .collect(Collectors.toList());
    }
}