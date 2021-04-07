package ro.tuc.tp.GUI;

import ro.tuc.tp.DataModel.Client;
import ro.tuc.tp.DataModel.Coada;
import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    public void addClient(List<Coada> cozi, Client c) {
        int poz = 0;
        int minNbClienti = cozi.get(0).getSizeOfQueue();
        for(int i = 1; i < cozi.size(); i++) {
            if (minNbClienti > cozi.get(i).getSizeOfQueue()) {
                minNbClienti = cozi.get(i).getSizeOfQueue();
            }
        }
        cozi.get(poz).adaugaClient(c);
    }
}
