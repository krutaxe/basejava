package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertResume(Resume r, int index) {
        storage[size] = r;
        size++;
    }

    @Override
    protected void deleteResume(int resultFindIndex) {
        storage[resultFindIndex] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }
}