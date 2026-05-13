package com.sleeplab.ui;

import com.sleeplab.service.GoalManager;
import javax.swing.*;
import java.awt.*;

public class GoalPanel extends JPanel {

    private MainFrame frame;
    private GoalManager goalManager = new GoalManager();

    private JSpinner hourSpinner;
    private JSpinner minSpinner;
    private JLabel messageLabel;
    private JLabel currentGoalLabel;

    public GoalPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(18, 18, 28));

        JLabel title = new JLabel("목표 수면 시간 설정", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        title.setForeground(new Color(160, 200, 255));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        currentGoalLabel = new JLabel("", SwingConstants.CENTER);
        currentGoalLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        currentGoalLabel.setForeground(new Color(120, 160, 220));
        currentGoalLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(18, 18, 28));
        topPanel.add(title);
        topPanel.add(currentGoalLabel);

        // 입력 폼 — 기간 제거
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 18));
        formPanel.setBackground(new Color(18, 18, 28));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        hourSpinner = new JSpinner(new SpinnerNumberModel(8, 1, 12, 1));
        styleSpinner(hourSpinner);

        minSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 45, 15));
        styleSpinner(minSpinner);

        formPanel.add(makeLabel("목표 시간 (시)"));
        formPanel.add(hourSpinner);
        formPanel.add(makeLabel("목표 시간 (분)"));
        formPanel.add(minSpinner);

        // 메달 기준 안내
        JPanel guidePanel = new JPanel(new GridLayout(4, 1, 0, 6));
        guidePanel.setBackground(new Color(25, 28, 45));
        guidePanel.setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40));
        guidePanel.add(makeGuideLabel("메달 기준 (목표 대비 달성률)", new Color(160, 200, 255)));
        guidePanel.add(makeGuideLabel("금메달 — 목표 100% 이상 달성", new Color(255, 215, 0)));
        guidePanel.add(makeGuideLabel("은메달 — 목표 87.5% 이상 달성", new Color(192, 192, 192)));
        guidePanel.add(makeGuideLabel("동메달 — 목표 75% 이상 달성", new Color(205, 127, 50)));

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(8, 20, 0, 20));

        JPanel msgPanel = new JPanel(new BorderLayout());
        msgPanel.setBackground(new Color(18, 18, 28));
        msgPanel.add(messageLabel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setBackground(new Color(18, 18, 28));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 60, 30, 60));

        JButton saveBtn = makeButton("저장", new Color(40, 80, 140));
        JButton backBtn = makeButton("← 뒤로", new Color(50, 50, 70));

        saveBtn.addActionListener(e -> saveGoal());
        backBtn.addActionListener(e -> frame.showPanel("MAIN"));

        btnPanel.add(saveBtn);
        btnPanel.add(backBtn);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(18, 18, 28));
        centerPanel.add(formPanel);
        centerPanel.add(guidePanel);
        centerPanel.add(msgPanel);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        updateCurrentGoalLabel();
    }

    private void saveGoal() {
        int hours   = (int) hourSpinner.getValue();
        int mins    = (int) minSpinner.getValue();
        int totalMins = hours * 60 + mins;
        goalManager.save(totalMins, "설정없음");
        messageLabel.setText("<html><div style='text-align:center;'>"
            + "<font color='#6BFF9E'>목표가 저장되었습니다! ("
            + hours + "h " + String.format("%02d", mins) + "m)</font>"
            + "</div></html>");
        updateCurrentGoalLabel();
    }

    private void updateCurrentGoalLabel() {
        goalManager = new GoalManager();
        currentGoalLabel.setText("현재 목표: "
            + goalManager.getGoalHours() + "h "
            + String.format("%02d", goalManager.getGoalMins()) + "m");
    }

    private void styleSpinner(JSpinner spinner) {
        spinner.getEditor().getComponent(0).setBackground(new Color(30, 35, 55));
        spinner.getEditor().getComponent(0).setForeground(new Color(220, 230, 255));
        spinner.setBackground(new Color(30, 35, 55));
    }

    private JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        label.setForeground(new Color(180, 200, 240));
        return label;
    }

    private JLabel makeGuideLabel(String text, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        label.setForeground(color);
        return label;
    }

    private JButton makeButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return btn;
    }

    public void refresh() {
        goalManager = new GoalManager();
        updateCurrentGoalLabel();
        hourSpinner.setValue(goalManager.getGoalHours());
        minSpinner.setValue(goalManager.getGoalMins());
        messageLabel.setText("");
    }
}