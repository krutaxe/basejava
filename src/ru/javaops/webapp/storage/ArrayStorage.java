package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertResume(Resume r) {
        storage[size] = r;
    }

    @Override
    protected void fillDeletedElement(Integer searchKey) {
        storage[searchKey] = storage[size - 1];
    }
}