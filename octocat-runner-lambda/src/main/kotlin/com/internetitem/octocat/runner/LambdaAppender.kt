package com.internetitem.octocat.runner

import ch.qos.logback.core.UnsynchronizedAppenderBase
import ch.qos.logback.core.encoder.Encoder
import ch.qos.logback.core.spi.DeferredProcessingAware
import com.amazonaws.services.lambda.runtime.LambdaRuntime
import com.amazonaws.services.lambda.runtime.LambdaRuntimeInternal

class LambdaAppender<T> : UnsynchronizedAppenderBase<T>() {

    var encoder: Encoder<T>? = null

    override fun append(event: T) {
        if (event is DeferredProcessingAware) {
            event.prepareForDeferredProcessing()
        }
        LambdaRuntime.getLogger().log(encoder?.encode(event))
    }

    companion object {
        init {
            LambdaRuntimeInternal.setUseLog4jAppender(true);
        }
    }
}