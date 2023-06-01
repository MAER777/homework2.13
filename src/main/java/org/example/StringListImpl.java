package org.example;
import java.util.Arrays;
import java.util.Objects;

public class StringListImpl implements StringList{
    private String[] strings;
    private int stringSize;

    public StringListImpl(int size) {
        strings = new String[size];
        stringSize = 0;
    }

    public StringListImpl (String... args) {
        strings = new String [args.length];
        System.arraycopy(args, 0,strings, 0, args.length);
        stringSize = strings.length;
    }
    @Override
    public String add(String item) {
        if (stringSize == strings.length) {
            resize(stringSize + 1);
        }
        strings[stringSize] = item;
        stringSize++;
        return item;
    }

    @Override
    public String add(int index, String item) {
        checkBound(index);
        if (stringSize == strings.length) {
            resize(stringSize + 1);
        }
        for (int i = stringSize; i > index; i--) {
            strings[i] = strings[i-1];
        }
        strings[index] = item;
        stringSize++;
        return item;
    }

    @Override
    public String set(int index, String item) {
        checkBound(index);
        strings[index] = item;
        return item;
    }

    @Override
    public String remove(String item) {
        int index = indexOf(item);
        if (index == -1) {
            throw new ElementNotFoundException();
        }
        return remove(index);
    }

    @Override
    public String remove(int index) {
        checkBound(index);
        String result = strings[index];
        for (int i = index + 1; i < stringSize; i++) {
            strings[i-1] = strings[i];
        }
        stringSize--;
        return result;
    }

    @Override
    public boolean contains(String item) {
        return indexOf(item) != -1;
    }

    @Override
    public int indexOf(String item) {
        for (int i = 0; i < stringSize; i++) {
            if (strings[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(String item) {
        for (int i = stringSize - 1; i >= 0; i--) {
            if (strings[i].equals(item))
                return i;
        }
        return -1;
    }

    @Override
    public String get(int index) {
        checkBound(index);
        return strings[index];
    }

    @Override
    public boolean equals(StringList otherList) {
        if (otherList == null) {
            throw new InvalidArgumentException();
        }
        if (stringSize != otherList.size()) {
            return false;
        }
        for (int i = 0; i < stringSize; i++) {
            if (!Objects.equals(strings[i], otherList.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return stringSize;
    }

    @Override
    public boolean isEmpty() {
        return stringSize ==0;
    }

    @Override
    public void clear() {
        Arrays.fill(strings, 0, stringSize, null);
        stringSize = 0;
    }

    @Override
    public String[] toArray() {
        String[] arr = new String[stringSize];
        System.arraycopy(strings, 0, arr, 0, stringSize);
        return arr;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (int i = 0; i < stringSize; i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(strings[i]);
        }
        result.append("]");
        return result.toString();
    }

    private  void checkBound(int index) {
        if (index < 0 || index >= stringSize) {
            throw new StringListIndexOutBoundsException();
        }
    }

    private void resize(int newSize) {
        int size = stringSize * 2;
        size = Math.max(size, newSize);
        String[] newStrings = new String[size];
        System.arraycopy(strings, 0, newStrings, 0, stringSize);
        strings = newStrings;
    }
}
