package gb.com.mvp.model.repository.avatar

import android.graphics.Bitmap
import android.os.Environment
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class AvatarFile(): IAvatarFile {

    override fun getDir(): Observable<File> {
        val directory = Environment.getExternalStorageDirectory()
        val appImagesDir = File(directory, "MyImages")
        if(!appImagesDir.exists()) {
            appImagesDir.mkdirs()
        }
        return Observable.just(appImagesDir)
    }

    override fun saveFile(file: File?, bitmap: Bitmap?): Completable {
        return Completable.create { emitter ->
            file?.let {
                if (it.exists()) {
                    it.delete()
                }
                var outputStream: OutputStream? = null
                try {
                    outputStream = FileOutputStream(file)
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.flush()
                    emitter.onComplete()
                } catch (e: Exception) {
                    e.printStackTrace()
                    emitter.onError(java.lang.RuntimeException("Save failed"))
                } finally {
                    outputStream?.close()
                }
            }
        }
    }
}