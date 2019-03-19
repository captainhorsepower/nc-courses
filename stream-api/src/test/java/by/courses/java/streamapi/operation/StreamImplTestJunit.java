package by.courses.java.streamapi.operation;

import by.courses.java.streamapi.entity.UserBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StreamImplTestJunit {

    private List<UserBase> userBases = new ArrayList<>();
    private Operation<UserBase> defaultStream = new DefaultStream();

    @BeforeEach
    void setUp() {
        userBases.add(UserBase.of("Alex", 19));
        userBases.add(UserBase.of("Pavel", 19));
        userBases.add(UserBase.of("Shasha", 20));
        userBases.add(UserBase.of("Ihar", 22));
        userBases.add(UserBase.of("Vadim", 18));
        userBases.add(UserBase.of("Andrey", 40));
        userBases.add(UserBase.of("Alex", 40));
    }

    @Test
    void removeWithMaxAge() {
        Collection<UserBase> userBasesWithoutMaxAge = defaultStream.removeWithMaxAge(this.userBases);
        assertEquals(userBasesWithoutMaxAge.size(), this.userBases.size() - 2);
    }

    @Test
    void removeAllOlder() {
        defaultStream.removeAllOlder(userBases, 19);
    }

    @Test
    void getAverageAge() {
        defaultStream.getAverageAge(userBases);
    }

    @Test
    void getThirdInCollection() {
        defaultStream.getThirdInCollection(userBases);
    }

    @Test
    void getTwoUsersStartingFromSecond() {
        defaultStream.getTwoUsersStartingFromSecond(userBases);
    }

    @Test
    void isCharacterPresentInAllNames() {
        defaultStream.isCharacterPresentInAllNames(userBases, "a");
    }

    @Test
    void addValueToAllNames() {
        defaultStream.addValueToAllNames(userBases, "a");
    }

    @Test
    void sortByNameThanByAge() {
        defaultStream.sortByNameThanByAge(userBases);
    }
}