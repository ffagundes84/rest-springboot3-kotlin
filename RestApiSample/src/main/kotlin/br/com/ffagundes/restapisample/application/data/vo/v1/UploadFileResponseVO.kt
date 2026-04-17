package br.com.ffagundes.restapisample.application.data.vo.v1

class UploadFileResponseVO (
    var fileName: String = "",
    var fileDownLoadUri: String = "",
    var fileType: String = "",
    var fileSize: Long = 0
)