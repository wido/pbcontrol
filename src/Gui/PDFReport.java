package Gui;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.io.File;
import Log.Logger;

public class PDFReport {

    private Document report;
    private BaseFont bf_courier;
    private BaseFont bf_helv;
    private PdfWriter writer;
    private PdfContentByte cb;

    private static final String DATE_FORMAT_NOW_SPACES = "dd-MM-yyyy HH:mm";

    private String blueTeamName = "";
    private String redTeamName = "";

    private int roundTime = 0;
    private int respawnTime = 0;
    private int blueScore = 0;
    private int redScore = 0;
    private int blueBonus = 0;
    private int redBonus = 0;

    private int[] blueFlagScore = {0,0,0};
    private int[] redFlagScore = {0,0,0};
    private int[] swingFlagScore = {0,0,0};

    private Font tableHeaderFont;
    private Font tableSmallHeaderFont;
    private Font tableCellFont;
    private Font tableCellFontBlue;
    private Font tableCellFontRed;

    public PDFReport(String reportFile) {
        try {

            this.report = new Document(PageSize.A4, 50, 50, 50, 50);
            this.writer = PdfWriter.getInstance(this.report, new FileOutputStream(reportFile));
            this.writer.setBoxSize("gamereport", new Rectangle(36, 54, 559, 788));

            this.bf_courier = BaseFont.createFont(BaseFont.COURIER, "Cp1252", false);
            this.bf_helv = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);

            this.tableHeaderFont = FontFactory.getFont(FontFactory.COURIER, 18, Font.BOLD);
            this.tableSmallHeaderFont = FontFactory.getFont(FontFactory.COURIER, 16, Font.ITALIC);
            this.tableCellFont =  FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD);
            this.tableCellFontBlue =  FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD, BaseColor.BLUE);
            this.tableCellFontRed =  FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD, BaseColor.RED);

        } catch (Exception e) {
            Logger.msg("Error", "Error while initiating PDF report: " + e.getMessage());
        }
    }

    public void setBlueTeamName(String name) {
        this.blueTeamName = name;
    }

    public void setRedTeamName(String name) {
        this.redTeamName = name;
    }

    public void setRoundTime(int roundtime) {
        this.roundTime = roundtime;
    }

    public void setRespawnTime(int respawntime) {
        this.respawnTime = respawntime;
    }

    public void setBlueScore(int bluescore) {
        this.blueScore = bluescore;
    }

    public void setRedScore(int redscore) {
        this.redScore = redscore;
    }

    public void setBlueFlagScore(int[] score) {
        this.blueFlagScore = score;
    }

    public void setRedFlagScore(int[] score) {
        this.redFlagScore = score;
    }

    public void setSwingFlagScore(int[] score) {
        this.swingFlagScore = score;
    }

    public void setBlueBonus(int bonus) {
        this.blueBonus = bonus;
    }

    public void setRedBonus(int bonus) {
        this.redBonus = bonus;
    }

    public void generateReport() {
        try {

            this.report.open();
            this.cb = this.writer.getDirectContent();
            this.drawHeaderText();
            this.drawGameDateTime();
            this.drawTeamNames();
            this.report.add(new Paragraph(" "));
            this.report.add(new Paragraph(" "));
            this.report.add(new Paragraph(" "));
            this.report.add(new Paragraph(" "));
            this.drawGameSettings();
            this.report.add(new Paragraph(" "));
            this.drawScore();
            this.report.add(new Paragraph(" "));
            this.drawFlagScore();
            this.report.add(new Paragraph(" "));
            this.drawBonus();
            this.drawSignatureArea();
            this.drawFooterText();
            this.report.close();

        } catch (Exception e) {
            Logger.msg("Error", "Error while generating PDF report: " + e.getMessage());
        }

    }

    private void drawHeaderText() {
        this.cb.beginText();
        this.cb.setFontAndSize(this.bf_helv, 32);

        this.cb.showTextAligned(Element.ALIGN_CENTER, "Game report", 295, 800, 0);

        this.cb.endText();
    }

    private void drawGameDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW_SPACES);

        this.cb.beginText();
        this.cb.setFontAndSize(this.bf_courier, 14);

        this.cb.showTextAligned(Element.ALIGN_CENTER, "Date / Time: " + sdf.format(Calendar.getInstance().getTime()), 295, 775, 0);

        this.cb.endText();
    }

    private void drawTeamNames() {
        this.cb.beginText();
        this.cb.setFontAndSize(this.bf_courier, 14);

        this.cb.showTextAligned(Element.ALIGN_CENTER, "Blue team: " + this.blueTeamName + " / Red team: " + this.redTeamName, 295, 750, 0);

        this.cb.endText();
    }

    private void drawGameSettings() {
        try {
            PdfPTable settingsTable = new PdfPTable(2);
            settingsTable.setWidthPercentage(65);

            settingsTable.addCell(this.getSmallHeaderCell("Settings", 2));

            settingsTable.addCell(this.getHeaderCell("Game time"));
            settingsTable.addCell(this.getHeaderCell("Respawn time"));

            settingsTable.addCell(this.getNormalCell(this.roundTime + ""));
            settingsTable.addCell(this.getNormalCell(this.respawnTime + ""));

            this.report.add(settingsTable);
        } catch (Exception e) {
            Logger.msg("Error", "Could not draw settings table: " + e.getMessage());
        }
    }

    private void drawScore() {
        try {
            PdfPTable scoreTable = new PdfPTable(2);
            scoreTable.setWidthPercentage(65);

            scoreTable.addCell(this.getSmallHeaderCell("Scores", 2));

            scoreTable.addCell(this.getHeaderCell("Blue team"));
            scoreTable.addCell(this.getHeaderCell("Red team"));

            scoreTable.addCell(this.getNormalCell(((this.blueScore / 60) + this.blueBonus) + "", this.tableCellFontBlue));
            scoreTable.addCell(this.getNormalCell(((this.redScore / 60) + this.redBonus ) + "", this.tableCellFontRed));

            this.report.add(scoreTable);
        } catch (Exception e) {
            Logger.msg("Error", "Could not draw score table: " + e.getMessage());
        }
    }

    private void drawFlagScore() {
        try {
            PdfPTable flagScoreTable = new PdfPTable(3);
            flagScoreTable.setWidthPercentage(75);

            flagScoreTable.addCell(this.getSmallHeaderCell("Flag scores", 3));

            flagScoreTable.addCell(this.getHeaderCell("Flag"));
            flagScoreTable.addCell(this.getHeaderCell("Blue team"));
            flagScoreTable.addCell(this.getHeaderCell("Red team"));

            flagScoreTable.addCell(this.getNormalCell("Blue", this.tableCellFontBlue));
            flagScoreTable.addCell(this.getNormalCell(this.secondsToHourMinuteText(blueFlagScore[1])));
            flagScoreTable.addCell(this.getNormalCell(this.secondsToHourMinuteText(blueFlagScore[2])));

            flagScoreTable.addCell(this.getNormalCell("Red", this.tableCellFontRed));
            flagScoreTable.addCell(this.getNormalCell(this.secondsToHourMinuteText(redFlagScore[1])));
            flagScoreTable.addCell(this.getNormalCell(this.secondsToHourMinuteText(redFlagScore[2])));

            flagScoreTable.addCell(this.getNormalCell("Swing"));
            flagScoreTable.addCell(this.getNormalCell(this.secondsToHourMinuteText(swingFlagScore[1])));
            flagScoreTable.addCell(this.getNormalCell(this.secondsToHourMinuteText(swingFlagScore[2])));

            this.report.add(flagScoreTable);
        } catch (Exception e) {
            Logger.msg("Error", "Could not draw score table: " + e.getMessage());
        }
    }

    private void drawBonus() {
        try {
            PdfPTable bonusTable = new PdfPTable(2);
            bonusTable.setWidthPercentage(65);

            bonusTable.addCell(this.getSmallHeaderCell("Bonus / Penalty", 2));

            bonusTable.addCell(this.getHeaderCell("Blue team"));
            bonusTable.addCell(this.getHeaderCell("Red team"));

            bonusTable.addCell(this.getNormalCell(this.blueBonus + "", this.tableCellFontBlue));
            bonusTable.addCell(this.getNormalCell(this.redBonus + "", this.tableCellFontRed));

            this.report.add(bonusTable);
        } catch (Exception e) {
            Logger.msg("Error", "Could not draw score table: " + e.getMessage());
        }
    }

    private void drawSignatureArea() {
        this.cb.setLineWidth(0f);

        // Linker sig lijn
        this.cb.moveTo(50, 75);
        this.cb.lineTo(200, 75);

        // Rechter sig lijn
        this.cb.moveTo(350, 75);
        this.cb.lineTo(500, 75);

        this.cb.stroke();

        this.cb.beginText();
        this.cb.setFontAndSize(this.bf_courier, 16);
        this.cb.showTextAligned(Element.ALIGN_LEFT, "Blue team", 50, 125, 0);
        this.cb.showTextAligned(Element.ALIGN_LEFT, "Red team", 350, 125, 0);
        this.cb.endText();
    }

    private void drawFooterText() {
        this.cb.beginText();
        this.cb.setFontAndSize(this.bf_helv, 12);
        this.cb.showTextAligned(Element.ALIGN_CENTER, "Powered by PBControl ( www.pbcontrol.net )", 295, 20, 0);
        this.cb.endText();
    }

    private PdfPCell getSmallHeaderCell(String text, int colspan) {
        PdfPCell headerCell = new PdfPCell(new Paragraph(text, this.tableSmallHeaderFont));
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setPadding(7);
        headerCell.setColspan(colspan);
        headerCell.setBackgroundColor(BaseColor.WHITE);
        return headerCell;
    }

    private PdfPCell getHeaderCell(String text) {
        PdfPCell headerCell = new PdfPCell(new Paragraph(text, this.tableHeaderFont));
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setPadding(10);
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        return headerCell;
    }

    private PdfPCell getNormalCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(10);
        return cell;
    }

    private PdfPCell getNormalCell(String text) {
        return this.getNormalCell(text, this.tableCellFont);
    }

    private String secondsToHourMinuteText(int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        return ((minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds);
    }

}