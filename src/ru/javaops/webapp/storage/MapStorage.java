package ru.javaops.webapp.storage;

import java.util.HashMap;
import java.util.Map;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void save(Resume resume) {
        if (storage.containsKey(resume.getUuid())) {
            throw new ExistStorageException(resume.getUuid());
        }
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void update(Resume r) {
        if (!storage.containsKey(r.getUuid())) {
            throw new NotExistStorageException(r.getUuid());
        }
        storage.replace(r.getUuid(), r);
    }

    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected Object findIndex(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void updateResume(Object searchKey, Resume resume) {
        storage.replace((String) searchKey, resume);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public Resume get(String uuid) {
        if (!storage.containsKey(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        return storage.get(uuid);
    }

    @Override
    public void delete(String uuid) {
        if (!storage.containsKey(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        storage.remove(uuid);
    }

    @Override
    public int size() {
        return storage.size();
    }
}