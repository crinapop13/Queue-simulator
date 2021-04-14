package ro.tuc.tp.DataModel;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Coada implements Runnable {
    private BlockingQueue<Client> clienti;
    private AtomicInteger waitingTime;

    public Coada() {
        clienti = new LinkedBlockingDeque<>();
        waitingTime = new AtomicInteger();
    }

    public void adaugaClient(Client c) {
        clienti.add(c);
        waitingTime.getAndAdd(c.getService());
    }

    public void run() {
        while(true) {
            while (!clienti.isEmpty()) {
                try {
                    Thread.sleep(1000);   //timpul e in milisecunde
                    waitingTime.getAndDecrement();  //scadem din timpul de asteptat
                    int timp = clienti.peek().getService();
                    timp--;
                    clienti.peek().setService(timp);
                    if(clienti.peek().getService() == 0) {
                        clienti.take();
                    }
                } catch (Exception e) {}
            }
        }
    }

    public BlockingQueue<Client> getClients() {
        return clienti;
    }

    public int getWaitingTime() {
        return waitingTime.intValue();
    }

    public int getSizeOfQueue() {
        return clienti.size();
    }

    public String toString() {
        if(clienti.peek() == null && waitingTime.intValue() == 0) {
            return "closed";
        } else {
            return clienti.toString();
        }
    }
}
