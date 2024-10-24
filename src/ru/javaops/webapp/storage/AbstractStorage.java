package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public void update(Resume r) {
        int searchKey = (int) findIndex(r.getUuid());
        if (!isExisting(searchKey)) {
            throw new NotExistStorageException(r.getUuid());
        }
        updateResume(searchKey, r);
    }

    @Override
    public Resume get(String uuid) {
        int searchKey = (int) findIndex(uuid);
        if (!isExisting(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(searchKey);
    }

    @Override
    public void delete(String uuid) {
        int searchKey = (int) findIndex(uuid);
        if (!isExisting(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        deleteResume(searchKey);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = findIndex(resume.getUuid());
        if (isExisting(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveResume(resume, searchKey);
    }

    private boolean isExisting(Object index) {
        return (int) index >= 0;
    }

    protected abstract void saveResume(Resume resume, Object searchKey);

    protected abstract Object findIndex(String uuid);

    protected abstract void updateResume(Object searchKey, Resume resume);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void deleteResume(Object searchKey);
}