package com.sleeplab.ui;

import com.sleeplab.model.SleepRecord;
import com.sleeplab.service.GoalManager;
import com.sleeplab.service.SleepRecordManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarPanel extends JPanel implements Refreshable {

    private MainFrame frame;
    private SleepRecordManager manager = new SleepRecordManager();
    private GoalManager goalManager    = new GoalManager();

    private JComboBox<Integer> yearBox;
    private JComboBox<String> monthBox;
    private JPanel calendarGrid;
    private JLabel goldCountLabel;
    private JLabel silverCountLabel;
    private JLabel bronzeCountLabel;

    private static final String[] MONTHS = {
        "1월", "2월", "3월", "4월", "5월", "6월",
        "7월", "8월", "9월", "10월", "11월", "12월"
    };

    public CalendarPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(18, 18, 28));

        // 타이틀
        JLabel title = new JLabel("월별 수면 캘린더", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        title.setForeground(new Color(160, 200, 255));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // 년도 선택 (2020 ~ 현재+1)
        int currentYear = LocalDate.now().getYear();
        Integer[] years = new Integer[currentYear - 2019];
        for (int i = 0; i < years.length; i++) {
            years[i] = 2020 + i;
        }
        yearBox = new JComboBox<>(years);
        yearBox.setSelectedItem(currentYear);
        yearBox.setBackground(new Color(30, 35, 55));
        yearBox.setForeground(new Color(220, 230, 255));
        yearBox.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        yearBox.addActionListener(e -> updateCalendar());

        // 월 선택
        monthBox = new JComboBox<>(MONTHS);
        monthBox.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
        monthBox.setBackground(new Color(30, 35, 55));
        monthBox.setForeground(new Color(220, 230, 255));
        monthBox.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        monthBox.addActionListener(e -> updateCalendar());

        // 년도 + 월 선택 패널
        JPanel selectPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        selectPanel.setBackground(new Color(18, 18, 28));

        JLabel yearLabel = new JLabel("년도:");
        yearLabel.setForeground(new Color(180, 200, 240));
        yearLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

        JLabel monthLabel = new JLabel("월:");
        monthLabel.setForeground(new Color(180, 200, 240));
        monthLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

        selectPanel.add(yearLabel);
        selectPanel.add(yearBox);
        selectPanel.add(monthLabel);
        selectPanel.add(monthBox);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(18, 18, 28));
        topPanel.add(title);
        topPanel.add(selectPanel);

        // 요일 헤더
        JPanel headerPanel = new JPanel(new GridLayout(1, 7, 2, 2));
        headerPanel.setBackground(new Color(18, 18, 28));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 4, 10));
        String[] days = {"일", "월", "화", "수", "목", "금", "토"};
        for (String day : days) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
            dayLabel.setForeground(
                day.equals("일") ? new Color(255, 100, 100) :
                day.equals("토") ? new Color(100, 150, 255) :
                new Color(160, 200, 255));
            headerPanel.add(dayLabel);
        }

        // 캘린더 그리드
        calendarGrid = new JPanel(new GridLayout(6, 7, 2, 2));
        calendarGrid.setBackground(new Color(18, 18, 28));
        calendarGrid.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        // 메달 집계
        JPanel medalSummary = new JPanel(new GridLayout(1, 3, 10, 0));
        medalSummary.setBackground(new Color(25, 28, 45));
        medalSummary.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        goldCountLabel   = makeMedalLabel("금: 0개", new Color(255, 215, 0));
        silverCountLabel = makeMedalLabel("은: 0개", new Color(192, 192, 192));
        bronzeCountLabel = makeMedalLabel("동: 0개", new Color(205, 127, 50));

        medalSummary.add(goldCountLabel);
        medalSummary.add(silverCountLabel);
        medalSummary.add(bronzeCountLabel);

        // 뒤로 버튼
        JButton backBtn = makeButton("← 뒤로", new Color(50, 50, 70));
        backBtn.addActionListener(e -> frame.showPanel("STAT"));

        JPanel btnPanel = new JPanel(new GridLayout(1, 1));
        btnPanel.setBackground(new Color(18, 18, 28));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(4, 50, 20, 50));
        btnPanel.add(backBtn);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(18, 18, 28));
        centerPanel.add(headerPanel);
        centerPanel.add(calendarGrid);
        centerPanel.add(medalSummary);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void updateCalendar() {
        calendarGrid.removeAll();
        manager     = new SleepRecordManager();
        goalManager = new GoalManager();

        int year  = (Integer) yearBox.getSelectedItem();
        int month = monthBox.getSelectedIndex() + 1;
        YearMonth yearMonth    = YearMonth.of(year, month);
        int daysInMonth        = yearMonth.lengthOfMonth();
        int firstDayOfWeek     = yearMonth.atDay(1).getDayOfWeek().getValue() % 7;

        // 기록 날짜별 맵
        List<SleepRecord> records = manager.getRecords();
        Map<String, SleepRecord> recordMap = new HashMap<>();
        for (SleepRecord r : records) {
            recordMap.put(r.getDate(), r);
        }

        int gold = 0, silver = 0, bronze = 0;

        // 앞 빈 칸
        for (int i = 0; i < firstDayOfWeek; i++) {
            calendarGrid.add(makeEmptyCell());
        }

        // 날짜 채우기
        for (int day = 1; day <= daysInMonth; day++) {
            String dateStr    = String.format("%d-%02d-%02d", year, month, day);
            SleepRecord record = recordMap.get(dateStr);

            JPanel cell = new JPanel(new BorderLayout());
            cell.setBackground(new Color(25, 28, 45));
            cell.setBorder(BorderFactory.createLineBorder(
                new Color(40, 50, 80), 1));

            JLabel dayLabel = new JLabel(String.valueOf(day), SwingConstants.CENTER);
            dayLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
            dayLabel.setForeground(new Color(160, 180, 220));

            JLabel medalLabel = new JLabel("", SwingConstants.CENTER);
            medalLabel.setFont(new Font("맑은 고딕", Font.BOLD, 12));

            if (record != null) {
                String medal = goalManager.getMedal(record.getSleepMinutes());
                switch (medal) {
                    case "GOLD" -> {
                        medalLabel.setText("금");
                        medalLabel.setForeground(new Color(255, 215, 0));
                        cell.setBackground(new Color(40, 38, 20));
                        gold++;
                    }
                    case "SILVER" -> {
                        medalLabel.setText("은");
                        medalLabel.setForeground(new Color(192, 192, 192));
                        cell.setBackground(new Color(30, 35, 45));
                        silver++;
                    }
                    case "BRONZE" -> {
                        medalLabel.setText("동");
                        medalLabel.setForeground(new Color(205, 127, 50));
                        cell.setBackground(new Color(38, 28, 18));
                        bronze++;
                    }
                    default -> {
                        medalLabel.setText("--");
                        medalLabel.setForeground(new Color(100, 100, 120));
                    }
                }
            }

            // 오늘 날짜 강조
            if (dateStr.equals(LocalDate.now().toString())) {
                cell.setBorder(BorderFactory.createLineBorder(
                    new Color(100, 160, 255), 2));
                dayLabel.setForeground(new Color(100, 180, 255));
            }

            cell.add(dayLabel, BorderLayout.NORTH);
            cell.add(medalLabel, BorderLayout.CENTER);
            calendarGrid.add(cell);
        }

        // 뒤 빈 칸
        int remaining = 42 - firstDayOfWeek - daysInMonth;
        for (int i = 0; i < remaining; i++) {
            calendarGrid.add(makeEmptyCell());
        }

        // 메달 집계 업데이트
        goldCountLabel.setText("금: " + gold + "개");
        silverCountLabel.setText("은: " + silver + "개");
        bronzeCountLabel.setText("동: " + bronze + "개");

        calendarGrid.revalidate();
        calendarGrid.repaint();
    }

    private JPanel makeEmptyCell() {
        JPanel cell = new JPanel();
        cell.setBackground(new Color(20, 22, 35));
        cell.setBorder(BorderFactory.createLineBorder(
            new Color(30, 35, 55), 1));
        return cell;
    }

    private JLabel makeMedalLabel(String text, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 14));
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

    @Override
    public void refresh() {
        manager     = new SleepRecordManager();
        goalManager = new GoalManager();
        updateCalendar();
    }
}