package ru.javaops.webapp.storage;

import java.util.List;
import ru.javaops.webapp.model.Resume;

public interface Storage {
    void clear();

    void save(Resume r);

    void update(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();
}