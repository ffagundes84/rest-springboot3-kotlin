package br.com.ffagundes.restapisample.domain.service

import br.com.ffagundes.restapisample.application.config.FileStorageConfig
import br.com.ffagundes.restapisample.application.exceptions.FileStorageException
import br.com.ffagundes.restapisample.application.exceptions.MyFileNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class FileStorageService @Autowired constructor (fileStorageConfig: FileStorageConfig) {
    private val fileStorageLocation = Paths.get(fileStorageConfig.uploadDir)
        .toAbsolutePath()
        .normalize()
    init {
        try {
            Files.createDirectories(fileStorageLocation)
        } catch(e: Exception) {
            throw FileStorageException("Failed to create the directory", e)
        }
    }

    fun storeFile(file: MultipartFile): String {
        val fileName = StringUtils.cleanPath(file.originalFilename!!)
        return try {
            if (fileName.contains(".."))
                throw FileStorageException("Invalid file name: $fileName")

            val targetLocation = fileStorageLocation.resolve(fileName)
            Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
            fileName
        } catch (e: Exception) {
            throw FileStorageException("Failed to storage file $fileName", e)
        }
    }

    fun loadFileAsResource(fileName: String): Resource {
        return try {
            val filePath = fileStorageLocation.resolve(fileName).normalize()
            val resource = UrlResource(filePath.toUri())
            if (resource.exists()) resource
            else throw MyFileNotFoundException("File not found: $fileName")
        } catch(e: Exception) {
            throw MyFileNotFoundException("File not found: $fileName", e)
        }
    }
}