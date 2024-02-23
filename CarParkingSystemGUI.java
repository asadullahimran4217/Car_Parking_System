import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
public class CarParkingSystemGUI {
    private LinkedList<vehicle> list1 = new LinkedList<>();
    private LinkedList<vehicle> list2 = new LinkedList<>();
    private JFrame frame;
    private JTextArea textArea;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CarParkingSystemGUI().createAndShowGUI());
    }
    private void createAndShowGUI() {
        frame = new JFrame("Car Parking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton enterButton = new JButton("Enter Garage");
        JButton exitButton = new JButton("Exit from Garage");
        JButton displayButton = new JButton("Display Car Lists");
        JButton exitMenuButton = new JButton("Exit Menu");
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterGarage();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitGarage();
            }
        });
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCarLists();
            }
        });
        exitMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitMenu();
            }
        });
        buttonPanel.add(enterButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(exitMenuButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    private void enterGarage() {
        if (list1.size() <= 9) {
            int numOfCars = 0;
            vehicle vehicleID = null;
            for (int h = 0; h < list1.size(); h++) {
                vehicleID = list1.get(h);
                numOfCars = vehicleID.no;
                textArea.append("Car numbers in the Car Park now " + numOfCars + "\n");
            }
            textArea.append("\nCar Park has another : " + (10 - numOfCars) + " vacancies\n");
            textArea.append("\t" + "...Please come next car...\n\n");
            textArea.append("Car number " + (list1.size() + 1) + " is the next to enter garage\n");
            String input = JOptionPane.showInputDialog("Enter the car number given above:");
            int carNum1 = Integer.parseInt(input);
            if ((list1.size() + 1) == carNum1) {
                list1.add(new vehicle(carNum1));
                textArea.append("Car entered the garage.\n");
            } else {
                textArea.append("Please enter correct car number.\n");
            }
        } else {
            textArea.append("\nSorry!!!!\n");
            textArea.append("No parking space available. Please wait until a vacancy comes\n");
            int inputNum2 = JOptionPane.showOptionDialog(frame,
                    "Cars are waiting to enter garage. Would you like to enter the waiting list?",
                    "Waiting List",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                   null);
            switch (inputNum2) {
                case JOptionPane.YES_OPTION:
                    textArea.append("Car number " + (list2.size() + 11) + " is the next to enter garage\n");
                    String waitingCarNum = JOptionPane.showInputDialog("Enter the car number given above:");
                   int waitingCarNumInt = Integer.parseInt(waitingCarNum);
                    if ((list2.size() + 11) == waitingCarNumInt) {
                        list2.add(new vehicle(waitingCarNumInt));
                        textArea.append("Car added to waiting list.\n");
                    } else {
                        textArea.append("Please enter correct car number.\n");
                    }
                    break;
                case JOptionPane.NO_OPTION:
                    textArea.append("Thank you\n");
                    break;
            }
        }
    }
    private void exitGarage() {
        if (list1.size() == 0) {
            textArea.append("Garage is empty. If you wish you can Enter your car now\n");
        } else {
            String carNumbers = "Car numbers in the Car park. Choose yours:\n";
            for (vehicle qsa : list1) {
                carNumbers += qsa.no + "\n";
            }
            String removeCar = JOptionPane.showInputDialog(carNumbers + "\nEnter the number of your car:");
            int which = Integer.parseInt(removeCar);
            for (int h = 0; h < list1.size(); h++) {
                vehicle qa = list1.get(h);
                if (qa.no != which) {
                    continue;
                } else {
                    for (int u = 0; u < list1.size() - 1; u++) {
                        vehicle kl = list1.get(u);
                        if (kl.no != which) {
                            kl.mvs += 2;
                        } else
                            break;
                    }
                    for (int v = list1.size(); (list1.get(h).no != which); v--) {
                        list1.get(h).mvs += 1;
                    }
                    textArea.append("Moves: " + list1.get(h).incmoves(1) + "\n");
                    list1.remove(h);
                    break;
                }
            }
            if (list2.size() > 0) {
                list1.add(new vehicle(list2.getFirst().no));
                list2.remove(0);
                textArea.append("New car list in car park: " + list1 + "\n\n");
            } else {
                textArea.append("No cars in the waiting list to enter the garage\n");
            }
        }
    }
    private void displayCarLists() {
        int displayNum = JOptionPane.showOptionDialog(frame,
                "What do you want to see?",
                "Display Options",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Main garage", "Waiting list"},
                null);
        try {
            switch (displayNum) {
                case JOptionPane.YES_OPTION:
                    String mainGarageList = "Cars in the main garage now:\n";
                    for (vehicle car : list1) {
                        mainGarageList += "Car in Park now " + car.no + "\n";
                    }
                    textArea.append(mainGarageList);
                    break;
                case JOptionPane.NO_OPTION:
                    String waitingList = "Cars in the waiting list now:\n";
                    for (vehicle car : list2) {
                        waitingList += car.no + " is in the Waiting list now\n";
                    }
                    textArea.append(waitingList);
                    break;
            }
        } catch (Exception e) {
            textArea.append("You have entered the wrong option number. Please check again\n");
        }
    }
    private void exitMenu() {
        textArea.append("Exiting the menu...\n");
    }
    private class vehicle {
        int no;
        int mvs;
        public vehicle(int abc) {
            no = abc;
            mvs = 0;
        }
        public int incmoves(int x) {
            return (mvs + x);
        }
    }
}
