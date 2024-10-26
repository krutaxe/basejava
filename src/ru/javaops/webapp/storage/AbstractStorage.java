package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractStorage<T> implements Storage {
    @Override
    public final void update(Resume r) {
        T searchKey = findSearchKey(r.getUuid());
        checkNotExistingSearchKey(searchKey, r.getUuid());
        updateResume(searchKey, r);
    }

    @Override
    public final Resume get(String uuid) {
        T searchKey = findSearchKey(uuid);
        checkNotExistingSearchKey(searchKey, uuid);
        return getResume(searchKey);
    }

    @Override
    public final void delete(String uuid) {
        T searchKey = findSearchKey(uuid);
        checkNotExistingSearchKey(searchKey, uuid);
        deleteResume(searchKey);
    }

    @Override
    public final void save(Resume resume) {
        T searchKey = findSearchKey(resume.getUuid());
        checkExistingSearchKey(searchKey, resume.getUuid());
        saveResume(resume);
    }

    protected abstract boolean isExisting(T searchKey);

    protected abstract void saveResume(Resume resume);

    protected abstract T findSearchKey(String uuid);

    protected abstract void updateResume(T searchKey, Resume resume);

    protected abstract Resume getResume(T searchKey);

    protected abstract void deleteResume(T searchKey);

    private void checkExistingSearchKey(T searchKey, String uuid) {
        if (isExisting(searchKey)) {
            throw new ExistStorageException(uuid);
        }
    }

    private void checkNotExistingSearchKey(T searchKey, String uuid) {
        if (!isExisting(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
    }
}