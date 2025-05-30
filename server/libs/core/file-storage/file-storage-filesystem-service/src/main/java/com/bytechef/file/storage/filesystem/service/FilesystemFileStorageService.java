/*
 * Copyright 2025 ByteChef
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bytechef.file.storage.filesystem.service;

import com.bytechef.config.ApplicationProperties;
import com.bytechef.file.storage.domain.FileEntry;
import com.bytechef.file.storage.exception.FileStorageException;
import com.bytechef.file.storage.service.FileStorageService;
import com.bytechef.tenant.TenantContext;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * @author Ivica Cardic
 */
public class FilesystemFileStorageService implements FileStorageService {

    private static final String URL_PREFIX = "file:";

    private final Path baseDirPath;

    public FilesystemFileStorageService(String baseDir) {
        this.baseDirPath = Paths.get(baseDir);
    }

    @Override
    public void deleteFile(String directoryPath, FileEntry fileEntry) {
        Path path = resolveDirectoryPath(directoryPath);
        String url = fileEntry.getUrl();

        boolean deleted = path.resolve(url.replace(URL_PREFIX, ""))
            .toFile()
            .delete();

        if (!deleted) {
            throw new FileStorageException("File %s cannot be deleted".formatted(path));
        }
    }

    @Override
    public boolean fileExists(String directoryPath, FileEntry fileEntry) throws FileStorageException {
        Path path = resolveDirectoryPath(directoryPath);
        String url = fileEntry.getUrl();

        return path.resolve(url.replace(URL_PREFIX, ""))
            .toFile()
            .exists();
    }

    @Override
    public boolean fileExists(String directoryPath, String nonRandomFilename)
        throws FileStorageException {

        Path path = resolveDirectoryPath(directoryPath);

        return path.resolve(nonRandomFilename)
            .toFile()
            .exists();
    }

    @Override
    public FileEntry getFileEntry(String directoryPath, String nonRandomFilename)
        throws FileStorageException {

        Path path = resolveDirectoryPath(directoryPath);

        FileEntry fileEntry = new FileEntry(nonRandomFilename, URL_PREFIX + path.resolve(nonRandomFilename));

        fileExists(directoryPath, fileEntry);

        return fileEntry;
    }

    @Override
    public Set<FileEntry> getFileEntries(String directoryPath) throws FileStorageException {
        Path curDirectoryPath = resolveDirectoryPath(directoryPath);

        try (Stream<Path> stream = Files.walk(curDirectoryPath)) {
            return stream
                .filter(path -> !Files.isDirectory(path))
                .map(path -> new FileEntry(toString(path.getFileName()), URL_PREFIX + path))
                .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new FileStorageException(e.getMessage(), e);
        }
    }

    @Override
    public InputStream getFileStream(String directoryPath, FileEntry fileEntry) {
        Path path = resolveDirectoryPath(directoryPath);
        String url = fileEntry.getUrl();

        try {
            return Files.newInputStream(path.resolve(url.replace(URL_PREFIX, "")), StandardOpenOption.READ);
        } catch (IOException ioe) {
            throw new FileStorageException("Failed to open file " + url, ioe);
        }
    }

    @Override
    public URL getFileEntryURL(String directoryPath, FileEntry fileEntry) {
        Path path = resolveDirectoryPath(directoryPath);
        String url = fileEntry.getUrl();

        try {
            return path.resolve(url.replace(URL_PREFIX, ""))
                .toUri()
                .toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] readFileToBytes(String directoryPath, FileEntry fileEntry)
        throws FileStorageException {

        Path path = resolveDirectoryPath(directoryPath);
        String url = fileEntry.getUrl();

        try {
            return Files.readAllBytes(path.resolve(url.replace(URL_PREFIX, "")));
        } catch (IOException ioe) {
            throw new FileStorageException("Failed to open file " + url, ioe);
        }
    }

    @Override
    public String readFileToString(String directoryPath, FileEntry fileEntry)
        throws FileStorageException {

        Path path = resolveDirectoryPath(directoryPath);
        String url = fileEntry.getUrl();

        try {
            return Files.readString(path.resolve(url.replace(URL_PREFIX, "")));
        } catch (IOException ioe) {
            throw new FileStorageException("Failed to open file " + url, ioe);
        }
    }

    @Override
    public String getType() {
        return ApplicationProperties.FileStorage.Provider.FILESYSTEM.name();
    }

    @Override
    public FileEntry storeFileContent(String directoryPath, String filename, byte[] data)
        throws FileStorageException {

        Assert.notNull(directoryPath, "directory is required");
        Assert.notNull(filename, "fileName is required");
        Assert.notNull(data, "data is required");

        return doStoreFileContent(directoryPath, filename, new ByteArrayInputStream(data), true);
    }

    @Override
    public FileEntry storeFileContent(
        String directoryPath, String filename, byte[] data, boolean randomFilename)
        throws FileStorageException {

        Assert.notNull(directoryPath, "directory is required");
        Assert.notNull(filename, "fileName is required");
        Assert.notNull(data, "data is required");

        return doStoreFileContent(directoryPath, filename, new ByteArrayInputStream(data), randomFilename);
    }

    @Override
    public FileEntry storeFileContent(String directoryPath, String filename, String data)
        throws FileStorageException {

        return storeFileContent(directoryPath, filename, data, true);
    }

    @Override
    public FileEntry storeFileContent(
        String directoryPath, String filename, String data, boolean randomFilename)
        throws FileStorageException {

        Assert.notNull(directoryPath, "directory is required");
        Assert.notNull(filename, "fileName is required");
        Assert.notNull(data, "data is required");

        return doStoreFileContent(
            directoryPath, filename, new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)), randomFilename);
    }

    @Override
    public FileEntry storeFileContent(
        String directoryPath, String filename, InputStream inputStream)
        throws FileStorageException {

        return storeFileContent(directoryPath, filename, inputStream, true);
    }

    @Override
    public FileEntry storeFileContent(
        String directoryPath, String filename, InputStream inputStream,
        boolean randomFilename) throws FileStorageException {

        Assert.notNull(directoryPath, "directory is required");
        Assert.notNull(filename, "fileName is required");
        Assert.notNull(inputStream, "inputStream is required");

        return doStoreFileContent(directoryPath, filename, inputStream, randomFilename);
    }

    private FileEntry doStoreFileContent(
        String directory, String filename, InputStream inputStream, boolean randomFilename) {

        directory = StringUtils.replace(directory.replaceAll("[^0-9a-zA-Z/_]", ""), " ", "");

        Path path = resolveDirectoryPath(directory.toLowerCase());

        path = path.resolve(
            randomFilename ? generateRandomFilename(filename.substring(filename.lastIndexOf("."))) : filename);

        try {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new FileStorageException("Failed to store file " + filename, ioe);
        }

        File file = path.toFile();

        if (file.length() == 0) {
            throw new FileStorageException("Failed to store empty file " + filename);
        }

        return new FileEntry(filename, URL_PREFIX + path);
    }

    private Path resolveDirectoryPath(String directoryPath) {
        try {
            return Files.createDirectories(
                baseDirPath.resolve(TenantContext.getCurrentTenantId())
                    .resolve(directoryPath));
        } catch (IOException ioe) {
            throw new FileStorageException("Could not initialize storage", ioe);
        }
    }

    private String generateRandomFilename(String extension) {
        return UUID.randomUUID() + extension;
    }

    private String toString(Path path) {
        return path.toString();
    }
}
