package confectionery.Repository;

import confectionery.Model.FileType;
import confectionery.Model.HasID;

public class FileRepositoryFactory {

    /**
     * Returns an instance of the specified file repository type.
     *
     * @param fileType The type of file repository to create (e.g., CSV, JSON).
     * @param filePath The path to the file for data storage.
     * @param <T>      The type of objects stored in the repository, which must implement HasId.
     * @return An instance of a FileRepository.
     * @throws IllegalArgumentException If the file type is unsupported.
     */
    public static <T extends HasID> FileRepository<T> getInstance(FileType fileType, String filePath) {
        switch (fileType) {
            case CSV:
                return new CSVFileRepository<>(filePath);
            // Future implementations can be added here:
            // case JSON:
            //     return new JSONFileRepository<>(filePath);
            // case TSV:
            //     return new TSVFileRepository<>(filePath);
            // case XML:
            //     return new XMLFileRepository<>(filePath);
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
    }
}