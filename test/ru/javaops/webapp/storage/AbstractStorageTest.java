package ru.javaops.webapp.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractStorageTest {
    protected final Storage storage;
    private static final String UUID1 = "uuid1";
    private static final String UUID2 = "uuid2";
    private static final String UUID3 = "uuid3";
    private static final String UUID4 = "uuid4";
    private static final String UUID5 = "uuid5";
    private static final Resume R1 = new Resume(UUID1, "Zorin Alex");
    private static final Resume R2 = new Resume(UUID2, "Medvedev Misha");
    private static final Resume R3 = new Resume(UUID3, "Zorin Alex");
    private static final Resume R4 = new Resume(UUID4, "Antonov Anton");
    private static final Resume R5 = new Resume(UUID5, "");

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(R3);
        storage.save(R1);
        storage.save(R2);
        storage.save(R4);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        Assertions.assertEquals(new ArrayList<>(), storage.getAllSorted());
    }

    @Test
    void save() {
        storage.save(R5);
        assertSize(5);
        assertGet(R5);
    }

    @Test
    void saveExist() {
        Resume newResume = new Resume("uuid3", "");
        ExistStorageException exception = Assertions.assertThrows(ExistStorageException.class,
                () -> storage.save(newResume));
        Assertions.assertEquals("Resume uuid3 already exist", exception.getMessage());
    }

    @Test
    void update() {
        Resume updateResume = new Resume("uuid1", "");
        storage.update(updateResume);
        Assertions.assertSame(updateResume, storage.get("uuid1"));
    }

    @Test
    void updateNotExist() {
        Resume updateResume = new Resume("uuid5", "");
        NotExistStorageException exception = Assertions.assertThrows(NotExistStorageException.class,
                () -> storage.update(updateResume));
        Assertions.assertEquals("Resume uuid5 not exist", exception.getMessage());
    }

    @Test
    void get() {
        assertGet(R3);
    }

    void assertGet(Resume resume) {
        Assertions.assertEquals(storage.get(resume.getUuid()), resume);
    }

    @Test()
    public void getNotExist() {
        NotExistStorageException exception = Assertions.assertThrows(NotExistStorageException.class,
                () -> storage.get("dummy"));
        Assertions.assertEquals("Resume dummy not exist", exception.getMessage());
    }

    @Test
    void delete() {
        storage.delete("uuid2");
        NotExistStorageException exception = Assertions.assertThrows(NotExistStorageException.class,
                () -> storage.get("uuid2"));
        assertSize(3);
        Assertions.assertEquals("Resume uuid2 not exist", exception.getMessage());
    }

    @Test
    void deleteNotExist() {
        NotExistStorageException exception = Assertions.assertThrows(NotExistStorageException.class,
                () -> storage.delete("uuid"));
        Assertions.assertEquals("Resume uuid not exist", exception.getMessage());
    }

    @Test
    void getAll() {
        List<Resume> expected = List.of(R4, R2, R1, R3);
        Assertions.assertEquals(expected, storage.getAllSorted());
    }

    @Test
    void size() {
        assertSize(4);
    }

    void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }
}