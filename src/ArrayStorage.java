import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i <= size(); i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
        storage[size()] = r;
    }

    Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            return new Resume();
        }
        return storage[index];
    }

    void delete(String uuid) {
        int size = size();
        int index = findIndex(uuid);
        if (storage[index].uuid.equals(uuid)) {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
            storage[size - 1] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] result = new Resume[size()];
        if (size() >= 0) {
            result = Arrays.copyOf(storage, size());
        }
        return result;
    }

    int size() {
        int size = 0;
        for (Resume resume : storage) {
            if (resume == null) {
                break;
            }
            size++;
        }
        return size;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}