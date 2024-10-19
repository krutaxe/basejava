package ru.javaops.webapp.storage;

import java.util.Arrays;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
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
    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            storage[index] = r;
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(index);
            storage[size - 1] = null;
            size--;
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

    protected abstract int findIndex(String uuid);

    protected abstract void insertResume(Resume r, int index);

    protected abstract void deleteResume(int index);
}