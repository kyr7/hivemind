package io.cyber.hivemind.service

import io.cyber.hivemind.*
import io.cyber.hivemind.constant.*
import io.cyber.hivemind.util.*
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.coroutines.withContext
import java.io.*
import java.util.*

/**
 * User: kirillskiy
 * Date: 22/07/2018
 * Time: 13:56
 */
interface FileService {
    /**
     * grab a zip with the given resource
     * @return the zip with compressed script or data
     */
    suspend fun getZip(type: ResourceType, id: UUID, refresh: Boolean = false): File

    /**
     * store a byte channel
     */
    suspend fun storeData(id: UUID, body: ByteReadChannel): DataMeta

    /**
     * store and optionally unzip a resource
     */
    suspend fun storeScript(id: UUID, scriptZip: InputStream): ScriptMeta

    /**
     * delete a resource
     * @return true if deleted
     */
    fun delete(type: ResourceType, id: UUID): Boolean

    fun notifyModelMetaUpdate(meta: ModelMeta)

    //todo search resources
}


class DiskFileServiceImpl : FileService {

    private fun prepareDirToWrite(type: ResourceType, id: UUID): File {
        val baseDir = getBaseDir(type)
        val dir = "$baseDir$id$SEP"
        when (type) {
            ResourceType.SCRIPT, ResourceType.MODEL -> File(dir).deleteRecursively()
        }
        val resourceDir = File(dir)
        if (!resourceDir.exists()) {
            resourceDir.mkdirs()
        }
        return resourceDir
    }

    override suspend fun getZip(type: ResourceType, id: UUID, refresh: Boolean): File {
        val zipName = "${getBaseDir(type)}$id$SEP$ZIP_NAME"
        if (!File(zipName).exists() || refresh) {
            makeZip(type, id)
        }
        return File(zipName)
    }


    @KtorExperimentalAPI
    override suspend fun storeData(id: UUID, body: ByteReadChannel): DataMeta {
        val dir = prepareDirToWrite(ResourceType.DATA, id)
        val dataPieceName = getNextDataFileName(dir)
        body.writeToFile(File(dataPieceName))
        //todo load data created date
        return DataMeta(id, Date(), Date())
    }

    @KtorExperimentalAPI
    override suspend fun storeScript(id: UUID, scriptZip: InputStream): ScriptMeta {
        return withContext(Dispatchers.IO) {
            val dir = prepareDirToWrite(ResourceType.SCRIPT, id)
            val zipDestination = File(dir, ZIP_NAME)
            scriptZip.copyTo(FileOutputStream(zipDestination))
            unzipData(dir, zipDestination)
            return@withContext ScriptMeta(id, Date())
        }
    }

    override fun delete(type: ResourceType, id: UUID): Boolean {
        val resourceDirName = "${getBaseDir(type)}$id$SEP$id"
        return File(resourceDirName).deleteRecursively()
    }

    override fun notifyModelMetaUpdate(meta: ModelMeta) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

