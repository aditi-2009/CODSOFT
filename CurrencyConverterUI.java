

import javax.swing.*;
import java.awt.*;

public class CurrencyConverterUI {

    CurrencyService service = new CurrencyService();

    public CurrencyConverterUI() {
        JFrame frame = new JFrame("Currency Converter");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 2, 10, 10));

        String[] currencies = {"USD", "INR", "EUR", "GBP", "JPY"};

        JComboBox<String> baseBox = new JComboBox<>(currencies);
        JComboBox<String> targetBox = new JComboBox<>(currencies);

        JTextField amountField = new JTextField();
        JLabel resultLabel = new JLabel("Result: ");

        JButton convertBtn = new JButton("Convert");

        frame.add(new JLabel("Base Currency:"));
        frame.add(baseBox);

        frame.add(new JLabel("Target Currency:"));
        frame.add(targetBox);

        frame.add(new JLabel("Amount:"));
        frame.add(amountField);

        frame.add(new JLabel(""));
        frame.add(convertBtn);

        frame.add(new JLabel(""));
        frame.add(resultLabel);

        convertBtn.addActionListener(e -> {
            try {
                String base = baseBox.getSelectedItem().toString();
                String target = targetBox.getSelectedItem().toString();

                double amount = Double.parseDouble(amountField.getText());

                double rate = service.getRate(base, target);

                if (rate == -1) {
                    resultLabel.setText("Error fetching rate");
                    return;
                }

                double result = amount * rate;

                resultLabel.setText("Result: " + result + " " + target);

            } catch (Exception ex) {
                resultLabel.setText("Invalid Input");
            }
        });

        frame.setVisible(true);
    }
}