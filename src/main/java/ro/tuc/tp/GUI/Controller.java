package ro.tuc.tp.GUI;

import ro.tuc.tp.Logic.SimulationManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private SimulationFrame frame;

    public Controller(SimulationFrame f) {
        this.frame = f;

        frame.addStartListener(new StartListener());
        frame.addClearListener(new ClearListener());
    }

    class StartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SimulationManager.startProgram();
        }
    }

    class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.clear();
        }
    }
}
