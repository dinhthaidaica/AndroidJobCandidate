package app.storytel.candidate.com.data.source

class AppDataStoreFactory(private val dataStore: AppDataStore) {
    fun retrieveRemoteDataStore(): AppDataStore {
        return dataStore
    }
}