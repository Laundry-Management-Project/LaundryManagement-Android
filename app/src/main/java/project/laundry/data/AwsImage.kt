package project.laundry.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ListObjectsRequest
import com.amazonaws.services.s3.model.ObjectListing
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URI
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazonaws.AmazonClientException
import com.amazonaws.mobileconnectors.s3.transferutility.*
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import kotlinx.coroutines.*
import java.io.IOException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger

class AwsImage(val context:Context) {
    private val s3Client = AmazonS3Client(BasicAWSCredentials(AwsClient.accessKey, AwsClient.secretKey))


    init {
        s3Client.setRegion(Region.getRegion(Regions.AP_NORTHEAST_2))
        TransferNetworkLossHandler.getInstance(context)
    }

    private val mBucketName = "laundry-management"
    fun uploadImg(id: String, files: List<Uri>) {

        val transferUtility = TransferUtility.builder()
            .context(context)
            .defaultBucket(mBucketName)
            .s3Client(s3Client)
            .build()

        var i = 1

        for (filePath in files) {

            val objectKey = "$id/$i.jpg"

            val inputStream = context.contentResolver.openInputStream(filePath)
            inputStream?.use { stream ->
                val file = createFileFromInputStream(stream)
                run {
                    val uploadObserver = transferUtility.upload(mBucketName, objectKey, file)
                    uploadObserver.setTransferListener(object : TransferListener {
                        override fun onStateChanged(id: Int, state: TransferState) {
                            if (state == TransferState.COMPLETED) {
                                Log.d("uploadObserver", "state Complete")
                            } else if (state == TransferState.FAILED) {

                                Log.d("uploadObserver", "state Fail")
                            }
                        }

                        override fun onProgressChanged(
                            id: Int,
                            bytesCurrent: Long,
                            bytesTotal: Long
                        ) {
                            Log.d(
                                "uploadProgress",
                                "$id / " + (bytesCurrent / bytesTotal * 100).toInt()
                                    .toString()
                            )
                        }

                        override fun onError(id: Int, ex: Exception) {
                            Log.d("uploadObserver", ex.toString())
                            // 오류 발생 시 처리 로직을 수행합니다.
                        }
                    })
                    i++
                }
                // /external/images/ 와 같은 경로는 바로 s3에 업로드 불가능으로 인해 변환

            }
        }
    }

    // 받아온 id로 s3에 객체 찾아서 bitmap 이미지로 반환
    suspend fun getImage(id:String) : List<Bitmap> = withContext(Dispatchers.Default) {

        val transferUtility = TransferUtility.builder()
            .context(context)
            .s3Client(s3Client)
            .build()

        val bitmaps: ArrayList<Bitmap> = ArrayList()

        val s3Objects = s3Client.listObjects(mBucketName,id).objectSummaries

        val deferred = CompletableDeferred<List<Bitmap>>()

        // 가져온 S3 객체 목록을 처리하는 로직을 추가하세요
        for (s3Object in s3Objects) {

            var bitmap: Bitmap
            val objectKey = s3Object.key
            val file = File(context.filesDir.path + "/"+objectKey)
            val downloadObserver = transferUtility.download(mBucketName, objectKey, file)

            Log.d("getImage1", s3Objects.size.toString())
            downloadObserver.setTransferListener(object : TransferListener {
                override fun onStateChanged(id: Int, state: TransferState) {
                    if (state == TransferState.COMPLETED) {
                        Log.d("getImage2", "ok")
                        bitmap = BitmapFactory.decodeFile(file.absolutePath)
                        bitmaps.add(bitmap)
                        file.delete()
                        if (bitmaps.size == s3Objects.size) {
                            deferred.complete(bitmaps)
                        }
                    }
                }

                override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                    val progress = (bytesCurrent.toDouble() / bytesTotal.toDouble() * 100).toInt()
                    // 다운로드 진행률 업데이트
                    Log.d("getImage2", "fail")
                    // 진행 상황에 따른 처리를 여기에 추가하세요
                }

                override fun onError(id: Int, ex: Exception?) {
                    // 다운로드 오류 발생 시 호출되는 콜백 메서드
                    Log.d("getImage2", "error")
                    // 오류 처리를 여기에 추가하세요
                }
            })


        }

        return@withContext deferred.await()
    }
//    private suspend fun getObjectsAsync(bucketName_ : String, folderPath:String): ObjectListing {
//        return kotlinx.coroutines.withContext(Dispatchers.IO) {
//            val listObjectsRequest = ListObjectsRequest().apply {
//                bucketName = bucketName_
//                prefix = folderPath // 폴더 경로 설정
//            }
//            s3Client.listObjects(listObjectsRequest)
//        }
//    }
    private fun createFileFromInputStream(inputStream: InputStream): File {
        val fileName = "temp_file_${System.currentTimeMillis()}.tmp"
        val file = File(context.cacheDir, fileName)
        val outputStream = FileOutputStream(file)
        val buffer = ByteArray(4096)
        var bytesRead: Int
        try {
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
        } catch (e: IOException) {
            // 파일 생성 중 오류가 발생한 경우 예외 처리
            e.printStackTrace()
        } finally {
            try {
                outputStream.close()
            } catch (e: IOException) {
                // 스트림 닫기 중 오류가 발생한 경우 예외 처리
                e.printStackTrace()
            }
        }
        return file
    }
}