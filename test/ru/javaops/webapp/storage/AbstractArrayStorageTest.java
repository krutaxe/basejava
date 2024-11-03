package ru.javaops.webapp.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    void saveArrayOverflow() {
        storage.clear();
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume(String.valueOf(i), ""));
        }
        StorageException exception = Assertions.assertThrows(StorageException.class,
                () -> storage.save(new Resume("10001", "")));
        Assertions.assertEquals("Storage overflow", exception.getMessage());
    }
}