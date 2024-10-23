package ru.javaops.webapp.storage;

import java.util.Arrays;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume r) {
        int result = findIndex(r.getUuid());
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else if (result >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            insertResume(r, result);
            size++;
        }
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        storage[index] = resume;
    }

    protected abstract void insertResume(Resume r, int index);

    @Override
    protected Resume getResume(int index) {
        return storage[index];
    }

    protected void updateArrayStorage(int index) {
        storage[size - 1] = null;
        size--;
    }
}