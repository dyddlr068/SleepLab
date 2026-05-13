package com.sleeplab.ui;

import com.sleeplab.service.UserManager;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {

    private MainFrame frame;
    private UserManager userManager = new UserManager();

    private JTextField idField;
    private JPasswordField pwField;
    private JPasswordField pwConfirmField;
    private JLabel messageLabel;

    public RegisterPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(18, 18, 28));

        // 타이틀
        JLabel title = new JLabel("회원가입", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        title.setForeground(new Color(160, 200, 255));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("SleepLab 계정을 만들어주세요", SwingConstants.CENTER);
        subtitle.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        subtitle.setForeground(new Color(120, 140, 180));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 아이디 행
        JPanel idRow = new JPanel(new BorderLayout(10, 0));
        idRow.setBackground(new Color(18, 18, 28));
        idRow.setBorder(BorderFactory.createEmptyBorder(6, 60, 6, 60));
        idRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        JLabel idLabel = makeLabel("아이디");
        idLabel.setPreferredSize(new Dimension(70, 30));
        idField = new JTextField();
        styleField(idField);
        idRow.add(idLabel, BorderLayout.WEST);
        idRow.add(idField, BorderLayout.CENTER);

        // 비밀번호 행
        JPanel pwRow = new JPanel(new BorderLayout(10, 0));
        pwRow.setBackground(new Color(18, 18, 28));
        pwRow.setBorder(BorderFactory.createEmptyBorder(6, 60, 6, 60));
        pwRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        JLabel pwLabel = makeLabel("비밀번호");
        pwLabel.setPreferredSize(new Dimension(70, 30));
        pwField = new JPasswordField();
        styleField(pwField);
        pwRow.add(pwLabel, BorderLayout.WEST);
        pwRow.add(pwField, BorderLayout.CENTER);

        // 비밀번호 확인 행
        JPanel pwConfirmRow = new JPanel(new BorderLayout(10, 0));
        pwConfirmRow.setBackground(new Color(18, 18, 28));
        pwConfirmRow.setBorder(BorderFactory.createEmptyBorder(6, 60, 6, 60));
        pwConfirmRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        JLabel pwConfirmLabel = makeLabel("재확인");
        pwConfirmLabel.setPreferredSize(new Dimension(70, 30));
        pwConfirmField = new JPasswordField();
        styleField(pwConfirmField);
        pwConfirmRow.add(pwConfirmLabel, BorderLayout.WEST);
        pwConfirmRow.add(pwConfirmField, BorderLayout.CENTER);

        // 메시지
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(8, 20, 0, 20));
        messageLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JPanel msgPanel = new JPanel(new BorderLayout());
        msgPanel.setBackground(new Color(18, 18, 28));
        msgPanel.add(messageLabel, BorderLayout.CENTER);
        msgPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // 폼 전체 세로 가운데 정렬
        JPanel formWrapper = new JPanel();
        formWrapper.setLayout(new BoxLayout(formWrapper, BoxLayout.Y_AXIS));
        formWrapper.setBackground(new Color(18, 18, 28));
        formWrapper.add(Box.createVerticalGlue());
        formWrapper.add(title);
        formWrapper.add(Box.createRigidArea(new Dimension(0, 6)));
        formWrapper.add(subtitle);
        formWrapper.add(Box.createRigidArea(new Dimension(0, 24)));
        formWrapper.add(idRow);
        formWrapper.add(Box.createRigidArea(new Dimension(0, 10)));
        formWrapper.add(pwRow);
        formWrapper.add(Box.createRigidArea(new Dimension(0, 10)));
        formWrapper.add(pwConfirmRow);
        formWrapper.add(msgPanel);
        formWrapper.add(Box.createVerticalGlue());

        // 버튼
        JPanel btnPanel = new JPanel(new GridLayout(2, 1, 0, 8));
        btnPanel.setBackground(new Color(18, 18, 28));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(12, 60, 30, 60));

        JButton registerBtn = makePointButton("가입하기", new Color(74, 144, 217), new Color(106, 90, 238));
        JButton backBtn     = makeButton("← 로그인으로", new Color(50, 50, 70));

        registerBtn.addActionListener(e -> register());
        backBtn.addActionListener(e -> frame.showPanel("LOGIN"));

        btnPanel.add(registerBtn);
        btnPanel.add(backBtn);

        add(formWrapper, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void register() {
        String id        = idField.getText().trim();
        String pw        = new String(pwField.getPassword()).trim();
        String pwConfirm = new String(pwConfirmField.getPassword()).trim();

        if (id.isBlank() || pw.isBlank()) {
            showError("아이디와 비밀번호를 입력해주세요.");
            return;
        }
        if (id.length() < 4) {
            showError("아이디는 4자 이상 입력해주세요.");
            return;
        }
        if (pw.length() < 4) {
            showError("비밀번호는 4자 이상 입력해주세요.");
            return;
        }
        if (!pw.equals(pwConfirm)) {
            showError("비밀번호가 일치하지 않습니다.");
            pwConfirmField.setText("");
            return;
        }
        if (userManager.exists(id)) {
            showError("이미 사용 중인 아이디입니다.");
            return;
        }

        boolean success = userManager.register(id, pw);
        if (success) {
            messageLabel.setText("<html><div style='text-align:center;'>"
                + "<font color='#6BFF9E'>가입을 축하합니다! 로그인해주세요.</font>"
                + "</div></html>");
            clearFields();
            Timer timer = new Timer(2000, e -> frame.showPanel("LOGIN"));
            timer.setRepeats(false);
            timer.start();
        } else {
            showError("회원가입에 실패했습니다. 다시 시도해주세요.");
        }
    }

    private void showError(String msg) {
        messageLabel.setText("<html><div style='text-align:center;'>"
            + "<font color='#FF6B6B'>[오류] " + msg + "</font>"
            + "</div></html>");
    }

    private void clearFields() {
        idField.setText("");
        pwField.setText("");
        pwConfirmField.setText("");
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
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 70, 100), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        return field;
    }

    private JPasswordField styleField(JPasswordField field) {
        field.setBackground(new Color(30, 35, 55));
        field.setForeground(new Color(220, 230, 255));
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 70, 100), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        return field;
    }

    private JButton makePointButton(String text, Color color1, Color color2) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                    0, 0, color1,
                    getWidth(), 0, color2
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setForeground(new Color(220, 220, 220));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setForeground(Color.WHITE);
            }
        });
        return btn;
    }

    private JButton makeButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(new Color(180, 200, 240));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(70, 70, 100));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(color);
            }
        });
        return btn;
    }

    public void refresh() {
        clearFields();
        messageLabel.setText("");
    }
}