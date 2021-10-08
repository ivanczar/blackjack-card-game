/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Ivan
 */
public class Controller implements ActionListener {

    public View view;
    public Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
//            case "Log in":
//                String username = this.view.unInput.getText();
//                String password = this.view.pwInput.getText();
//                this.model.checkName(username, password);
//                break;
//            case "Quit":
//                this.model.quitGame();
//                break;
//            case "Reset":
//                this.model.resetGame();
//                break;
//            case "Place Bet":
//                int bet = Integer.parseInt(this.view.betField.getText());
//                this.model.placeBet(bet);
//                break;
//            

        }

    }

}
