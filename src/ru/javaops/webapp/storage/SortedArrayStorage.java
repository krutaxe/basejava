package ru.javaops.webapp.storage;

import java.util.Arrays;
import ru.javaops.webapp.model.Resume;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected Integer findSearchKey(String uuid) {
        Resume resume = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, resume);
    }

    @Override
    protected void insertResume(Resume r) {
        int searchKey = findSearchKey(r.getUuid());
        if (searchKey < 0) {
            searchKey = Math.abs(searchKey) - 1;
        }
        System.arraycopy(storage, searchKey, storage, searchKey + 1, size - searchKey);
        storage[searchKey] = r;
    }

    @Override
    protected void deleteResume(Integer searchKey) {
        if (searchKey < 0) {
            searchKey = Math.abs(searchKey) - 1;
        }
        System.arraycopy(storage, searchKey + 1, storage, searchKey, size - searchKey - 1);
        updateArrayStorage();
    }
}