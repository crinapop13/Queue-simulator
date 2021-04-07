package ro.tuc.tp.GUI;

import ro.tuc.tp.Logic.SimulationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame {
    private JPanel content = new JPanel();
    private JPanel all = new JPanel();
    private JPanel vertL = new JPanel();
    private JPanel vertR = new JPanel();
    private JPanel sub = new JPanel();
    private JPanel sub1 = new JPanel();
    private JLabel N = new JLabel("Introduceti nr. de clienti:");
    private JLabel Q = new JLabel("Introduceti nr. de cozi:");
    private JLabel t = new JLabel("Introduceti timpul de simulare:");
    private JLabel arrMin = new JLabel("Introduceti timpul minim de sosire al clientilor:");
    private JLabel arrMax = new JLabel("Introduceti timpul maxim de sosire al clientilor:");
    private JLabel procMin = new JLabel("Introduceti timpul minim de procesare al clientilor:");
    private JLabel procMax = new JLabel("Introduceti timpul maxim de procesare al clientilor:");
    private JTextField nr = new JTextField(15);
    private JTextField qu = new JTextField(15);
    private JTextField min1 = new JTextField(15);
    private JTextField min2 = new JTextField(15);
    private JTextField max1 = new JTextField(15);
    private JTextField max2 = new JTextField(15);
    private JTextField sim = new JTextField(15);
    private JButton start = new JButton("Start");
    private JButton clear = new JButton("Clear");
    private JTextArea text = new JTextArea(20,20);
    //JScrollPane scroll = new JScrollPane(text);

    Controller controller = new Controller(this);

    public SimulationFrame(String name) {
        super(name);
        all.setLayout(new BoxLayout(all,BoxLayout.Y_AXIS));
        content.setLayout(new BoxLayout(content,BoxLayout.X_AXIS));
        vertL.setLayout(new BoxLayout(vertL,BoxLayout.Y_AXIS));
        vertR.setLayout(new BoxLayout(vertR,BoxLayout.Y_AXIS));
        sub.setLayout(new BoxLayout(sub,BoxLayout.X_AXIS));
        sub1.setLayout(new BoxLayout(sub1,BoxLayout.X_AXIS));
        content.add(vertL);
        content.add(Box.createRigidArea(new Dimension(20,0)));
        content.add(vertR);
        all.add(content);
        all.add(sub);
        all.add(sub1);

        vertL.add(N);
        vertR.add(nr);

        vertL.add(Q);
        vertR.add(qu);

        vertL.add(t);
        vertR.add(sim);

        vertL.add(arrMin);
        vertR.add(min1);

        vertL.add(arrMax);
        vertR.add(max1);

        vertL.add(procMin);
        vertR.add(min2);

        vertL.add(procMax);
        vertR.add(max2);

        sub.add(start);
        sub.add(clear);
        sub1.add(text);

        this.add(all);
    }

    public int getN() {
        return Integer.parseInt(nr.getText());
    }
    public int getQ() {
        return Integer.parseInt(qu.getText());
    }
    public int getArrMin() {
        return Integer.parseInt(min1.getText());
    }
    public int getArrMax() {
        return Integer.parseInt(max1.getText());
    }
    public int getProcMin() {
        return Integer.parseInt(min2.getText());
    }
    public int getProcMax() {
        return Integer.parseInt(max2.getText());
    }
    public int getSimTime() {
        return Integer.parseInt(sim.getText());
    }
    public void setResult(String s) {
        text.setText(s);
    }
    public void clear() {
        nr.setText("");
        qu.setText("");
        min1.setText("");
        max1.setText("");
        min2.setText("");
        max2.setText("");
        sim.setText("");
        text.setText("");
    }
    public void addStartListener(ActionListener a) {
        start.addActionListener(a);
    }
    public void addClearListener(ActionListener a) {
        clear.addActionListener(a);
    }
}
