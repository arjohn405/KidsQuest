import javax.swing.*;
import java.awt.*;

public class LandingPanel extends JPanel {
    private JFrame parentFrame;

    public LandingPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to the Quiz Program!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");

        easyButton.addActionListener(e -> showQuestions("easy"));
        mediumButton.addActionListener(e -> showQuestions("medium"));
        hardButton.addActionListener(e -> showQuestions("hard"));

        buttonPanel.add(easyButton);
        buttonPanel.add(mediumButton);
        buttonPanel.add(hardButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void showQuestions(String difficulty) {
        QuestionsPanel questionsPanel = new QuestionsPanel(difficulty);
        parentFrame.add(questionsPanel, "Questions");
        CardLayout cl = (CardLayout) (parentFrame.getContentPane().getLayout());
        cl.show(parentFrame.getContentPane(), "Questions");
    }
}
