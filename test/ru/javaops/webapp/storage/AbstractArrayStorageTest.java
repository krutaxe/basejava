package ru.javaops.webapp.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID1 = "uuid1";
    private static final String UUID2 = "uuid2";
    private static final String UUID3 = "uuid3";
    private static final String UUID4 = "uuid4";
    private static final Resume R1 = new Resume(UUID1);
    private static final Resume R2 = new Resume(UUID2);
    private static final Resume R3 = new Resume(UUID3);
    private static final Resume R4 = new Resume(UUID4);

    public AbstractArrayStorageTest(Storage storage) {
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
        Assertions.assertTrue(assertSize(0));
        Assertions.assertArrayEquals(new Resume[]{}, storage.getAll());
    }

    @Test
    void save() {
        storage.save(R4);
        Assertions.assertTrue(assertSize(4));
        Assertions.assertTrue(assertGet(R4));
    }

    @Test
    void saveExist() {
        Resume newResume = new Resume("uuid3");
        ExistStorageException exception = Assertions.assertThrows(ExistStorageException.class,
                () -> storage.save(newResume));
        Assertions.assertEquals("Resume uuid3 already exist", exception.getMessage());
    }

    @Test
    void saveArrayOverflow() {
        storage.clear();
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume(String.valueOf(i)));
        }
        StorageException exception = Assertions.assertThrows(StorageException.class,
                () -> storage.save(new Resume("10001")));
        Assertions.assertEquals("Storage overflow", exception.getMessage());
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
        Assertions.assertTrue(assertGet(R3));
    }

    boolean assertGet(Resume resume) {
        return storage.get(resume.getUuid()).equals(resume);
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
        Assertions.assertTrue(assertSize(2));
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
        Resume[] expected = {R1, R2, R3};
        Assertions.assertArrayEquals(expected, storage.getAll());
    }

    @Test
    void size() {
        Assertions.assertTrue(assertSize(3));
    }

    boolean assertSize(int size) {
        return size == storage.size();
    }
}