package ru.javaops.webapp.storage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.javaops.webapp.model.Resume;

public class MapUuidStorage extends AbstractStorage<String> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void saveResume(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected String findSearchKey(String uuid) {
        if (storage.get(uuid) == null) {
            return null;
        }
        return uuid;
    }

    @Override
    protected void updateResume(String searchKey, Resume resume) {
        storage.replace(searchKey, resume);
    }

    @Override
    protected Resume getResume(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void deleteResume(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = new ArrayList<>(storage.values());
        resumeList.sort(Comparator.comparing(Resume::getFullName)
                .thenComparing(Resume::getUuid));
        return resumeList;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExisting(String searchKey) {
        return storage.containsKey(searchKey);
    }
}