package ru.javaops.webapp.storage;

import java.util.Arrays;

import ru.javaops.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10_000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size - 1, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size >= 10000) {
            System.out.println("Ошибка! Память полностью заполнена! Удалите ненужные резюме.");
        } else if (findIndex(r.getUuid()) != -1) {
            System.out.println("Ошибка! Резюме с uuid: " + r.getUuid() + " уже существует!");
        } else {
            storage[size] = r;
            size++;
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

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            printError(uuid);
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    private void printError(String uuid) {
        System.out.println("Ошибка! Резюме с uuid: " + uuid + " не существует!");
    }
}