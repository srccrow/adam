/*
 * Copyright (C) 2021 Anton Malinskiy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.malinskiy.adam.request.sync.v1

import com.malinskiy.adam.Const
import com.malinskiy.adam.request.sync.base.BasePullFileRequest
import com.malinskiy.adam.transport.AndroidReadChannel
import com.malinskiy.adam.transport.AndroidWriteChannel
import kotlinx.coroutines.Dispatchers
import java.io.File
import kotlin.coroutines.CoroutineContext

/**
 * @param coroutineContext if you don't specify your context then you'll have no control over the `wait for file to finish writing`: closing the channel doesn't close the underlying resources
 */
class PullFileRequest(
    private val remotePath: String,
    local: File,
    size: Long? = null,
    coroutineContext: CoroutineContext = Dispatchers.IO
) : BasePullFileRequest(remotePath, local, size, coroutineContext) {
    override suspend fun handshake(readChannel: AndroidReadChannel, writeChannel: AndroidWriteChannel) {
        super.handshake(readChannel, writeChannel)
        writeChannel.writeSyncRequest(Const.Message.RECV_V1, remotePath)
    }
}