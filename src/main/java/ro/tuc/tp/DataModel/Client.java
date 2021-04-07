package ro.tuc.tp.DataModel;

public class Client {
    private int id;
    private int t_arrival;
    private int t_service;

    public Client(int id, int ariv, int process) {
        this.id = id;
        t_arrival = ariv;
        t_service = process;
    }

    public int getId() {
        return id;
    }

    public int getArrival() {
        return t_arrival;
    }

    public int getService() {
        return t_service;
    }

    public void setService(int serv) {
        t_service = serv;
    }

    public String toString() {
        return "(" + id + "," + t_arrival + "," + t_service + ");";
    }
}
