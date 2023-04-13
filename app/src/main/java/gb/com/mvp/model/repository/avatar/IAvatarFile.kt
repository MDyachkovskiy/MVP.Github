package gb.com.mvp.model.repository.avatar

import android.graphics.Bitmap
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.io.File

interface IAvatarFile {
    fun getDir(): Observable<File>
    fun saveFile(file: File?, bitmap: Bitmap?): Completable
}