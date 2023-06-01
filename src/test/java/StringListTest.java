import org.example.ElementNotFoundException;
import org.example.InvalidArgumentException;
import org.example.StringListImpl;
import org.example.StringListIndexOutBoundsException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class StringListTest {
    private StringListImpl out = new StringListImpl(5);

    public static Stream<Arguments> argumentsForContainsPositiveTest() {
        return Stream.of(
                Arguments.of("aaa"),
                Arguments.of("bbb"),
                Arguments.of("ccc")
        );
    }
    public static Stream<Arguments> argumentsForContainsNegativeTest() {
        return Stream.of(
                Arguments.of("ggg"),
                Arguments.of("rrr"),
                Arguments.of("ttt")
        );
    }

    public static Stream<Arguments> argumentsIndexForContainsPositiveTest() {
        return Stream.of(
                Arguments.of("aaa", 0),
                Arguments.of("bbb", 1),
                Arguments.of("ccc", 2)
        );
    }

    public static Stream<Arguments> argumentsIndexForContainsNegativeTest() {
        return Stream.of(
                Arguments.of("ggg", -1),
                Arguments.of("rrr", -1),
                Arguments.of("ttt", -1)
        );
    }

    public static Stream<Arguments> argumentsLastIndexTest() {
        return Stream.of(
                Arguments.of("aaa", 5),
                Arguments.of("bbb", 6),
                Arguments.of("ggg", -1)
        );
    }

    public static Stream<Arguments> argumentsGetPositiveTest() {
        return Stream.of(
                Arguments.of("aaa", 0),
                Arguments.of("bbb", 1),
                Arguments.of("ccc", 2)
        );
    }

    public static Stream<Arguments> argumentsEqualsNegativeTest() {
        return Stream.of(
                Arguments.of((new StringListImpl("fff", "kkk", "lll"))),
                Arguments.of(new StringListImpl("fff", "kkk", "lll", "aaa")),
                Arguments.of(new StringListImpl("fff", "ooo", "ooo"))
        );
    }

    @BeforeEach
    public void fillList() {
        out.add("aaa");
        out.add("bbb");
        out.add("ccc");
        out.add("ddd");
        out.add("eee");
    }

    @AfterEach
    public void clearList() {
        out.clear();
    }

    @Test
    public void addPositiveTest() {
        int size = out.size();
        assertEquals("ggg", out.add("ggg"));
        assertEquals(size + 1, out.size());
    }

    @Test
    public void addIndexPositiveTest() {
        int size = out.size();
        int index = 1;
        assertEquals("ggg", out.add(index, "ggg"));
        assertEquals(index, out.indexOf("ggg"));
        assertEquals(size + 1, out.size());
    }

    @Test
    public void addIndexNegativeTest() {
        assertThrows(StringListIndexOutBoundsException.class, () -> out.add(5, "ggg"));
    }

    @Test
    public void setPositiveTest() {
        int size = out.size();
        int index = 1;
        assertEquals("ggg", out.set(index, "ggg"));
        assertEquals(index, out.indexOf("ggg"));
        assertEquals(size, out.size());
    }

    @Test
    public void setNegativeTest() {
        assertThrows(StringListIndexOutBoundsException.class, () -> out.set(5, "ggg"));
    }

    @Test
    public void removeByValuePositiveTest() {
        int size = out.size();
        assertEquals("aaa", out.remove("aaa"));
        assertEquals(size - 1, out.size());
    }

    @Test
    public void removeByValueNegativeTest() {
        assertThrows(ElementNotFoundException.class, () -> out.remove("ggg"));
    }

    @Test
    public void removeByIndexPositiveTest() {
        int size = out.size();
        assertEquals("aaa", out.remove(0));
        assertEquals(size - 1, out.size());
    }

    @Test
    public void removeByIndexNegativeTest() {
        assertThrows(StringListIndexOutBoundsException.class, () -> out.remove(5));
    }

    @ParameterizedTest
    @MethodSource("argumentsForContainsPositiveTest")
    public void containsPositiveTest(String str) {
        assertTrue(out.contains(str));
    }

    @ParameterizedTest
    @MethodSource("argumentsForContainsNegativeTest")
    public void containsNegativeTest (String str) {
        assertFalse(out.contains(str));
    }

    @ParameterizedTest
    @MethodSource("argumentsIndexForContainsPositiveTest")
    public void argumentsIndexForContainsPositiveTest (String str, int index) {
        assertEquals(index, out.indexOf(str));
    }

    @ParameterizedTest
    @MethodSource("argumentsIndexForContainsNegativeTest")
    public void argumentsIndexForContainsNegativeTest (String str, int index) {
        assertEquals(index, out.indexOf(str));
    }

    @ParameterizedTest
    @MethodSource("argumentsLastIndexTest")
    public void argumentsLastIndexTest(String str, int index) {
        out.add("aaa");
        out.add("bbb");
        assertEquals(index, out.lastIndexOf(str));
    }

    @ParameterizedTest
    @MethodSource("argumentsGetPositiveTest")
    public void argumentsGetPositiveTest(String str, int index) {
        assertEquals(str, out.get(index));
    }

    @Test
    public void getNegativeTest() {
        assertThrows(StringListIndexOutBoundsException.class, () -> out.get(5));
    }

    @Test
    public void equalsPositiveTest() {
        StringListImpl test = new StringListImpl(5);
        test.add("aaa");
        test.add("bbb");
        test.add("ccc");
        test.add("ddd");
        test.add("eee");
        assertTrue(out.equals(test));
    }

    @Test
    public void equalsNullNegativeTest() {
        assertThrows(InvalidArgumentException.class, () -> out.equals(null));
    }

    @ParameterizedTest
    @MethodSource("argumentsEqualsNegativeTest")
    public void argumentsEqualsNegativeTest(StringListImpl str) {
        assertFalse(out.equals(str));
    }

    @Test
    public void isEmptyPositiveTest() {
        StringListImpl test = new StringListImpl(5);
        assertTrue(test.isEmpty());
    }

    @Test
    public void isEmptyNegativeTest() {
        assertFalse(out.isEmpty());
    }

    @Test
    public void toArrayTest() {
        String[] test = {"aaa", "bbb", "ccc", "ddd","eee"};
        assertArrayEquals(test, out.toArray());
    }
}
