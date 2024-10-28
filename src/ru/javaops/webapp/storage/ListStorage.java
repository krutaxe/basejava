package ru.javaops.webapp.storage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import ru.javaops.webapp.model.Resume;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected void saveResume(Resume resume) {
        storage.add(resume);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        Comparator<Resume> comparator = Comparator.comparing(Resume::getFullName)
                .thenComparing(Resume::getUuid);
        storage.sort(comparator);
        return storage;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public Integer findSearchKey(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void updateResume(Integer searchKey, Resume resume) {
        storage.set(searchKey, resume);
    }

    @Override
    protected Resume getResume(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void deleteResume(Integer searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected boolean isExisting(Integer searchKey) {
        return searchKey >= 0;
    }
}