package ro.tuc.tp.Logic;

import ro.tuc.tp.DataModel.Client;
import java.util.Comparator;

public class Sort implements Comparator<Client> {
    public int compare(Client c1, Client c2) {
        return c1.getArrival() - c2.getArrival();
    }
}
