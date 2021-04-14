package ro.tuc.tp.Logic;

import ro.tuc.tp.DataModel.Client;
import ro.tuc.tp.GUI.SelectionPolicy;
import ro.tuc.tp.GUI.SimulationFrame;
import javax.swing.*;
import java.io.FileWriter;
import java.util.*;

public class SimulationManager implements Runnable{
    public int timeLimit;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int maxArrivingTime;
    public int minArrivingTime;
    public int numberOfQueues;
    public int numberOfClients;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    private Scheduler scheduler;
    private static SimulationFrame frame;
    private List<Client> generatedClients;
    private FileWriter myWriter;

    public SimulationManager() {
        timeLimit = frame.getSimTime();
        numberOfClients = frame.getN();
        numberOfQueues = frame.getQ();
        minArrivingTime = frame.getArrMin();
        maxArrivingTime = frame.getArrMax();
        minProcessingTime = frame.getProcMin();
        maxProcessingTime = frame.getProcMax();

        scheduler = new Scheduler(numberOfQueues,numberOfClients);
        scheduler.changeStrategy(selectionPolicy);

        generateNRandomClients();
        try {
            myWriter = new FileWriter("log_file.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void generateNRandomClients() {
        generatedClients = new ArrayList<Client>(numberOfClients);
        Random randArriveTime = new Random();
        Random randProcessTime = new Random();
        int rand1, rand2;
        for(int i = 1; i <= numberOfClients; i++) {
            rand1 = randArriveTime.nextInt(maxArrivingTime - minArrivingTime) + minArrivingTime;
            rand2 = randProcessTime.nextInt(maxProcessingTime - minProcessingTime) + minProcessingTime;
            Client c = new Client(i,rand1,rand2);
            generatedClients.add(c);
        }
        Collections.sort(generatedClients,new Sort());
    }

    public void run() {
        int currentTime = 0;
        int servTime = 0;
        int size = generatedClients.size();
        int peekHour = 0, max = -1;
        Iterator<Client> it = generatedClients.iterator();
        Client c = it.next();
        while(currentTime < timeLimit) {
            while(c.getArrival() == currentTime) {
                scheduler.dispatchClient(c);
                servTime += c.getService();
                it.remove();
                if(it.hasNext()) {
                    c = it.next();
                } else {
                    break;
                }
            }
            try {
                myWriter.write(afiseazaRezultat(currentTime));
                frame.setResult(afiseazaRezultat(currentTime));
            } catch(Exception e){}
            //System.out.println(afiseazaRezultat(currentTime));
            int tot = scheduler.getTotalWaitingTime();
            if(max < tot) {
                max = tot;
                peekHour = currentTime;
            }
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch(Exception e) {}

            if(c.getService() == 0 && scheduler.isEmpty()) {
                break;
            }
        }

        try {
            myWriter.write("\r\nAverage service time: " + servTime / (1.0*size) + "\n");
            myWriter.write("\r\nPeek hour: " + peekHour + "\n");
            frame.setResult("\r\nAverage service time: " + servTime / (1.0*size) + "\n" + "Peek hour: " + peekHour + "\n");
            myWriter.flush();
            myWriter.close();
        } catch(Exception e){}
    }

    public String afiseazaRezultat(int t) {
        String rez = "";
        rez = rez + "Time " + t + "\r\n" + "Waiting clients: ";

        for(Client c: generatedClients) {
            rez += c.toString();
        }
        rez += "\r\n" + scheduler.toString();
        return rez;
    }

    public static void startProgram() {
        SimulationManager gen = new SimulationManager();
        Thread t = new Thread(gen);
        t.start();
    }

    public static void main(String[] args) {
        frame = new SimulationFrame("Queues");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(400,500);
        frame.setVisible(true);
    }
}
