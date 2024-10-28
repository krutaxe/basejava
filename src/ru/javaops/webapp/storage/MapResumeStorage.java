package ru.javaops.webapp.storage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.javaops.webapp.model.Resume;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void saveResume(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume findSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void updateResume(Resume searchKey, Resume resume) {
        storage.replace(searchKey.getUuid(), resume);
    }

    @Override
    protected Resume getResume(Resume searchKey) {
        return storage.get(searchKey.getUuid());
    }

    @Override
    protected void deleteResume(Resume searchKey) {
        storage.remove(searchKey.getUuid());
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
    protected boolean isExisting(Resume searchKey) {
        return storage.containsKey(searchKey.getUuid());
    }
}