package ru.javaops.webapp.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10_000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = new ArrayList<>();
        Collections.addAll(resumeList, Arrays.copyOf(storage, size));
        resumeList.sort(Comparator.comparing(Resume::getFullName)
                .thenComparing(Resume::getUuid));
        return resumeList;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected void updateResume(Integer searchKey, Resume resume) {
        storage[searchKey] = resume;
    }

    @Override
    protected Resume getResume(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected void saveResume(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insertResume(resume);
        size++;
    }

    @Override
    protected boolean isExisting(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void deleteResume(Integer searchKey) {
        fillDeletedElement(searchKey);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void insertResume(Resume r);

    protected abstract void fillDeletedElement(Integer searchKey);
}