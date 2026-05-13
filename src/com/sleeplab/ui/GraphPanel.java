package com.sleeplab.ui;

import com.sleeplab.model.SleepRecord;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphPanel extends JPanel {

    private List<SleepRecord> records;
    private int goalMins;
    private int days = 7;

    public void setData(List<SleepRecord> records, int goalMins) {
        this.records  = records;
        this.goalMins = goalMins;
        repaint();
    }

    public void setDays(int days) {
        this.days = days;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (records == null || records.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int padL = 44, padR = 44, padT = 36, padB = 34;
        int graphW = w - padL - padR;
        int graphH = h - padT - padB;

        // 날짜 오름차순 정렬 후 최신 days개 추출
        List<SleepRecord> sorted = new ArrayList<>(records);
        sorted.sort((a, b) -> a.getDate().compareTo(b.getDate()));
        List<SleepRecord> recent = sorted.size() > days
            ? sorted.subList(sorted.size() - days, sorted.size())
            : sorted;

        int maxMins = 720;
        int n = recent.size();
        if (n == 0) return;

        // 배경 격자
        g2.setStroke(new BasicStroke(0.5f));
        for (int i = 0; i <= 4; i++) {
            int y = padT + graphH * i / 4;
            g2.setColor(new Color(40, 50, 80));
            g2.drawLine(padL, y, w - padR, y);
            int labelMins = maxMins - maxMins * i / 4;
            g2.setColor(new Color(100, 130, 180));
            g2.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
            g2.drawString(labelMins / 60 + "h", 4, y + 4);
        }

        // 목표 라인
        g2.setColor(new Color(255, 215, 0, 180));
        g2.setStroke(new BasicStroke(1.5f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
            0, new float[]{6, 4}, 0));
        int goalY = padT + graphH - (int)((double) goalMins / maxMins * graphH);
        goalY = Math.max(padT, Math.min(padT + graphH, goalY));
        g2.drawLine(padL, goalY, w - padR, goalY);
        g2.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
        g2.drawString("목표", w - padR + 2, goalY + 4);

        // 좌표 계산
        int[] xs = new int[n];
        int[] ys = new int[n];
        for (int i = 0; i < n; i++) {
            int mins = recent.get(i).getSleepMinutes();
            xs[i] = padL + (n == 1 ? graphW / 2 : graphW * i / (n - 1));
            ys[i] = padT + graphH - (int)((double) mins / maxMins * graphH);
            ys[i] = Math.max(padT, Math.min(padT + graphH, ys[i]));
        }

        // 꺾은선
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(new Color(100, 180, 255));
        for (int i = 0; i < n - 1; i++) {
            g2.drawLine(xs[i], ys[i], xs[i + 1], ys[i + 1]);
        }

        // 점 그리기
        for (int i = 0; i < n; i++) {
            int mins = recent.get(i).getSleepMinutes();
            Color dotColor = mins >= goalMins
                ? new Color(255, 215, 0)
                : new Color(255, 100, 100);
            g2.setColor(dotColor);
            g2.fillOval(xs[i] - 4, ys[i] - 4, 8, 8);
        }

        // 날짜 라벨 — 기간에 따라 자동 간격 조절
        // 최대 표시 개수를 6개로 제한
        int maxLabels = 6;
        int step = Math.max(1, (int) Math.ceil((double) n / maxLabels));

        g2.setFont(new Font("맑은 고딕", Font.PLAIN, 9));

        for (int i = 0; i < n; i++) {
            if (i % step != 0 && i != n - 1) continue;

            String date = recent.get(i).getDate();
            String label;

            // 기간에 따라 라벨 형식 변경
            if (days <= 7) {
                // 7일 이하: MM-dd
                label = date.length() >= 10 ? date.substring(5) : date;
            } else if (days <= 15) {
                // 15일 이하: dd일
                label = date.length() >= 10 ? date.substring(8) + "일" : date;
            } else {
                // 16일 이상: dd
                label = date.length() >= 10 ? date.substring(8) : date;
            }

            // 라벨 색상 — 목표 달성 여부
            int mins = recent.get(i).getSleepMinutes();
            g2.setColor(mins >= goalMins
                ? new Color(255, 215, 0, 180)
                : new Color(160, 200, 255, 180));

            int labelX = xs[i] - (label.length() * 3);
            labelX = Math.max(padL, Math.min(w - padR - 20, labelX));
            g2.drawString(label, labelX, h - 8);
        }

        // 그래프 제목
        g2.setColor(new Color(120, 160, 220));
        g2.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
        g2.drawString("최근 " + n + "일  |  노란점 = 목표달성", padL + 4, 12);
    }
}