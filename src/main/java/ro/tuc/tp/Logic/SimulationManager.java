package ro.tuc.tp.Logic;

import ro.tuc.tp.DataModel.Client;
import ro.tuc.tp.DataModel.Coada;
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
        /*Scanner s = new Scanner(System.in);
        System.out.println("Introduceti timpul de simulare: ");
        timeLimit = s.nextInt();
        System.out.println("Introduceti nr. de clienti: ");
        numberOfClients = s.nextInt();
        System.out.println("Introduceti nr. de cozi: ");
        numberOfQueues = s.nextInt();
        System.out.println("Introduceti timpul minim de servire: ");
        minProcessingTime = s.nextInt();
        System.out.println("Introduceti timpul maxim de servire: ");
        maxProcessingTime = s.nextInt();
        System.out.println("Introduceti timpul minim de sosire: ");
        minArrivingTime = s.nextInt();
        System.out.println("Introduceti timpul maxim de sosire: ");
        maxArrivingTime = s.nextInt();*/

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
        int clientiPerCoada = 0;
        while(currentTime < timeLimit && !generatedClients.isEmpty()) {
            Iterator<Client> it = generatedClients.iterator();
            while(it.hasNext()) {
                Client c = it.next();
                if (c.getArrival() == currentTime) {
                    scheduler.dispatchClient(c);
                    clientiPerCoada++;
                    it.remove();
                }
            }
            try {
                myWriter.write(afiseazaRezultat(currentTime));
                frame.setResult(afiseazaRezultat(currentTime));
            } catch(Exception e){}
            System.out.println(afiseazaRezultat(currentTime));
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch(Exception e) {}
        }
        try {
            myWriter.write("\r\nAverage waiting time: " + clientiPerCoada / (1.0*currentTime*numberOfQueues));
            myWriter.flush();
            myWriter.close();
        } catch(Exception e){}
        System.out.println("\r\nAverage waiting time: " + clientiPerCoada / (1.0*currentTime*numberOfQueues));
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
