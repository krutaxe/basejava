package ru.javaops.webapp.storage;

import java.util.Arrays;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size - 1, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            printError(uuid);
            return null;
        }
        return storage[index];
    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index == -1) {
            printError(r.getUuid());
        } else {
            storage[index] = r;
        }
    }

    protected abstract int findIndex(String uuid);

    protected void printError(String uuid) {
        System.out.println("Ошибка! Резюме с uuid: " + uuid + " не существует!");
    }
}