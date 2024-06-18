import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LeaderboardPanel extends JPanel {
    private JTextArea leaderboardTextArea;
    private JButton exitButton;

    public LeaderboardPanel() {
        setLayout(new BorderLayout());

        leaderboardTextArea = new JTextArea();
        leaderboardTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(leaderboardTextArea);
        add(scrollPane, BorderLayout.CENTER);

        exitButton = new JButton("Exit to Landing Page");
        exitButton.addActionListener(e -> exitToLandingPage());
        add(exitButton, BorderLayout.SOUTH);

        updateLeaderboard();
    }

    public void updateLeaderboard() {
        Map<String, Integer> leaderboard = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("leaderboard.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String username = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    leaderboard.put(username, leaderboard.getOrDefault(username, 0) + score);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder leaderboardText = new StringBuilder();
        for (Map.Entry<String, Integer> entry : leaderboard.entrySet()) {
            leaderboardText.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        leaderboardTextArea.setText(leaderboardText.toString());
    }

    private void exitToLandingPage() {
        Container parentContainer = getParent();
        if (parentContainer instanceof JPanel) {
            CardLayout cl = (CardLayout) (parentContainer.getLayout());
            cl.show(parentContainer, "Landing");
        }
    }
}
