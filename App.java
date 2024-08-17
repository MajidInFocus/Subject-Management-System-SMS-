/**
 * @author majid
 */

package com.mycompany.projectdbs;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the login frame
            Login loginFrame = new Login();
            loginFrame.setVisible(true);
        });
    }
}


