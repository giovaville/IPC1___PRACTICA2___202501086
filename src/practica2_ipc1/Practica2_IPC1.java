/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica2_ipc1;

import javax.swing.SwingUtilities;
import practica2_ipc1.vista.MainFrame;
/**
 *
 * @author Gio
 */
public class Practica2_IPC1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame ventanaPrincipal = new MainFrame();
                ventanaPrincipal.setVisible(true);
            }
        });
    }
}
