import java.awt.*;
import javax.swing.*;

public class ATMUI {

    private ATM atm;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextArea output;
    private JTextField amountField;

    public ATMUI() {
        BankAccount account = new BankAccount(5000);
        atm = new ATM(account);

        JFrame frame = new JFrame("ATM System");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Fonts
        Font bigFont = new Font("Arial", Font.BOLD, 16);
        Font titleFont = new Font("Arial", Font.BOLD, 22);

        JLabel title = new JLabel("ATM System", JLabel.CENTER);
        title.setFont(titleFont);
        frame.add(title, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(menuPanel(bigFont), "menu");
        mainPanel.add(transactionPanel(bigFont), "transaction");

        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JPanel menuPanel(Font font) {
        JPanel panel = new JPanel(new GridLayout(4, 1, 15, 15));

        JButton withdraw = new JButton("Withdraw");
        JButton deposit = new JButton("Deposit");
        JButton balance = new JButton("Check Balance");
        JButton exit = new JButton("Exit");

        withdraw.setFont(font);
        deposit.setFont(font);
        balance.setFont(font);
        exit.setFont(font);

        withdraw.addActionListener(e -> showTransaction("withdraw"));
        deposit.addActionListener(e -> showTransaction("deposit"));

        balance.addActionListener(e ->
                JOptionPane.showMessageDialog(null, atm.checkBalance()));

        exit.addActionListener(e -> System.exit(0));

        panel.add(withdraw);
        panel.add(deposit);
        panel.add(balance);
        panel.add(exit);

        return panel;
    }

    private JPanel transactionPanel(Font font) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        amountField = new JTextField();
        amountField.setFont(font);

        output = new JTextArea();
        output.setFont(new Font("Monospaced", Font.BOLD, 16));
        output.setEditable(false);

        JButton submit = new JButton("Submit");
        JButton back = new JButton("Back");

        submit.setFont(font);
        back.setFont(font);

        JPanel top = new JPanel(new GridLayout(2, 1));
        top.add(new JLabel("Enter Amount:"));
        top.add(amountField);

        JPanel bottom = new JPanel();
        bottom.add(submit);
        bottom.add(back);

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(output), BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        submit.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String action = (String) submit.getClientProperty("action");

                String result = action.equals("withdraw")
                        ? atm.withdraw(amount)
                        : atm.deposit(amount);

                output.setText(result + "\n" + atm.checkBalance());

            } catch (Exception ex) {
                output.setText("Invalid Input");
            }
        });

        back.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        return panel;
    }

    private void showTransaction(String action) {
        Component comp = mainPanel.getComponent(1);
        JButton submit = findSubmitButton(comp);

        submit.putClientProperty("action", action);

        // ✅ CLEAR OLD DATA
        amountField.setText("");
        output.setText("");

        cardLayout.show(mainPanel, "transaction");
    }

    private JButton findSubmitButton(Component comp) {
        if (comp instanceof JButton) {
            JButton btn = (JButton) comp;
            if ("Submit".equals(btn.getText())) return btn;
        }
        if (comp instanceof Container) {
            for (Component c : ((Container) comp).getComponents()) {
                JButton b = findSubmitButton(c);
                if (b != null) return b;
            }
        }
        return null;
    }
}
