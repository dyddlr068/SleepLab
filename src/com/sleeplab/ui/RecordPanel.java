package com.sleeplab.ui;

import com.sleeplab.model.SleepRecord;
import com.sleeplab.service.SleepRecordManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RecordPanel extends JPanel implements Refreshable {

    private MainFrame frame;
    private SleepRecordManager manager = new SleepRecordManager();

    private DefaultTableModel tableModel;
    private JLabel avgLabel;
    private JLabel messageLabel;

    public RecordPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(18, 18, 28));

        // 타이틀
        JLabel title = new JLabel("기록 전체 보기", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        title.setForeground(new Color(160, 200, 255));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        // 테이블
        String[] columns = {"선택", "날짜", "취침", "기상", "수면시간", "품질", "컨디션"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 0;
            }
            @Override
            public Class<?> getColumnClass(int col) {
                return col == 0 ? Boolean.class : String.class;
            }
        };

        JTable table = new JTable(tableModel);
        table.setBackground(new Color(25, 28, 45));
        table.setForeground(new Color(200, 220, 255));
        table.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.getTableHeader().setBackground(new Color(40, 50, 90));
        table.getTableHeader().setForeground(new Color(160, 200, 255));
        table.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 13));

        // 체크박스 컬럼 너비
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(0).setMaxWidth(40);

        // 나머지 컬럼 가운데 정렬
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setBackground(new Color(25, 28, 45));
        centerRenderer.setForeground(new Color(200, 220, 255));
        for (int i = 1; i < columns.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scrollPane.getViewport().setBackground(new Color(25, 28, 45));

        // 평균 라벨
        avgLabel = new JLabel("", SwingConstants.CENTER);
        avgLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        avgLabel.setForeground(new Color(160, 200, 255));
        avgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        avgLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel avgPanel = new JPanel(new BorderLayout());
        avgPanel.setBackground(new Color(18, 18, 28));
        avgPanel.add(avgLabel, BorderLayout.CENTER);

        // 메시지 라벨
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        JPanel msgPanel = new JPanel(new BorderLayout());
        msgPanel.setBackground(new Color(18, 18, 28));
        msgPanel.add(messageLabel, BorderLayout.CENTER);

        // 버튼
        JPanel btnPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        btnPanel.setBackground(new Color(18, 18, 28));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));

        JButton allSelectBtn = makeButton("전체 선택", new Color(50, 80, 120));
        JButton editBtn      = makeButton("선택 수정", new Color(60, 100, 60));
        JButton deleteBtn    = makeButton("선택 삭제", new Color(140, 40, 40));
        JButton backBtn      = makeButton("← 뒤로",   new Color(50, 50, 70));

        allSelectBtn.addActionListener(e -> toggleSelectAll());
        editBtn.addActionListener(e -> editSelected());
        deleteBtn.addActionListener(e -> deleteSelected());
        backBtn.addActionListener(e -> frame.showPanel("MAIN"));

        btnPanel.add(allSelectBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(backBtn);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(new Color(18, 18, 28));
        bottomPanel.add(avgPanel);
        bottomPanel.add(msgPanel);
        bottomPanel.add(btnPanel);

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // 전체 선택 / 전체 해제 토글
    private void toggleSelectAll() {
        boolean allChecked = true;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (!(Boolean) tableModel.getValueAt(i, 0)) {
                allChecked = false;
                break;
            }
        }
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt(!allChecked, i, 0);
        }
    }

    // 선택 수정
    private void editSelected() {
        List<Integer> selectedRows = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if ((Boolean) tableModel.getValueAt(i, 0)) {
                selectedRows.add(i);
            }
        }

        if (selectedRows.isEmpty()) {
            showMessage("수정할 기록을 선택해주세요.", false);
            return;
        }
        if (selectedRows.size() > 1) {
            showMessage("수정은 1개씩만 가능합니다.", false);
            return;
        }

        int row = selectedRows.get(0);
        String date = (String) tableModel.getValueAt(row, 1);

        // 수정 팝업창
        JDialog dialog = new JDialog();
        dialog.setTitle(date + " 기록 수정");
        dialog.setSize(340, 280);
        dialog.setLocationRelativeTo(this);
        dialog.setModal(true);
        dialog.getContentPane().setBackground(new Color(18, 18, 28));
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 12));
        formPanel.setBackground(new Color(18, 18, 28));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        JTextField bedTimeField  = new JTextField((String) tableModel.getValueAt(row, 2));
        JTextField wakeTimeField = new JTextField((String) tableModel.getValueAt(row, 3));
        JComboBox<String> qualityBox   = new JComboBox<>(new String[]{"1","2","3","4","5"});
        JComboBox<String> conditionBox = new JComboBox<>(new String[]{"좋음","보통","나쁨"});

        // 기존값 세팅
        String currentQuality   = (String) tableModel.getValueAt(row, 5);
        String currentCondition = (String) tableModel.getValueAt(row, 6);
        int qualityCount = (int) currentQuality.chars().filter(c -> c == '★').count();
        qualityBox.setSelectedIndex(qualityCount - 1);
        conditionBox.setSelectedItem(currentCondition);

        styleField(bedTimeField);
        styleField(wakeTimeField);
        styleCombo(qualityBox);
        styleCombo(conditionBox);

        formPanel.add(makeLabel("취침 시각"));
        formPanel.add(bedTimeField);
        formPanel.add(makeLabel("기상 시각"));
        formPanel.add(wakeTimeField);
        formPanel.add(makeLabel("수면 품질 (1~5)"));
        formPanel.add(qualityBox);
        formPanel.add(makeLabel("컨디션"));
        formPanel.add(conditionBox);

        // 버튼
        JPanel dialogBtnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        dialogBtnPanel.setBackground(new Color(18, 18, 28));
        dialogBtnPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));

        JButton saveBtn   = makeButton("저장", new Color(40, 80, 140));
        JButton cancelBtn = makeButton("취소", new Color(50, 50, 70));

        saveBtn.addActionListener(e -> {
            String bedTime  = bedTimeField.getText().trim();
            String wakeTime = wakeTimeField.getText().trim();
            int quality     = Integer.parseInt((String) qualityBox.getSelectedItem());
            String condition = (String) conditionBox.getSelectedItem();

            if (!bedTime.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                JOptionPane.showMessageDialog(dialog,
                    "취침 시각 형식이 올바르지 않습니다. (예: 23:30)");
                return;
            }
            if (!wakeTime.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                JOptionPane.showMessageDialog(dialog,
                    "기상 시각 형식이 올바르지 않습니다. (예: 07:00)");
                return;
            }

            SleepRecord updated = new SleepRecord(date, bedTime, wakeTime, quality, condition);
            manager.update(date, updated);
            dialog.dispose();
            refresh();
            showMessage(date + " 기록이 수정되었습니다.", true);
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        dialogBtnPanel.add(saveBtn);
        dialogBtnPanel.add(cancelBtn);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(dialogBtnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    // 체크된 항목 삭제
    private void deleteSelected() {
        List<String> toDelete = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if ((Boolean) tableModel.getValueAt(i, 0)) {
                toDelete.add((String) tableModel.getValueAt(i, 1));
            }
        }

        if (toDelete.isEmpty()) {
            showMessage("삭제할 기록을 선택해주세요.", false);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            toDelete.size() + "개의 기록을 삭제하시겠습니까?",
            "삭제 확인",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            for (String date : toDelete) {
                manager.delete(date);
            }
            refresh();
            showMessage(toDelete.size() + "개의 기록이 삭제되었습니다.", true);
        }
    }

    private void showMessage(String msg, boolean success) {
        String color = success ? "#6BFF9E" : "#FF6B6B";
        messageLabel.setText("<html><div style='text-align:center;'>"
            + "<font color='" + color + "'>" + msg + "</font></div></html>");
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
        tableModel.setRowCount(0);
        messageLabel.setText("");

        List<SleepRecord> records = manager.getRecords();
        if (records.isEmpty()) {
            avgLabel.setText("기록이 없습니다.");
            return;
        }

        records.sort((a, b) -> b.getDate().compareTo(a.getDate()));
        for (SleepRecord r : records) {
            int mins = r.getSleepMinutes();
            String stars = "★".repeat(r.getSleepQuality())
                         + "☆".repeat(5 - r.getSleepQuality());
            tableModel.addRow(new Object[]{
                false,
                r.getDate(), r.getBedTime(), r.getWakeTime(),
                String.format("%dh %02dm", mins / 60, mins % 60),
                stars, r.getCondition()
            });
        }

        double avgMins = records.stream()
            .mapToInt(SleepRecord::getSleepMinutes).average().orElse(0);
        avgLabel.setText(String.format("평균 수면: %dh %02dm",
            (int) avgMins / 60, (int) avgMins % 60));
    }
}