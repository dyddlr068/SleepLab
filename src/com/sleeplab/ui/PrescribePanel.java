package com.sleeplab.ui;

import com.sleeplab.exception.InsufficientDataException;
import com.sleeplab.model.routine.SleepRoutine;
import com.sleeplab.service.SleepAnalyzer;
import com.sleeplab.service.SleepRecordManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class PrescribePanel extends JPanel implements Refreshable {

    private MainFrame frame;
    private SleepRecordManager manager = new SleepRecordManager();
    private SleepAnalyzer analyzer = new SleepAnalyzer();

    private JTextArea resultArea;
    private JLabel messageLabel;

    public PrescribePanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(18, 18, 28));

        // 타이틀
        JLabel title = new JLabel("오늘의 루틴 처방", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        title.setForeground(new Color(160, 200, 255));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        // 결과 영역
        resultArea = new JTextArea();
        resultArea.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        resultArea.setBackground(new Color(25, 28, 45));
        resultArea.setForeground(new Color(200, 220, 255));
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        resultArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scrollPane.getViewport().setBackground(new Color(25, 28, 45));

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
        centerPanel.add(scrollPane);
        JPanel msgPanel = new JPanel(new BorderLayout());
        msgPanel.setBackground(new Color(18, 18, 28));
        msgPanel.add(messageLabel, BorderLayout.CENTER);
        centerPanel.add(msgPanel);

        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
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
            StringBuilder sb = new StringBuilder();
            sb.append("유형: ").append(routine.getTypeName()).append("\n\n");
            sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            sb.append("[오전 루틴]\n");
            sb.append(getMorningRoutine(routine)).append("\n\n");
            sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            sb.append("[오후 루틴]\n");
            sb.append(getAfternoonRoutine(routine)).append("\n\n");
            sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            sb.append("[취침 준비]\n");
            sb.append(getNightRoutine(routine)).append("\n\n");
            sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            sb.append("처방일: ").append(LocalDate.now());
            resultArea.setText(sb.toString());
            messageLabel.setText("");
            routine.savePrescription(LocalDate.now().toString());
        } catch (InsufficientDataException e) {
            resultArea.setText("");
            messageLabel.setText("<html><div style='text-align:center;'><font color='#FF6B6B'>"
            	    + e.getMessage() + "</font></div></html>");
        }
    }

    private String getMorningRoutine(SleepRoutine routine) {
        String name = routine.getTypeName();
        if (name.contains("아침형")) {
            return "  " + routine.getRecommendedWakeTime() + "   기상 알람 (권장)\n"
                 + "  기상 후   스트레칭 10분\n"
                 + "  +30분     단백질 위주 아침 식사";
        } else if (name.contains("저녁형")) {
            return "  " + routine.getRecommendedWakeTime() + "   기상 알람 (권장)\n"
                 + "  기상 후   햇빛 10분 쬐기\n"
                 + "  +30분     단백질 위주 아침 식사";
        } else {
            return "  " + routine.getRecommendedWakeTime() + "   고정 기상 알람 (주말도 동일!)\n"
                 + "  기상 후   햇빛 15분 쬐기\n"
                 + "  +30분     매일 같은 시간 아침 식사";
        }
    }

    private String getAfternoonRoutine(SleepRoutine routine) {
        return "  " + routine.getCaffeineDeadline() + "   카페인 차단 시각\n"
             + "  이후      낮잠 허용 " + routine.getNapAllowedMinutes() + "분";
    }

    private String getNightRoutine(SleepRoutine routine) {
        return "  취침 1시간 전   블루라이트 차단\n"
             + "  취침 30분 전    조명 낮추기\n"
             + "  " + routine.getRecommendedBedTime() + "         권장 취침 시각";
    }
}