package ru.javaops.webapp.storage;

import java.util.Arrays;
import ru.javaops.webapp.model.Resume;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int findIndex(String uuid) {
        Resume resume = new Resume();
        resume.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, resume);
    }

    @Override
    protected void insertResume(Resume r, int resultBinarySearch) {
        int index = Math.abs(resultBinarySearch) - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = r;
        size++;
    }

    @Override
    protected void deleteResume(int resultFindIndex) {
        System.arraycopy(storage, resultFindIndex + 1, storage, resultFindIndex, size - resultFindIndex - 1);
        storage[size - 1] = null;
        size--;
    }
}