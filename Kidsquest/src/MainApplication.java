import javax.swing.*;
import java.awt.*;

public class MainApplication {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new CardLayout());

        // Adding panels to the CardLayout
        LoginPanel loginPanel = new LoginPanel(frame);
        RegisterPanel registerPanel = new RegisterPanel(frame);
        LandingPanel landingPanel = new LandingPanel(frame);
        LeaderboardPanel leaderboardPanel = new LeaderboardPanel();

        frame.add(loginPanel, "Login");
        frame.add(registerPanel, "Register");
        frame.add(landingPanel, "Landing");
        frame.add(leaderboardPanel, "Leaderboard");

        frame.setVisible(true);
    }
}
