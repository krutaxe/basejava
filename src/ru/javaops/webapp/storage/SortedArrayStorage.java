package ru.javaops.webapp.storage;

import java.util.Arrays;
import ru.javaops.webapp.model.Resume;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected Object findIndex(String uuid) {
        Resume resume = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, resume);
    }

    @Override
    protected void insertResume(Resume r, int index) {
        if (index < 0) {
            index = Math.abs(index) - 1;
        }
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = r;
    }

    @Override
    protected void deleteResume(Object index) {
        if ((int) index < 0) {
            index = Math.abs((int) index) - 1;
        }
        System.arraycopy(storage, (int) index + 1, storage, (int) index, size - (int) index - 1);
        updateArrayStorage(index);
    }
}