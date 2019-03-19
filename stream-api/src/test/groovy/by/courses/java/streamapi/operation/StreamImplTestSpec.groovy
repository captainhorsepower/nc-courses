package by.courses.java.streamapi

import by.courses.java.streamapi.entity.UserBase
import by.courses.java.streamapi.operation.DefaultStream
import by.courses.java.streamapi.operation.Operation
import spock.lang.Shared
import spock.lang.Specification

class StreamImplTestSpec extends Specification {

    @Shared
    def userBases = []
    @Shared
    def deleted = []
    @Shared
    Operation<UserBase> defaultStream = new DefaultStream()


    def setupSpec() {
        userBases = [
                UserBase.of("Alex", 19),
                UserBase.of("Pavel", 19),
                UserBase.of("Andrey", 40),
                UserBase.of("Shasha", 17),
                UserBase.of("Ihar", 22),
                UserBase.of("Vadim", 18),
                UserBase.of("Alex", 40)
        ]
        deleted = [
                UserBase.of("Alex", 19),
                UserBase.of("Pavel", 19),
                UserBase.of("Shasha", 17),
                UserBase.of("Ihar", 22),
                UserBase.of("Vadim", 18)
        ]
    }

    def "RemoveWithMaxAge"() {
        expect:
        defaultStream.removeWithMaxAge(a) == b

        where:
        a << [userBases]
        b << [deleted]
    }

    def "RemoveAllOlder"() {
        expect:
        defaultStream.removeAllOlder(a, b).size() == c
        where:
        a = userBases
        b || c
        19 || 4
        22 || 5
    }

    def "GetAverageAge"() {
        expect:
        defaultStream.getAverageAge(a) == b
        where:
        a         || b
        userBases || 25
        deleted   || 19

    }

    def "GetThirdInCollection"() {
        expect:
        defaultStream.getThirdInCollection(a) == b
        where:
        a         || b
        userBases || UserBase.of("Andrey", 40)
        deleted   || UserBase.of("Shasha", 17)
    }

    def "GetTwoUsersStartingFromSecond"() {
        expect:
        defaultStream.getTwoUsersStartingFromSecond(a) == b
        where:
        a         || b
        userBases || [UserBase.of("Pavel", 19), UserBase.of("Andrey", 40)]
        deleted   || [UserBase.of("Pavel", 19), UserBase.of("Shasha", 17)]
    }

    def "IsCharacterPresentInAllNames"() {
        expect:
        defaultStream.isCharacterPresentInAllNames(a, b) == c
        where:
        a         | b   || c
        userBases | 'a' || true
        deleted   | 'b' || false
    }

    def "AddValueToAllNames"() {
        expect:
        defaultStream.addValueToAllNames(a,b) == c
        where:
        a         | b   || c
        userBases | 'w' || [
                            UserBase.of("Alexw", 19),
                            UserBase.of("Pavelw", 19),
                            UserBase.of("Andreyw", 40),
                            UserBase.of("Shashaw", 17),
                            UserBase.of("Iharw", 22),
                            UserBase.of("Vadimw", 18),
                            UserBase.of("Alexw", 40)
        ]

    }

    def "SortByNameThanByAge"() {
        expect:
        defaultStream.sortByNameThanByAge(a) == b
        where:
        a         || b
        userBases || [
                        UserBase.of("Alex", 19),
                        UserBase.of("Alex", 40),
                        UserBase.of("Andrey", 40),
                        UserBase.of("Ihar", 22),
                        UserBase.of("Pavel", 19),
                        UserBase.of("Shasha", 17),
                        UserBase.of("Vadim", 18)
                        ]
    }
}