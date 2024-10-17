package ru.javaops.webapp.storage;

import java.util.Arrays;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size - 1, null);
        size = 0;
    }

    @Override
    public void save(Resume r) {
        int result = findIndex(r.getUuid());
        if (size >= STORAGE_LIMIT) {
            System.out.println("Ошибка! Память полностью заполнена! Удалите ненужные резюме.");
        } else if (result >= 0) {
            System.out.println("Ошибка! Резюме с uuid: " + r.getUuid() + " уже существует!");
        } else {
            insertResume(r, result);
        }
    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index == -1) {
            printError(r.getUuid());
        } else {
            storage[index] = r;
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            printError(uuid);
            return null;
        }
        return storage[index];
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            printError(uuid);
        } else {
            deleteResume(index);
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract int findIndex(String uuid);

    protected abstract void insertResume(Resume r, int resultFindIndex);

    protected abstract void deleteResume(int resultFindIndex);

    private void printError(String uuid) {
        System.out.println("Ошибка! Резюме с uuid: " + uuid + " не существует!");
    }
}