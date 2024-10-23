package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        checkResume(index, r.getUuid());
        updateResume(index, r);
    }

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        checkResume(index, uuid);
        return getResume(index);
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        checkResume(index, uuid);
        deleteResume(index);
    }

    protected abstract int findIndex(String uuid);

    protected abstract void updateResume(int index, Resume resume);

    protected abstract Resume getResume(int index);

    protected abstract void deleteResume(int index);

    private void checkResume(int index, String uuid) {
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
    }
}