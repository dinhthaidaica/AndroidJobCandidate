package app.storytel.candidate.com.remote.mapper

import app.storytel.candidate.com.data.model.Photo
import app.storytel.candidate.com.remote.model.RemotePhoto

class PhotoMapper : RemoteMapper<RemotePhoto, Photo> {
    override fun mapFromRemote(type: RemotePhoto): Photo {
        return Photo(
            type.albumId,
            type.id,
            type.title,
            type.url,
            type.thumbnailUrl
        )
    }

}