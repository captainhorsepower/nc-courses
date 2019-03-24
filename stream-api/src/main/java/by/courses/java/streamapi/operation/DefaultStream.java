package by.courses.java.streamapi.operation;

import by.courses.java.streamapi.entity.UserBase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public class DefaultStream implements Operation<UserBase> {

    @Override
    public Collection<UserBase> removeWithMaxAge(Collection<UserBase> entities) {
        return null;
    }

    @Override
    public Collection<UserBase> removeAllOlder(Collection<UserBase> entities, int age) {
        return entities.stream()
                .filter(u -> u.getAge() > age)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public double getAverageAge(Collection<UserBase> entities) {
        return entities.stream()
                .collect(Collectors.averagingDouble(UserBase::getAge));
    }

    @Override
    public UserBase getThirdInCollection(Collection<UserBase> entities) {
        return null;
    }

    @Override
    public Collection<UserBase> getTwoUsersStartingFromSecond(Collection<UserBase> entities) {
        return null;
    }

    @Override
    public boolean isCharacterPresentInAllNames(Collection<UserBase> entities, String character) {
        return entities.stream()
                .allMatch(u -> u.getName().contains(character));
    }

    @Override
    public Collection<UserBase> addValueToAllNames(Collection<UserBase> entities, String value) {
        return null;
    }

    @Override
    public Collection<UserBase> sortByNameThanByAge(Collection<UserBase> entities) {
        return null;
    }
}