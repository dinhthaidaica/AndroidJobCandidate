package app.storytel.candidate.com.data.executor

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler: Scheduler
}