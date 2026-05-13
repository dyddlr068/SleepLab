package com.sleeplab.ui;

import com.sleeplab.service.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class LoginPanel extends JPanel {

    private MainFrame frame;
    private UserManager userManager = new UserManager();

    private JTextField idField;
    private JPasswordField pwField;
    private JLabel messageLabel;

    private final int STAR_COUNT = 60;
    private final float[] starX    = new float[STAR_COUNT];
    private final float[] starY    = new float[STAR_COUNT];
    private final int[]   starSize = new int[STAR_COUNT];
    private final float[] starAlpha = new float[STAR_COUNT];
    private final float[] starAlphaDelta = new float[STAR_COUNT];

    private final int SHOOT_COUNT = 3;
    private final float[] shootX     = new float[SHOOT_COUNT];
    private final float[] shootY     = new float[SHOOT_COUNT];
    private final float[] shootAlpha = new float[SHOOT_COUNT];
    private final boolean[] shootActive = new boolean[SHOOT_COUNT];

    private final float moonTargetY = 40f;
    private float moonY = moonTargetY; 
    private boolean moonArrived = true;

    private float glowSize = 0f;
    private float glowDelta = 0.3f;

    private Timer animTimer;
    private Random rand = new Random();

    public LoginPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(8, 10, 26));

        for (int i = 0; i < STAR_COUNT; i++) {
            starX[i]          = rand.nextInt(500);
            starY[i]          = rand.nextInt(600);
            starSize[i]       = rand.nextInt(2) + 1;
            starAlpha[i]      = rand.nextFloat();
            starAlphaDelta[i] = (rand.nextFloat() * 0.02f + 0.005f)
                                * (rand.nextBoolean() ? 1 : -1);
        }

        for (int i = 0; i < SHOOT_COUNT; i++) {
            resetShoot(i);
            shootActive[i] = false;
        }

        JLabel title = new JLabel("SleepLab", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 32));
        title.setForeground(new Color(160, 200, 255));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("수면 루틴 처방 시스템", SwingConstants.CENTER);
        subtitle.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        subtitle.setForeground(new Color(120, 140, 180));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel idRow = new JPanel(new BorderLayout(10, 0));
        idRow.setOpaque(false);
        idRow.setBorder(BorderFactory.createEmptyBorder(6, 60, 6, 60));
        idRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        JLabel idLabel = makeLabel("아이디");
        idLabel.setPreferredSize(new Dimension(70, 30));
        idField = new JTextField();
        styleField(idField);
        idRow.add(idLabel, BorderLayout.WEST);
        idRow.add(idField, BorderLayout.CENTER);

        JPanel pwRow = new JPanel(new BorderLayout(10, 0));
        pwRow.setOpaque(false);
        pwRow.setBorder(BorderFactory.createEmptyBorder(6, 60, 6, 60));
        pwRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        JLabel pwLabel = makeLabel("비밀번호");
        pwLabel.setPreferredSize(new Dimension(70, 30));
        pwField = new JPasswordField();
        styleField(pwField);
        pwRow.add(pwLabel, BorderLayout.WEST);
        pwRow.add(pwField, BorderLayout.CENTER);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(8, 20, 0, 20));
        messageLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JPanel msgPanel = new JPanel(new BorderLayout());
        msgPanel.setOpaque(false);
        msgPanel.add(messageLabel, BorderLayout.CENTER);
        msgPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JPanel formWrapper = new JPanel();
        formWrapper.setLayout(new BoxLayout(formWrapper, BoxLayout.Y_AXIS));
        formWrapper.setOpaque(false);
        formWrapper.add(Box.createVerticalGlue());
        formWrapper.add(title);
        formWrapper.add(Box.createRigidArea(new Dimension(0, 6)));
        formWrapper.add(subtitle);
        formWrapper.add(Box.createRigidArea(new Dimension(0, 24)));
        formWrapper.add(idRow);
        formWrapper.add(Box.createRigidArea(new Dimension(0, 10)));
        formWrapper.add(pwRow);
        formWrapper.add(msgPanel);
        formWrapper.add(Box.createVerticalGlue());

        JPanel btnPanel = new JPanel(new GridLayout(2, 1, 0, 8));
        btnPanel.setOpaque(false);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(12, 60, 30, 60));

        JButton loginBtn    = makePointButton("로그인", new Color(74, 144, 217), new Color(106, 90, 238));
        JButton registerBtn = makeButton("회원가입", new Color(40, 44, 70));

        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> frame.showPanel("REGISTER"));
        pwField.addActionListener(e -> login());

        btnPanel.add(loginBtn);
        btnPanel.add(registerBtn);

        add(formWrapper, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        animTimer = new Timer(33, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAnimation();
                repaint();
            }
        });
        animTimer.start();
    }

    private void updateAnimation() {
        for (int i = 0; i < STAR_COUNT; i++) {
            starAlpha[i] += starAlphaDelta[i];
            if (starAlpha[i] >= 1f) {
                starAlpha[i] = 1f;
                starAlphaDelta[i] = -Math.abs(starAlphaDelta[i]);
            } else if (starAlpha[i] <= 0.1f) {
                starAlpha[i] = 0.1f;
                starAlphaDelta[i] = Math.abs(starAlphaDelta[i]);
            }
        }

        for (int i = 0; i < SHOOT_COUNT; i++) {
            if (!shootActive[i]) {
                if (rand.nextInt(200) == 0) {
                    shootActive[i] = true;
                    resetShoot(i);
                }
            } else {
                shootX[i] += 4f;
                shootY[i] += 2.5f;
                shootAlpha[i] -= 0.02f;
                if (shootAlpha[i] <= 0 || shootX[i] > 500) {
                    shootActive[i] = false;
                }
            }
        }

        glowSize += glowDelta;
        if (glowSize >= 15f) glowDelta = -0.3f;
        if (glowSize <= 0f)  glowDelta =  0.3f;
    }

    private void resetShoot(int i) {
        shootX[i]     = rand.nextInt(300);
        shootY[i]     = rand.nextInt(150);
        shootAlpha[i] = 1f;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        GradientPaint bg = new GradientPaint(
            0, 0, new Color(8, 10, 26),
            0, h, new Color(18, 14, 40)
        );
        g2.setPaint(bg);
        g2.fillRect(0, 0, w, h);

        for (int i = 0; i < STAR_COUNT; i++) {
            int alpha = (int)(starAlpha[i] * 255);
            g2.setColor(new Color(255, 255, 255, Math.min(255, alpha)));
            g2.fillOval((int) starX[i], (int) starY[i], starSize[i], starSize[i]);
        }

        for (int i = 0; i < SHOOT_COUNT; i++) {
            if (shootActive[i]) {
                int alpha = (int)(shootAlpha[i] * 200);
                g2.setColor(new Color(255, 255, 255, Math.min(255, alpha)));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawLine(
                    (int) shootX[i], (int) shootY[i],
                    (int) shootX[i] - 20, (int) shootY[i] - 12
                );
            }
        }

        int moonX    = w - 100;
        int moonSize = 60;
        int glow1    = (int)(glowSize * 2);

        g2.setColor(new Color(255, 245, 180, 20));
        g2.fillOval(moonX - 20 - glow1, (int) moonY - 20 - glow1,
                    moonSize + 40 + glow1 * 2, moonSize + 40 + glow1 * 2);
        g2.setColor(new Color(255, 245, 180, 35));
        g2.fillOval(moonX - 10, (int) moonY - 10,
                    moonSize + 20, moonSize + 20);

        g2.setColor(new Color(255, 245, 200, 220));
        g2.fillOval(moonX, (int) moonY, moonSize, moonSize);

        g2.setColor(new Color(8, 10, 26, 200));
        g2.fillOval(moonX + 12, (int) moonY - 8, moonSize, moonSize);
    }

    private void login() {
        String id = idField.getText().trim();
        String pw = new String(pwField.getPassword()).trim();

        if (id.isBlank() || pw.isBlank()) {
            showError("아이디와 비밀번호를 입력해주세요.");
            return;
        }

        if (userManager.login(id, pw)) {
            messageLabel.setText("");
            idField.setText("");
            pwField.setText("");
            animTimer.stop();
            frame.loginSuccess(id);
        } else {
            showError("아이디 또는 비밀번호가 올바르지 않습니다.");
            pwField.setText("");
        }
    }

    private void showError(String msg) {
        messageLabel.setText("<html><div style='text-align:center;'>"
            + "<font color='#FF6B6B'>[오류] " + msg + "</font>"
            + "</div></html>");
    }

    private JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        label.setForeground(new Color(180, 200, 240));
        return label;
    }

    private JTextField styleField(JTextField field) {
        field.setBackground(new Color(25, 28, 52, 200));
        field.setForeground(new Color(220, 230, 255));
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 90, 140), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        return field;
    }

    private JPasswordField styleField(JPasswordField field) {
        field.setBackground(new Color(25, 28, 52, 200));
        field.setForeground(new Color(220, 230, 255));
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 90, 140), 1, true),
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
        return btn;
    }

    private JButton makeButton(String text, Color color) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(new Color(180, 200, 240));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(60, 65, 100));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(color);
            }
        });
        return btn;
    }

    public void refresh() {
        idField.setText("");
        pwField.setText("");
        messageLabel.setText("");
        moonY = moonTargetY; 
        moonArrived = true;
        if (!animTimer.isRunning()) animTimer.start();
    }
}