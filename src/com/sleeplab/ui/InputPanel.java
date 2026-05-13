package com.sleeplab.ui;

import com.sleeplab.exception.*;
import com.sleeplab.model.SleepRecord;
import com.sleeplab.service.SleepRecordManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class InputPanel extends JPanel implements Refreshable {

    private MainFrame frame;
    private SleepRecordManager manager = new SleepRecordManager();

    private JTextField dateField;
    private JTextField bedTimeField;
    private JTextField wakeTimeField;
    private JComboBox<String> qualityBox;
    private JComboBox<String> conditionBox;
    private JLabel messageLabel;

    public InputPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(18, 18, 28));

        // 타이틀
        JLabel title = new JLabel("수면 기록 입력", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        title.setForeground(new Color(160, 200, 255));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        // 입력 폼
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 15));
        formPanel.setBackground(new Color(18, 18, 28));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        dateField = new JTextField(LocalDate.now().toString());
        bedTimeField  = new JTextField();
        wakeTimeField = new JTextField();
        qualityBox    = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        conditionBox  = new JComboBox<>(new String[]{"좋음", "보통", "나쁨"});

        formPanel.add(makeLabel("날짜 (예: 2025-11-04)"));
        formPanel.add(styleField(dateField));
        formPanel.add(makeLabel("취침 시각 (예: 23:30)"));
        formPanel.add(styleField(bedTimeField));
        formPanel.add(makeLabel("기상 시각 (예: 07:00)"));
        formPanel.add(styleField(wakeTimeField));
        formPanel.add(makeLabel("수면 품질 (1~5)"));
        formPanel.add(styleCombo(qualityBox));
        formPanel.add(makeLabel("오늘 컨디션"));
        formPanel.add(styleCombo(conditionBox));

        // 메시지 라벨
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

        JPanel msgPanel = new JPanel(new BorderLayout());
        msgPanel.setBackground(new Color(18, 18, 28));
        msgPanel.add(messageLabel, BorderLayout.CENTER);

        // 버튼
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setBackground(new Color(18, 18, 28));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 30, 50));

        JButton saveBtn = makeButton("저장", new Color(40, 80, 140));
        JButton backBtn = makeButton("← 뒤로", new Color(50, 50, 70));

        saveBtn.addActionListener(e -> saveRecord());
        backBtn.addActionListener(e -> frame.showPanel("MAIN"));

        btnPanel.add(saveBtn);
        btnPanel.add(backBtn);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(18, 18, 28));
        centerPanel.add(formPanel);
        centerPanel.add(msgPanel);

        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void saveRecord() {
        String date      = dateField.getText().trim();
        String bedTime   = bedTimeField.getText().trim();
        String wakeTime  = wakeTimeField.getText().trim();
        int quality      = Integer.parseInt((String) qualityBox.getSelectedItem());
        String condition = (String) conditionBox.getSelectedItem();

        // 날짜 검증
        try {
            LocalDate parsed  = LocalDate.parse(date);
            LocalDate minDate = LocalDate.of(2000, 1, 1);
            LocalDate maxDate = LocalDate.now();
            if (parsed.isBefore(minDate) || parsed.isAfter(maxDate)) {
                showError("날짜는 2000-01-01 부터 " + maxDate + " 사이만 입력 가능합니다.");
                return;
            }
        } catch (DateTimeParseException e) {
            showError("존재하지 않는 날짜입니다.<br>2000-01-01 ~ "
                + LocalDate.now() + " 사이로 입력해주세요.");
            return;
        }

        // 시각 형식 검증
        if (!bedTime.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
            showError("취침 시각 형식이 올바르지 않습니다. (예: 23:30)");
            return;
        }
        if (!wakeTime.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
            showError("기상 시각 형식이 올바르지 않습니다. (예: 07:00)");
            return;
        }

        // 중복 날짜 확인
        if (manager.isDuplicate(date)) {
            showError(date + " 날짜의 기록이 이미 존재합니다.");
            return;
        }

        // 취침/기상 시각 역전 확인
        try {
            int bedM  = toMinutes(bedTime);
            int wakeM = toMinutes(wakeTime);
            if (bedM > 12 * 60 && wakeM > 12 * 60 && bedM > wakeM) {
                throw new InvalidSleepTimeException("취침 시각이 기상 시각보다 늦습니다.");
            }
        } catch (InvalidSleepTimeException e) {
            showError(e.getMessage());
            return;
        }

        SleepRecord record = new SleepRecord(date, bedTime, wakeTime, quality, condition);
        manager.save(record);
        showSuccess(date + " 기록이 저장되었습니다!");
        clearFields();
    }

    private int toMinutes(String time) {
        String[] p = time.split(":");
        return Integer.parseInt(p[0]) * 60 + Integer.parseInt(p[1]);
    }

    private void showError(String msg) {
        messageLabel.setText("<html><div style='text-align:center;'><font color='#FF6B6B'> " + msg + "</font></div></html>");
    }

    private void showSuccess(String msg) {
        messageLabel.setText("<html><div style='text-align:center;'><font color='#6BFF9E'>" + msg + "</font></div></html>");
    }

    private void clearFields() {
    	dateField.setText(LocalDate.now().toString());
        bedTimeField.setText("");
        wakeTimeField.setText("");
        qualityBox.setSelectedIndex(2);
        conditionBox.setSelectedIndex(1);
    }

    private JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        label.setForeground(new Color(180, 200, 240));
        return label;
    }

    private JTextField styleField(JTextField field) {
        field.setBackground(new Color(30, 35, 55));
        field.setForeground(new Color(220, 230, 255));
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        field.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        return field;
    }

    private JComboBox<String> styleCombo(JComboBox<String> box) {
        box.setBackground(new Color(30, 35, 55));
        box.setForeground(new Color(220, 230, 255));
        box.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        return box;
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
        clearFields();
        messageLabel.setText("");
    }
}