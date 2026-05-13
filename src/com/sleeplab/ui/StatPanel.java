package com.sleeplab.ui;

import com.sleeplab.model.SleepRecord;
import com.sleeplab.service.GoalManager;
import com.sleeplab.service.SleepRecordManager;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class StatPanel extends JPanel implements Refreshable {

    private MainFrame frame;
    private SleepRecordManager manager = new SleepRecordManager();
    private GoalManager goalManager    = new GoalManager();

    private JLabel totalLabel;
    private JLabel avgTimeLabel;
    private JLabel avgQualityLabel;
    private JLabel bestLabel;
    private JLabel worstLabel;
    private JLabel goodCountLabel;
    private JLabel warningLabel;
    private JLabel goalInfoLabel;
    private JLabel sliderValueLabel;
    private JButton prescribeBtn;
    private GraphPanel graphPanel;
    private JSlider daySlider;

    public StatPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(18, 18, 28));

        // 타이틀
        JLabel title = new JLabel("수면 통계 요약", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        title.setForeground(new Color(160, 200, 255));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // 슬라이더
        daySlider = new JSlider(1, 30, 7);
        daySlider.setBackground(new Color(18, 18, 28));
        daySlider.setForeground(new Color(160, 200, 255));
        daySlider.setMajorTickSpacing(5);
        daySlider.setMinorTickSpacing(1);
        daySlider.setPaintTicks(true);
        daySlider.setPaintLabels(false);
        daySlider.setSnapToTicks(true);

        sliderValueLabel = new JLabel("기간: 7일", SwingConstants.CENTER);
        sliderValueLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        sliderValueLabel.setForeground(new Color(160, 200, 255));

        daySlider.addChangeListener(e -> {
            int val = daySlider.getValue();
            sliderValueLabel.setText("기간: " + val + "일");
            if (graphPanel != null) {
                graphPanel.setDays(val);
            }
        });

        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.setBackground(new Color(18, 18, 28));
        sliderPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 4, 20));
        sliderPanel.add(sliderValueLabel, BorderLayout.NORTH);
        sliderPanel.add(daySlider, BorderLayout.CENTER);

        // 주간 그래프
        graphPanel = new GraphPanel();
        graphPanel.setBackground(new Color(25, 28, 45));

        JPanel graphWrapper = new JPanel(new BorderLayout());
        graphWrapper.setBackground(new Color(18, 18, 28));
        graphWrapper.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        graphWrapper.add(graphPanel, BorderLayout.CENTER);
        graphWrapper.setPreferredSize(new Dimension(460, 150));

        // 월별 캘린더 버튼 — 그래프 바로 아래
        JButton calendarBtn = makeButton("월별 캘린더 보기 →", new Color(40, 70, 120));
        calendarBtn.addActionListener(e -> frame.showPanel("CALENDAR"));

        JPanel calendarBtnPanel = new JPanel(new GridLayout(1, 1));
        calendarBtnPanel.setBackground(new Color(18, 18, 28));
        calendarBtnPanel.setBorder(BorderFactory.createEmptyBorder(4, 50, 4, 50));
        calendarBtnPanel.add(calendarBtn);

        JPanel graphSection = new JPanel();
        graphSection.setLayout(new BoxLayout(graphSection, BoxLayout.Y_AXIS));
        graphSection.setBackground(new Color(18, 18, 28));
        graphSection.add(graphWrapper);
        graphSection.add(sliderPanel);
        graphSection.add(calendarBtnPanel);

        // 결과 패널
        JPanel resultPanel = new JPanel(new GridLayout(6, 1, 0, 8));
        resultPanel.setBackground(new Color(25, 28, 45));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40));

        totalLabel      = makeResultLabel("총 기록 수: -");
        avgTimeLabel    = makeResultLabel("평균 수면 시간: -");
        avgQualityLabel = makeResultLabel("평균 수면 품질: -");
        bestLabel       = makeResultLabel("최고 품질 날짜: -");
        worstLabel      = makeResultLabel("최저 품질 날짜: -");
        goodCountLabel  = makeResultLabel("컨디션 좋음: -");

        resultPanel.add(totalLabel);
        resultPanel.add(avgTimeLabel);
        resultPanel.add(avgQualityLabel);
        resultPanel.add(bestLabel);
        resultPanel.add(worstLabel);
        resultPanel.add(goodCountLabel);

        // 목표 정보 라벨
        goalInfoLabel = new JLabel("", SwingConstants.CENTER);
        goalInfoLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        goalInfoLabel.setForeground(new Color(160, 200, 255));
        goalInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        goalInfoLabel.setBorder(BorderFactory.createEmptyBorder(8, 20, 4, 20));

        JPanel goalInfoPanel = new JPanel(new BorderLayout());
        goalInfoPanel.setBackground(new Color(18, 18, 28));
        goalInfoPanel.add(goalInfoLabel, BorderLayout.CENTER);

        // 경고 라벨
        warningLabel = new JLabel("", SwingConstants.CENTER);
        warningLabel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
        warningLabel.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 20));

        JPanel warningPanel = new JPanel(new BorderLayout());
        warningPanel.setBackground(new Color(18, 18, 28));
        warningPanel.add(warningLabel, BorderLayout.CENTER);

        // 루틴 처방 버튼
        prescribeBtn = makeButton("지금 바로 루틴 처방받기 →", new Color(140, 60, 40));
        prescribeBtn.addActionListener(e -> frame.showPanel("PRESCRIBE"));
        prescribeBtn.setVisible(false);

        JPanel prescribePanel = new JPanel(new GridLayout(1, 1));
        prescribePanel.setBackground(new Color(18, 18, 28));
        prescribePanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 6, 50));
        prescribePanel.add(prescribeBtn);

        // 뒤로 버튼
        JButton backBtn = makeButton("← 뒤로", new Color(50, 50, 70));
        backBtn.addActionListener(e -> frame.showPanel("MAIN"));

        JPanel btnPanel = new JPanel(new GridLayout(1, 1));
        btnPanel.setBackground(new Color(18, 18, 28));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(4, 50, 20, 50));
        btnPanel.add(backBtn);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(18, 18, 28));
        centerPanel.add(graphSection);
        centerPanel.add(resultPanel);
        centerPanel.add(goalInfoPanel);
        centerPanel.add(warningPanel);
        centerPanel.add(prescribePanel);

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(18, 18, 28));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private JLabel makeResultLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
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
        manager     = new SleepRecordManager();
        goalManager = new GoalManager();
        List<SleepRecord> records = manager.getRecords();

        graphPanel.setData(records, goalManager.getGoalMinutes());
        graphPanel.setDays(daySlider.getValue());

        goalInfoLabel.setText(String.format("목표: %dh %02dm",
            goalManager.getGoalHours(),
            goalManager.getGoalMins()));

        if (records.isEmpty()) {
            totalLabel.setText("총 기록 수: 0일");
            avgTimeLabel.setText("평균 수면 시간: -");
            avgQualityLabel.setText("평균 수면 품질: -");
            bestLabel.setText("최고 품질 날짜: -");
            worstLabel.setText("최저 품질 날짜: -");
            goodCountLabel.setText("컨디션 좋음: -");
            warningLabel.setText("");
            prescribeBtn.setVisible(false);
            return;
        }

        double avgMins = records.stream()
            .mapToInt(SleepRecord::getSleepMinutes).average().orElse(0);

        SleepRecord best = records.stream()
            .max(Comparator.comparingInt(SleepRecord::getSleepQuality))
            .orElse(null);

        SleepRecord worst = records.stream()
            .min(Comparator.comparingInt(SleepRecord::getSleepQuality))
            .orElse(null);

        double avgQuality = records.stream()
            .mapToInt(SleepRecord::getSleepQuality).average().orElse(0);

        long goodCount = records.stream()
            .filter(r -> r.getCondition().equals("좋음")).count();

        totalLabel.setText("총 기록 수:      " + records.size() + "일");
        avgTimeLabel.setText(String.format("평균 수면 시간:  %dh %02dm",
            (int) avgMins / 60, (int) avgMins % 60));
        avgQualityLabel.setText(String.format("평균 수면 품질:  %.1f / 5.0", avgQuality));
        bestLabel.setText("최고 품질 날짜:  "
            + (best != null ? best.getDate()
            + " (" + "★".repeat(best.getSleepQuality()) + ")" : "-"));
        worstLabel.setText("최저 품질 날짜:  "
            + (worst != null ? worst.getDate()
            + " (" + "★".repeat(worst.getSleepQuality()) + ")" : "-"));
        goodCountLabel.setText("컨디션 좋음:     "
            + goodCount + "회 / " + records.size() + "일");

        if (avgQuality < 3.0) {
            warningLabel.setText("<html><div style='text-align:center;'>"
                + "<font color='#FF6B6B'>[경고] 평균 수면 품질이 3.0 미만입니다!</font>"
                + "</div></html>");
            prescribeBtn.setVisible(true);
        } else {
            warningLabel.setText("<html><div style='text-align:center;'>"
                + "<font color='#6BFF9E'>수면 품질이 양호합니다!</font>"
                + "</div></html>");
            prescribeBtn.setVisible(false);
        }
    }
}