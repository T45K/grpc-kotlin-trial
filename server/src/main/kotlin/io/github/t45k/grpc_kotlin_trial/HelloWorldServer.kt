package io.github.t45k.grpc_kotlin_trial

import GreeterGrpc
import Helloworld.HelloReply
import Helloworld.HelloRequest
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver

fun main() {
    val server = HelloWorldServer()
    server.start()
    server.blockUntilShutdown()
}

private class HelloWorldServer {

    private lateinit var server: Server

    fun start() {
        server = ServerBuilder.forPort(50051)
            .addService(GreeterImpl())
            .build()
            .start()
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                this@HelloWorldServer.stop()
            }
        })
    }

    private fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }
}

private class GreeterImpl : GreeterGrpc.GreeterImplBase() {
    override fun sayHello(request: HelloRequest, responseObserver: StreamObserver<HelloReply>) {
        val reply = HelloReply.newBuilder()
            .setMessage("Hello, ${request.name}")
            .build()

        responseObserver.onNext(reply)
        responseObserver.onCompleted()
    }

    override fun sayHelloAgain(
        request: HelloRequest,
        responseObserver: StreamObserver<HelloReply>
    ) {
        val reply = HelloReply.newBuilder()
            .setMessage("Hello again, ${request.name}")
            .build()

        responseObserver.onNext(reply)
        responseObserver.onCompleted()
    }
}
