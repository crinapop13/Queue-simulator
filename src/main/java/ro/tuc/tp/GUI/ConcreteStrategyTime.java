package ro.tuc.tp.GUI;

import ro.tuc.tp.DataModel.Client;
import ro.tuc.tp.DataModel.Coada;
import java.util.List;

public class ConcreteStrategyTime implements Strategy{
    public void addClient(List<Coada> cozi, Client c) {
        int poz = 0;
        int min = cozi.get(0).getWaitingTime();
        for(int i = 1; i < cozi.size(); i++) {
            if(min > cozi.get(i).getWaitingTime()) {
                min = cozi.get(i).getWaitingTime();
                poz = i;   //salvam pozitia cozii cu cel mai mic timp de asteptare
            }
        }
        cozi.get(poz).adaugaClient(c);
    }
}
