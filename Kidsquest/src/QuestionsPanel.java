import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionsPanel extends JPanel {
    private List<Question> questions;
    private JLabel questionLabel;
    private JRadioButton[] choiceButtons;
    private ButtonGroup choicesGroup;
    private JButton nextButton;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private String difficulty;

    public QuestionsPanel(String difficulty) {
        this.difficulty = difficulty;
        setLayout(new BorderLayout());

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Serif", Font.BOLD, 18));
        add(questionLabel, BorderLayout.NORTH);

        JPanel choicesPanel = new JPanel();
        choicesPanel.setLayout(new GridLayout(4, 1));
        choiceButtons = new JRadioButton[4];
        choicesGroup = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            choiceButtons[i] = new JRadioButton();
            choicesGroup.add(choiceButtons[i]);
            choicesPanel.add(choiceButtons[i]);
        }

        add(choicesPanel, BorderLayout.CENTER);

        nextButton = new JButton("Next");
        nextButton.addActionListener(new NextButtonListener());
        add(nextButton, BorderLayout.SOUTH);

        questions = loadQuestions(difficulty);
        if (!questions.isEmpty()) {
            displayQuestion(0);
        } else {
            questionLabel.setText("No questions available for " + difficulty + " level.");
            nextButton.setEnabled(false);
        }
    }

    private List<Question> loadQuestions(String difficulty) {
        List<Question> questions = new ArrayList<>();
        if (difficulty.equals("easy")) {
            questions.add(new Question("Easy Question 1?", new String[]{"a) Option 1", "b) Option 2", "c) Option 3", "d) Option 4"}, "a"));
            questions.add(new Question("Easy Question 2?", new String[]{"a) Option 1", "b) Option 2", "c) Option 3", "d) Option 4"}, "b"));
            questions.add(new Question("Easy Question 3?", new String[]{"a) Option 1", "b) Option 2", "c) Option 3", "d) Option 4"}, "c"));
        } else if (difficulty.equals("medium")) {
            questions.add(new Question("Medium Question 1?", new String[]{"a) Option 1", "b) Option 2", "c) Option 3", "d) Option 4"}, "a"));
            questions.add(new Question("Medium Question 2?", new String[]{"a) Option 1", "b) Option 2", "c) Option 3", "d) Option 4"}, "b"));
            questions.add(new Question("Medium Question 3?", new String[]{"a) Option 1", "b) Option 2", "c) Option 3", "d) Option 4"}, "c"));
        } else if (difficulty.equals("hard")) {
            questions.add(new Question("Hard Question 1?", new String[]{"a) Option 1", "b) Option 2", "c) Option 3", "d) Option 4"}, "a"));
            questions.add(new Question("Hard Question 2?", new String[]{"a) Option 1", "b) Option 2", "c) Option 3", "d) Option 4"}, "b"));
            questions.add(new Question("Hard Question 3?", new String[]{"a) Option 1", "b) Option 2", "c) Option 3", "d) Option 4"}, "c"));
        }
        return questions;
    }

    private void displayQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            Question question = questions.get(index);
            questionLabel.setText(question.getQuestion());
            String[] choices = question.getChoices();
            for (int i = 0; i < choices.length; i++) {
                choiceButtons[i].setText(choices[i]);
                choiceButtons[i].setSelected(false);
            }
        }
    }

    private class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentQuestionIndex < questions.size()) {
                Question currentQuestion = questions.get(currentQuestionIndex);
                String correctAnswer = currentQuestion.getAnswer();
                for (int i = 0; i < choiceButtons.length; i++) {
                    if (choiceButtons[i].isSelected() && choiceButtons[i].getText().charAt(0) == correctAnswer.charAt(0)) {
                        score++;
                    }
                }
            }

            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                displayQuestion(currentQuestionIndex);
            } else {
                JOptionPane.showMessageDialog(QuestionsPanel.this, "Level completed! Your score: " + score + "/" + questions.size());

                saveScoreToLeaderboard(score);

                if (difficulty.equals("hard")) {
                    Container parentContainer = getParent();
                    Component[] components = parentContainer.getComponents();
                    for (Component component : components) {
                        if (component instanceof LeaderboardPanel) {
                            ((LeaderboardPanel) component).updateLeaderboard();
                            CardLayout cl = (CardLayout) (parentContainer.getLayout());
                            cl.show(parentContainer, "Leaderboard");
                            return;
                        }
                    }
                } else {
                    CardLayout cl = (CardLayout) (getParent().getLayout());
                    cl.show(getParent(), "Landing");
                }
            }
        }

        private void saveScoreToLeaderboard(int score) {
            String username = JOptionPane.showInputDialog(QuestionsPanel.this, "Enter your name for the leaderboard:");
            if (username != null && !username.trim().isEmpty()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("leaderboard.txt", true))) {
                    writer.write(username + ": " + score);
                    writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
