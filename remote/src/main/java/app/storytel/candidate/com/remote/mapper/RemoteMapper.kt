package app.storytel.candidate.com.remote.mapper

/**
 * Map from Remote object to Data object
 */
interface RemoteMapper<in R, out D> {
    fun mapFromRemote(type: R): D
}