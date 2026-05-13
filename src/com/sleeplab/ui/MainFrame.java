package com.sleeplab.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel container;
    private String loggedInUser = ""; // 로그인한 아이디 저장

    private MainPanel mainPanel;

    public MainFrame() {
        setTitle("SleepLab 수면 루틴 처방 시스템");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        container  = new JPanel(cardLayout);

        // 각 패널 등록
        mainPanel = new MainPanel(this);
        container.add(new LoginPanel(this),    "LOGIN");
        container.add(new RegisterPanel(this), "REGISTER");
        container.add(mainPanel,               "MAIN");
        container.add(new InputPanel(this),    "INPUT");
        container.add(new AnalyzePanel(this),  "ANALYZE");
        container.add(new PrescribePanel(this),"PRESCRIBE");
        container.add(new RecordPanel(this),   "RECORD");
        container.add(new StatPanel(this),     "STAT");
        container.add(new GoalPanel(this),     "GOAL");
        container.add(new CalendarPanel(this), "CALENDAR");

        add(container);
        cardLayout.show(container, "LOGIN");
        setVisible(true);
    }

    // 로그인 성공 시 호출
    public void loginSuccess(String userId) {
        this.loggedInUser = userId;
        mainPanel.updateWelcome(userId);
        showPanel("MAIN");
    }

    public String getLoggedInUser() { return loggedInUser; }

    public void showPanel(String name) {
        cardLayout.show(container, name);
        for (Component comp : container.getComponents()) {
            if (comp instanceof Refreshable && comp.isVisible()) {
                ((Refreshable) comp).refresh();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}