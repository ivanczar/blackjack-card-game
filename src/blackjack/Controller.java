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
        this.view.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Log in":
                System.out.println("Login Pressed");
                String username = this.view.loginPanel.unInput.getText();
                String password = this.view.loginPanel.pwInput.getText();
                this.model.checkName(username, password);

                break;
            case "STAND":
                this.model.playerStand();
                break;
            case "HIT":
                this.model.playerHit();

                break;
            case "Place bet":
                System.out.println("pressed place bet");
                double bet = Double.parseDouble(this.view.betPanel.betField.getText());

                this.model.placeBet(this.model.player, bet);

                this.model.initialDeal();

                break;
//            

        }

    }

}
