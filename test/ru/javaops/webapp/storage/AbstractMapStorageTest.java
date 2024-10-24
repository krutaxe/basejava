package ru.javaops.webapp.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

abstract class AbstractMapStorageTest {
    private final Storage storage;
    private static final String UUID1 = "uuid1";
    private static final String UUID2 = "uuid2";
    private static final String UUID3 = "uuid3";
    private static final String UUID4 = "uuid4";
    private static final Resume R1 = new Resume(UUID1);
    private static final Resume R2 = new Resume(UUID2);
    private static final Resume R3 = new Resume(UUID3);
    private static final Resume R4 = new Resume(UUID4);

    public AbstractMapStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        Assertions.assertArrayEquals(new Resume[]{}, storage.getAll());
    }

    @Test
    void save() {
        storage.save(R4);
        assertSize(4);
        assertGet(R4);
    }

    @Test
    void saveExist() {
        Resume newResume = new Resume("uuid3");
        ExistStorageException exception = Assertions.assertThrows(ExistStorageException.class,
                () -> storage.save(newResume));
        Assertions.assertEquals("Resume uuid3 already exist", exception.getMessage());
    }

    @Test
    void update() {
        Resume updateResume = new Resume("uuid1");
        storage.update(updateResume);
        Assertions.assertSame(updateResume, storage.get("uuid1"));
    }

    @Test
    void updateNotExist() {
        Resume updateResume = new Resume("uuid5");
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
        assertSize(2);
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
        Assertions.assertEquals(3, storage.getAll().length);
    }

    @Test
    void size() {
        assertSize(3);
    }

    void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }
}