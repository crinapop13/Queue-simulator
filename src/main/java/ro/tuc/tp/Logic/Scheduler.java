package ro.tuc.tp.Logic;

import ro.tuc.tp.DataModel.Client;
import ro.tuc.tp.DataModel.Coada;
import ro.tuc.tp.GUI.ConcreteStrategyQueue;
import ro.tuc.tp.GUI.ConcreteStrategyTime;
import ro.tuc.tp.GUI.SelectionPolicy;
import ro.tuc.tp.GUI.Strategy;

import java.util.*;

public class Scheduler {
    private List<Coada> cozi;
    private int maxNrCozi;
    private int maxClientPerCoada;
    private Strategy strategy;

    public Scheduler(int maxNrCozi, int maxClientPerCoada) {
        this.maxNrCozi = maxNrCozi;
        this.maxClientPerCoada = maxClientPerCoada;
        this.cozi = new ArrayList<Coada>(maxNrCozi);
        strategy = new ConcreteStrategyTime();
        ArrayList<Thread> t = new ArrayList<Thread>(maxNrCozi);
        for(int i = 0; i < maxNrCozi; i++) {
            Coada c = new Coada();
            cozi.add(c);
            t.add(new Thread(c));
            t.get(i).start();
        }
    }

    public void changeStrategy(SelectionPolicy policy) {
        if(policy == SelectionPolicy.SHORTEST_TIME) {
            this.strategy = new ConcreteStrategyTime();
        }
        if(policy == SelectionPolicy.SHORTHEST_QUEUE) {
            this.strategy = new ConcreteStrategyQueue();
        }
    }

    public void dispatchClient(Client c) {
        strategy.addClient(cozi,c);
    }

    public List<Coada> getCoada() {
        return cozi;
    }

    public int getNumberOfClienti() {

        int clienti = 0;
        for(Coada c: cozi) {
            clienti += c.getSizeOfQueue();
        }
        return clienti;
    }

    public boolean isEmpty() {
        for (int i = 0; i < cozi.size(); i++) {
            if(!cozi.get(i).getClients().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public int getMaxNrCozi() {
        return maxNrCozi;
    }

    public String toString() {
        String rez = "";
        for(int i = 0; i < maxNrCozi; i++) {
            rez = rez + "Queue " + i + ": " + cozi.get(i).toString() + "\r\n";
        }
       return rez;
    }
}
