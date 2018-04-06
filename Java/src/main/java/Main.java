package main.java;

import main.java.client.Client;
import main.java.server.Server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        try {
            executorService.execute(() -> Server.main(args));
            executorService.execute(() -> Client.main(args));
        } finally {
            executorService.shutdown();
        }
    }

}
