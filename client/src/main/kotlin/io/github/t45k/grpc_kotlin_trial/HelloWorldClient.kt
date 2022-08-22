package io.github.t45k.grpc_kotlin_trial

import GreeterGrpc
import Helloworld.HelloRequest
import io.grpc.ManagedChannelBuilder
import java.util.concurrent.TimeUnit

fun main() {
    val channel = ManagedChannelBuilder.forTarget("localhost:50051")
        .usePlaintext()
        .build()
    val blockingStub = GreeterGrpc.newBlockingStub(channel)

    val helloRequest = HelloRequest.newBuilder()
        .setName("client")
        .build()
    val helloReply = blockingStub.sayHello(helloRequest)
    println(helloReply.message)

    val helloAgainReply = blockingStub.sayHelloAgain(helloRequest)
    println(helloAgainReply.message)

    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
}
