import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterPanel extends JPanel {
    private JFrame parentFrame;

    public RegisterPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new GridLayout(4, 2));

        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField();
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordText = new JPasswordField();
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");

        registerButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            String confirmPassword = new String(confirmPasswordText.getPassword());

            if (password.equals(confirmPassword)) {
                if (registerUser(username, password)) {
                    JOptionPane.showMessageDialog(this, "Registration successful");
                    CardLayout cl = (CardLayout) (parentFrame.getContentPane().getLayout());
                    cl.show(parentFrame.getContentPane(), "Login");
                } else {
                    JOptionPane.showMessageDialog(this, "User already exists");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Passwords do not match");
            }
        });

        backButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) (parentFrame.getContentPane().getLayout());
            cl.show(parentFrame.getContentPane(), "Login");
        });

        add(userLabel);
        add(userText);
        add(passwordLabel);
        add(passwordText);
        add(confirmPasswordLabel);
        add(confirmPasswordText);
        add(registerButton);
        add(backButton);
    }

    private boolean registerUser(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials[0].equals(username)) {
                    return false;  // User already exists
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true))) {
            bw.write(username + "," + password);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
