import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginPanel extends JPanel {
    private JFrame parentFrame;

    public LoginPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new GridLayout(3, 2));

        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());

            if (validateLogin(username, password)) {
                CardLayout cl = (CardLayout) (parentFrame.getContentPane().getLayout());
                cl.show(parentFrame.getContentPane(), "Landing");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }
        });

        registerButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) (parentFrame.getContentPane().getLayout());
            cl.show(parentFrame.getContentPane(), "Register");
        });

        add(userLabel);
        add(userText);
        add(passwordLabel);
        add(passwordText);
        add(loginButton);
        add(registerButton);
    }

    private boolean validateLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
