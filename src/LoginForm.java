import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class LoginForm {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Form
        JLabel l1 = new JLabel("Username: ");
        JTextField tf1 = new JTextField(22);
        JLabel l2 = new JLabel("Password: ");
        JPasswordField tf2 = new JPasswordField(22);

        JButton btn = new JButton("Login");
        JButton register = new JButton("Register");

        // JPanel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(l1);
        panel.add(tf1);
        panel.add(l2);
        panel.add(tf2);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(btn);
        buttonPanel.add(register);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(panel);
        mainPanel.add(buttonPanel);

        // Adding to JFrame
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
        frame.pack();

        // ActionListener
        btn.addActionListener(e -> {
            String username = tf1.getText();
            String password = new String(tf2.getPassword());

            // Validation
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all the fields");
            }

            else {
                try {
                    // Establishing connection with MySQL
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/talha", "root",
                            "talha");
                    System.out.println("Connection established successfully.");

                    // Fetching data from MySQL
                    String query = "SELECT * FROM register WHERE username = ? AND password = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, username);
                    statement.setString(2, password);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(frame, "Login successful");
                        frame.dispose();
                        DashboardForm db = new DashboardForm();
                        db.dashboard();

                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid credentials");
                    }

                } catch (Exception ex) {
                    System.out.println("Connection Failed" + ex);
                }
            }
        });

        register.addActionListener(e -> {
            frame.dispose();
            RegistrationForm rf = new RegistrationForm();
            rf.register();
        });

    }

}