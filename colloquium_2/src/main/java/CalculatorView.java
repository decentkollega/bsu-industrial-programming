import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class CalculatorView extends JFrame {
    private JTextField display;
    private static final Font BOLD_FONT = new Font(Font.MONOSPACED, Font.ITALIC, 26);

    private boolean startNumber = true;
    private String prevOperation = "=";
    private final CalculatorEngine engine = new CalculatorEngine();
    ActionListener operationListener = new OperationListener();

    public CalculatorView() {
        display = getDisplayField();
        JPanel operators = getOperatorsPanel();
        JPanel operations = getOperationsPanel();
        JPanel buttons = getButtonsPanel();

        setContentPane(organize(display, operators, operations, buttons));

        setPreferredSize(new Dimension(320, 300));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private JTextField getDisplayField() {
        display = new JTextField("0", 18);
        display.setFont(BOLD_FONT);
        display.setHorizontalAlignment(JTextField.RIGHT);
        return display;
    }

    private JPanel getOperatorsPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 1, 2, 2));
        JButton clearButton = new JButton("C");
        clearButton.addActionListener(new ClearKeyListener());
        controlPanel.add(clearButton);
        JButton equalButton = new JButton("=");
        equalButton.addActionListener(operationListener);
        controlPanel.add(equalButton);
        return controlPanel;
    }

    private JPanel getOperationsPanel() {
        JPanel operationPanel = new JPanel();
        String[] operationPanelNames = new String[]{
                "+", "-", "x", "/"
        };
        operationPanel.setLayout(new GridLayout(2, 2, 2, 2));
        for (String operationPanelName : operationPanelNames) {
            JButton button = new JButton(operationPanelName);
            operationPanel.add(button);
            button.addActionListener(operationListener);
        }
        return operationPanel;
    }

    private JPanel getButtonsPanel() {
        JPanel buttonPanel = new JPanel();
        ActionListener numberListener = new NumberKeyListener();
        String[] buttonPanelNames = new String[]{
                "7", "8", "9", "4", "5", "6", "1", "2", "3", "0"
        };
        buttonPanel.setLayout(new GridLayout(4, 3, 2, 2));
        for (String buttonPanelName : buttonPanelNames) {
            JButton button = new JButton(buttonPanelName);
            if (buttonPanelName.equals(" ")) {
                button.setEnabled(false);
            }
            button.addActionListener(numberListener);
            buttonPanel.add(button);
        }
        return buttonPanel;
    }


    private JPanel organize(JTextField display, JPanel operators, JPanel operations, JPanel buttons) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(display, BorderLayout.NORTH);
        mainPanel.add(operators, BorderLayout.SOUTH);
        mainPanel.add(operations, BorderLayout.EAST);
        mainPanel.add(buttons, BorderLayout.CENTER);
        return mainPanel;
    }


    void actionClear() {
        startNumber = true;
        display.setText("0");
        prevOperation = "=";
        engine.equal("0");
    }

    class OperationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (startNumber) {
                actionClear();
                display.setText("Wrong operation...");
            } else {
                startNumber = true;
                try {
                    String displayText = display.getText();
                    switch (prevOperation) {
                        case "+" -> engine.add(displayText);
                        case "-" -> engine.subtract(displayText);
                        case "x" -> engine.multiply(displayText);
                        case "/" -> engine.divide(displayText);
                        case "=" -> engine.equal(displayText);
                    }
                    display.setText(engine.getTotalString());
                } catch (NumberFormatException ex) {
                    actionClear();
                }
                prevOperation = e.getActionCommand();
            }
        }
    }

    class NumberKeyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String digit = e.getActionCommand();
            if (startNumber) {
                display.setText(digit);
                startNumber = false;
            } else {
                display.setText(display.getText() + digit);
            }
        }
    }

    class ClearKeyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            actionClear();
        }
    }

}