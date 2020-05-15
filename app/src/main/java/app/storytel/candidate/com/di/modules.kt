package app.storytel.candidate.com.di

import app.storytel.candidate.com.UiThread
import app.storytel.candidate.com.data.AppDataRepository
import app.storytel.candidate.com.data.executor.JobExecutor
import app.storytel.candidate.com.data.executor.PostExecutionThread
import app.storytel.candidate.com.data.executor.ThreadExecutor
import app.storytel.candidate.com.data.repository.AppRepository
import app.storytel.candidate.com.data.source.AppDataStore
import app.storytel.candidate.com.data.source.AppDataStoreFactory
import app.storytel.candidate.com.data.usecase.GetComments
import app.storytel.candidate.com.data.usecase.GetPostAndPhoto
import app.storytel.candidate.com.remote.AppRemoteImpl
import app.storytel.candidate.com.remote.ServiceFactory
import app.storytel.candidate.com.remote.mapper.CommentMapper
import app.storytel.candidate.com.remote.mapper.PhotoMapper
import app.storytel.candidate.com.remote.mapper.PostMapper
import app.storytel.candidate.com.ui.MainActivityViewModel
import app.storytel.candidate.com.ui.comment.DetailActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val applicationModule = module(override = true) {

    single { JobExecutor() as ThreadExecutor }
    single { UiThread() as PostExecutionThread }

    factory { CommentMapper() }
    factory { PhotoMapper() }
    factory { PostMapper() }

    factory<AppDataStore> { AppRemoteImpl(get(), get(), get(), get()) }
    factory { AppDataStoreFactory(get()) }

    factory { ServiceFactory.makeService() }
    factory<AppRepository> { AppDataRepository(get()) }

}

val mainActivityModule: Module = module {
    viewModel { MainActivityViewModel(get()) }
    factory { GetPostAndPhoto(get(), get(), get()) }
}

val detailActivityModule: Module = module {
    viewModel { DetailActivityViewModel(get()) }
    factory { GetComments(get(), get(), get()) }
}