package com.concurrency.examples;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConcurrencyExample1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> completableFuture = new CompletableFuture<String>();
        completableFuture.get(1, TimeUnit.SECONDS);
        completableFuture.complete("Future's Result");
    }
}
