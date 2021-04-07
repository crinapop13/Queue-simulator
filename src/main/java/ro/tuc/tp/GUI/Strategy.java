package ro.tuc.tp.GUI;

import ro.tuc.tp.DataModel.Client;
import ro.tuc.tp.DataModel.Coada;
import java.util.List;

public interface Strategy {
    public void addClient(List<Coada> cozi, Client c);
}
