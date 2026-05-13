package com.sleeplab.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainPanel extends JPanel {

    private JLabel welcomeLabel;

    public MainPanel(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(new Color(18, 18, 28));

        // 상단 타이틀
        JLabel title = new JLabel("SleepLab", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 32));
        title.setForeground(new Color(160, 200, 255));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 4, 0));

        JLabel subtitle = new JLabel("수면 루틴 처방 시스템", SwingConstants.CENTER);
        subtitle.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        subtitle.setForeground(new Color(120, 140, 180));
        subtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));

        // 환영 문구
        welcomeLabel = new JLabel("", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        welcomeLabel.setForeground(new Color(100, 180, 255));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

        JPanel titlePanel = new JPanel(new GridLayout(3, 1));
        titlePanel.setBackground(new Color(18, 18, 28));
        titlePanel.add(title);
        titlePanel.add(subtitle);
        titlePanel.add(welcomeLabel);

        // 구분선
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(40, 50, 80));
        separator.setBackground(new Color(40, 50, 80));

        // 메뉴 버튼
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 1, 0, 5));
        buttonPanel.setBackground(new Color(18, 18, 28));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        String[][] menus = {
            {"수면 기록 입력",   "INPUT",    "#4A90D9"},
            {"수면 유형 분석",   "ANALYZE",  "#7B68EE"},
            {"루틴 처방받기",    "PRESCRIBE","#48C9B0"},
            {"기록 전체 보기",   "RECORD",   "#F0A500"},
            {"수면 통계 요약",   "STAT",     "#E74C8B"},
            {"목표 수면 설정",   "GOAL",     "#27AE60"},
            {"종료",            "EXIT",     "#888888"}
        };

        for (String[] menu : menus) {
            JButton btn = makeButton(menu[0], menu[2]);
            String target = menu[1];
            btn.addActionListener(e -> {
                if (target.equals("EXIT")) {
                    System.exit(0);
                } else {
                    frame.showPanel(target);
                }
            });
            buttonPanel.add(btn);
        }

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(18, 18, 28));
        centerPanel.add(separator);
        centerPanel.add(buttonPanel);

        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    public void updateWelcome(String userId) {
        welcomeLabel.setText("좋은 하루예요, " + userId + "님! 오늘의 수면은 어떠셨나요?");
    }

    private JButton makeButton(String text, String colorHex) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);

                // 배경
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                // 왼쪽 사이드 라인
                Color lineColor = Color.decode(colorHex);
                g2.setColor(lineColor);
                g2.fillRoundRect(0, 0, 4, getHeight(), 4, 4);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        btn.setBackground(new Color(25, 30, 48));
        btn.setForeground(new Color(200, 220, 255));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        // hover 효과
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(35, 42, 65));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(25, 30, 48));
            }
        });

        return btn;
    }
}