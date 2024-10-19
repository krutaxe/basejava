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
    private static final Resume r1 = new Resume("uuid1");
    private static final Resume r2 = new Resume("uuid2");
    private static final Resume r3 = new Resume("uuid3");

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @Test
    void save() {
        storage.save(new Resume("uuid4"));
        Assertions.assertEquals(4, storage.size());
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
        for (int i = 2; i < 9999; i++) {
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
        Assertions.assertEquals(updateResume.getUuid(), r1.getUuid());
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
        Assertions.assertEquals(r2, storage.get("uuid2"));
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
        Assertions.assertEquals(2, storage.size());
    }

    @Test
    void deleteNotExist() {
        NotExistStorageException exception = Assertions.assertThrows(NotExistStorageException.class,
                () -> storage.delete("uuid"));
        Assertions.assertEquals("Resume uuid not exist", exception.getMessage());
    }

    @Test
    void getAll() {
        Resume[] array = storage.getAll();
        Assertions.assertEquals(r1, array[0]);
        Assertions.assertEquals(r2, array[1]);
        Assertions.assertEquals(r3, array[2]);


    }

    @Test
    void size() {
        Assertions.assertEquals(3, storage.size());
    }
}