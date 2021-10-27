
import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import Questions.Question;
import java.sql.PreparedStatement;

class Register extends JFrame implements ActionListener {

    JLabel title_lb, lb_name, l2_surname, lb_email, lb_mobile, lb_username, lb_password, lb_question, lb_answer;
    JTextField txb_name, txb_surname, txb_email, txb_mobile, txb_username, txb_password, txb_answer;
    JButton btn_submit;
    JComboBox cb_selection;
    String Fqa;
    int queselected;
    String ques[] = {"What was the name of your first manager at your first job?", "What was your favorite subject in high school?", "What is your employee ID number?", "Where did you go on your favorite vacation as a child?", "What is the name of the road you grew up on?"};

    Register() { // constructor
        // frame req.
        super(" Registration Form ");
        this.setVisible(true);
        this.setSize(600, 600);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Question q = new Question();
        Fqa = q.txb_Answer.getText();
        components(); // Calling components

    }

    // components method
    public void components() {

        title_lb = new JLabel(" Registration Form "); // Title
        title_lb.setFont(new Font("serif", Font.BOLD, 20));
        int val = getWidth();
        title_lb.setBounds((val / 2) / 2, 10, 200, 25); // x , y , width , height
        add(title_lb);

        lb_name = new JLabel("Name : "); // Name
        lb_name.setBounds(50, 50, 100, 25);
        add(lb_name);

        txb_name = new JTextField(20);  // Name text field
        txb_name.setBounds(150, 50, 350, 25);
        add(txb_name);

        l2_surname = new JLabel("Surname : "); // Surname
        l2_surname.setBounds(50, 90, 100, 25);
        add(l2_surname);

        txb_surname = new JTextField(20);  // surname text field
        txb_surname.setBounds(150, 90, 350, 25);
        add(txb_surname);

        lb_email = new JLabel("Email : ");
        lb_email.setBounds(50, 125, 100, 25);
        add(lb_email);

        txb_email = new JTextField(20);
        txb_email.setBounds(150, 130, 350, 25);
        add(txb_email);

        lb_mobile = new JLabel("Mobile : ");
        lb_mobile.setBounds(50, 165, 100, 25);
        add(lb_mobile);

        txb_mobile = new JTextField(20);
        txb_mobile.setBounds(150, 170, 350, 25);
        add(txb_mobile);

        lb_username = new JLabel("Username : ");
        lb_username.setBounds(50, 205, 100, 25);
        add(lb_username);

        txb_username = new JTextField(20);
        txb_username.setBounds(150, 210, 350, 25);
        add(txb_username);

        lb_password = new JLabel("password : ");
        lb_password.setBounds(50, 245, 100, 25);
        add(lb_password);

        txb_password = new JTextField(20);
        txb_password.setBounds(150, 250, 350, 25);
        add(txb_password);

//        combo
        cb_selection = new JComboBox(ques);
        cb_selection.setBounds(150, 280, 350, 25);

        add(cb_selection);

//        combo lb
        lb_question = new JLabel("Select Question");
        lb_question.setBounds(50, 275, 100, 25);
        add(lb_question);

        //txb Answer
        txb_answer = new JTextField(20);
        txb_answer.setBounds(150, 310, 350, 25);
        add(txb_answer);

        //Answer Label
        lb_answer = new JLabel("Answer");
        lb_answer.setBounds(50, 315, 100, 25);
        add(lb_answer);

        btn_submit = new JButton("Submit");
        btn_submit.setBounds((val / 2) / 2, 355, 100, 25);
        add(btn_submit);
        btn_submit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btn_submit) {
            Connection con = null;
            Statement smt = null;
            String regex = "^(.+)@(.+)$";

            String txb_nameText = txb_name.getText();
            String txb_passwordText = txb_password.getText();
            String txb_emailText = txb_email.getText();
            String txb_surnameText = txb_surname.getText();
            String txb_mobileText = txb_mobile.getText();
            String txb_usernameText = txb_username.getText();
            String txb_ans = txb_answer.getText();
            queselected = cb_selection.getSelectedIndex();

            Pattern emailpattern1 = Pattern.compile(regex);
            Matcher emailmatcher = emailpattern1.matcher(txb_emailText);

            Pattern mobilepattern = Pattern.compile("(0/91)?[7-9][0-9]{9}");

            Matcher mobilematch = mobilepattern.matcher(txb_mobileText);

            if (!emailmatcher.matches()) {
                JOptionPane.showMessageDialog(rootPane, "Error in Email");
                txb_name.setText("");
                txb_password.setText("");
                txb_email.setText("");

                txb_surname.setText("");
                txb_mobile.setText("");
                txb_username.setText("");
                return;
            }

            if (!mobilematch.matches()) {
                JOptionPane.showMessageDialog(rootPane, "Error in mobile");
                txb_name.setText("");
                txb_password.setText("");
                txb_email.setText("");

                txb_surname.setText("");
                txb_mobile.setText("");
                txb_username.setText("");
                return;
            }

//            String jdbc_driver = "com.mysql.jdbc.Driver";
            String db_url = "jdbc:mysql://localhost:3306/AudioStego";

            try {
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("connecting to db");
                con = DriverManager.getConnection(db_url, "root", "");
                System.out.println("Connected successfully");
//                JOptionPane.showMessageDialog(rootPane, "Connected successfully");
                System.out.println("Inserting ... ");
                // the mysql insert statement
                String query = " insert into user (fname, sname, email, mobile, uname, pass, q, ans)"
                        + " values (?, ?, ?, ?, ?,?,?,?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setString(1, txb_nameText);
                preparedStmt.setString(2, txb_surnameText);
                preparedStmt.setString(3, txb_emailText);
                preparedStmt.setString(4, txb_mobileText);
                preparedStmt.setString(5, txb_usernameText);
                preparedStmt.setString(6, txb_passwordText);
                preparedStmt.setString(8, txb_ans);

                preparedStmt.setInt(7, queselected);
                preparedStmt.execute();

            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Error" + e);
            }

            new Login().setVisible(true); // back to login form

            this.dispose(); // remove this object

        } else {

        }
    }

}
