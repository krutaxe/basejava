package ru.javaops.webapp.storage;

import java.util.Arrays;
import ru.javaops.webapp.model.Resume;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void save(Resume r) {
        int result = findIndex(r.getUuid());
        if (size >= STORAGE_LIMIT) {
            System.out.println("Ошибка! Память полностью заполнена! Удалите ненужные резюме.");
        } else if (result >= 0) {
            System.out.println("Ошибка! Резюме с uuid: " + r.getUuid() + " уже существует!");
        } else {
            int index = Math.abs(result) - 1;
            System.arraycopy(storage, index, storage, index + 1, size - index);
            storage[index] = r;
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            printError(uuid);
        } else {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    protected int findIndex(String uuid) {
        Resume resume = new Resume();
        resume.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, resume);
    }
}