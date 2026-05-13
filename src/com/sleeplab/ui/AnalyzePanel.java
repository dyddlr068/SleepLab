package com.sleeplab.ui;

import com.sleeplab.exception.InsufficientDataException;
import com.sleeplab.model.routine.SleepRoutine;
import com.sleeplab.service.SleepAnalyzer;
import com.sleeplab.service.SleepRecordManager;

import javax.swing.*;
import java.awt.*;

public class AnalyzePanel extends JPanel implements Refreshable {

    private MainFrame frame;
    private SleepRecordManager manager = new SleepRecordManager();
    private SleepAnalyzer analyzer = new SleepAnalyzer();

    private JLabel typeLabel;
    private JLabel descLabel;
    private JLabel bedLabel;
    private JLabel wakeLabel;
    private JLabel caffeineLabel;
    private JLabel napLabel;
    private JLabel messageLabel;

    public AnalyzePanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(18, 18, 28));

        // 타이틀
        JLabel title = new JLabel("수면 유형 분석", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        title.setForeground(new Color(160, 200, 255));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        // 결과 패널
        JPanel resultPanel = new JPanel(new GridLayout(6, 1, 0, 12));
        resultPanel.setBackground(new Color(25, 28, 45));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        typeLabel     = makeResultLabel("판정: -");
        descLabel     = makeResultLabel("특징: -");
        bedLabel      = makeResultLabel("평균 취침: -");
        wakeLabel     = makeResultLabel("평균 기상: -");
        caffeineLabel = makeResultLabel("카페인 차단: -");
        napLabel      = makeResultLabel("낮잠 허용: -");

        resultPanel.add(typeLabel);
        resultPanel.add(descLabel);
        resultPanel.add(bedLabel);
        resultPanel.add(wakeLabel);
        resultPanel.add(caffeineLabel);
        resultPanel.add(napLabel);

        // 메시지
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

        // 버튼
        JButton backBtn = makeButton("← 뒤로", new Color(50, 50, 70));
        backBtn.addActionListener(e -> frame.showPanel("MAIN"));

        JPanel btnPanel = new JPanel(new GridLayout(1, 1));
        btnPanel.setBackground(new Color(18, 18, 28));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 30, 50));
        btnPanel.add(backBtn);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(18, 18, 28));
        centerPanel.add(resultPanel);
        JPanel msgPanel = new JPanel(new BorderLayout());
        msgPanel.setBackground(new Color(18, 18, 28));
        msgPanel.add(messageLabel, BorderLayout.CENTER);
        centerPanel.add(msgPanel);

        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private JLabel makeResultLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        label.setForeground(new Color(200, 220, 255));
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
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

    @Override
    public void refresh() {
        manager = new SleepRecordManager();
        try {
            SleepRoutine routine = analyzer.analyze(manager.getRecords());
            typeLabel.setText("판정: " + routine.getTypeName());
            descLabel.setText("특징: " + routine.getRecommendedBedTime()
                + " 취침 / " + routine.getRecommendedWakeTime() + " 기상 권장");
            bedLabel.setText("평균 취침: "
                + analyzer.getAverageBedTime(manager.getRecords()));
            wakeLabel.setText("평균 기상: "
                + analyzer.getAverageWakeTime(manager.getRecords()));
            caffeineLabel.setText("카페인 차단: " + routine.getCaffeineDeadline());
            napLabel.setText("낮잠 허용: " + routine.getNapAllowedMinutes() + "분");
            messageLabel.setText("");
        } catch (InsufficientDataException e) {
        	messageLabel.setText("<html><div style='text-align:center;'><font color='#FF6B6B'>"
        		    + e.getMessage() + "</font></div></html>");
            typeLabel.setText("판정: -");
            descLabel.setText("특징: -");
            bedLabel.setText("평균 취침: -");
            wakeLabel.setText("평균 기상: -");
            caffeineLabel.setText("카페인 차단: -");
            napLabel.setText("낮잠 허용: -");
        }
    }
}