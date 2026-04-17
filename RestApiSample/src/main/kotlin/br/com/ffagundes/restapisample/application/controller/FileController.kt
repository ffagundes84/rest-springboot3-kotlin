package br.com.ffagundes.restapisample.application.controller

import br.com.ffagundes.restapisample.application.data.vo.v1.UploadFileResponseVO
import br.com.ffagundes.restapisample.domain.service.FileStorageService
import com.sun.istack.logging.Logger
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@Tag(name = "File Endpoint")
@RestController
@RequestMapping("/api/file/v1")
class FileController {
    private val logger = Logger.getLogger(FileController::class.java)
    @Autowired
    private lateinit var service: FileStorageService

    private fun uploadFileService(file: MultipartFile): UploadFileResponseVO {
        val fileName = service.storeFile(file)
        val fileDownLoadUri = ServletUriComponentsBuilder.fromCurrentContextPath() // obtem: localhost:8080
            .path("/api/file/v1/uploadFile/")
            .path(fileName)
            .toUriString()

        return UploadFileResponseVO(fileName, fileDownLoadUri, file.contentType!!, file.size)
    }

    @PostMapping("/uploadFile")
    fun uploadFile(@RequestParam("file") file: MultipartFile): UploadFileResponseVO {
        return uploadFileService(file)
    }

    @PostMapping("/uploadMultipleFiles")
    fun uploadMultipleFiles(@RequestParam("files") files: Array<MultipartFile>): List<UploadFileResponseVO> {
        val uploadFilesResponseVO = arrayListOf<UploadFileResponseVO>()
        for (file in files) {
            uploadFilesResponseVO.add(uploadFileService(file))
        }
        return uploadFilesResponseVO
    }

    @GetMapping("/downLoadFile/{fileName}")
    fun downLoadFile(@PathVariable fileName: String, request: HttpServletRequest): ResponseEntity<Resource> {
        val resource = service.loadFileAsResource(fileName)
        var contentType = ""
        try {
            contentType = request.servletContext.getMimeType(resource.file.absolutePath)
        } catch(e: Exception) {
            logger.info("Could not determine file type. Exception: ${e.message}")
        }
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, """attachment; filename="${resource.filename}"""")
            .body(resource)
    }
}