
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

class Register extends JFrame implements ActionListener {

    JLabel title_lb, lb_name, l2_surname, lb_email, lb_mobile, lb_username, lb_password;
    JTextField txb_name, txb_surname, txb_email, txb_mobile, txb_username, txb_password;
    JButton btn_submit;

    Register() { // constructor
        // frame req.
        super(" Registration Form ");
        this.setVisible(true);
        this.setSize(400, 400);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        txb_name.setBounds(150, 50, 150, 25);
        add(txb_name);

        l2_surname = new JLabel("Surname : "); // Surname
        l2_surname.setBounds(50, 90, 100, 25);
        add(l2_surname);

        txb_surname = new JTextField(20);  // surname text field
        txb_surname.setBounds(150, 90, 150, 25);
        add(txb_surname);

        lb_email = new JLabel("Email : ");
        lb_email.setBounds(50, 125, 100, 25);
        add(lb_email);

        txb_email = new JTextField(20);
        txb_email.setBounds(150, 130, 150, 25);
        add(txb_email);

        lb_mobile = new JLabel("Mobile : ");
        lb_mobile.setBounds(50, 165, 100, 25);
        add(lb_mobile);

        txb_mobile = new JTextField(20);
        txb_mobile.setBounds(150, 170, 150, 25);
        add(txb_mobile);

        lb_username = new JLabel("Username : ");
        lb_username.setBounds(50, 205, 100, 25);
        add(lb_username);

        txb_username = new JTextField(20);
        txb_username.setBounds(150, 210, 150, 25);
        add(txb_username);

        lb_password = new JLabel("password : ");
        lb_password.setBounds(50, 245, 100, 25);
        add(lb_password);

        txb_password = new JTextField(20);
        txb_password.setBounds(150, 250, 150, 25);
        add(txb_password);

        btn_submit = new JButton("Submit");
        btn_submit.setBounds((val / 2) / 2, 290, 100, 25);
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
                JOptionPane.showMessageDialog(rootPane, "Connected successfully");
                System.out.println("Inserting ... ");
                smt = con.createStatement();

                String query = "INSERT INTO USER(fname,sname,email,mobile,uname,pass) VALUES ( ' " + txb_nameText + "' , '" + txb_surnameText + "' , '" + txb_emailText + "' , '" + txb_mobileText + "' , '" + txb_usernameText + "' , '" + txb_passwordText + "');";
                smt.execute(query);

            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Error" + e);
            }

            new Login().setVisible(true); // back to login form

            this.dispose(); // remove this object

        } else {

        }
    }

}
