package Gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.File;
import Log.Logger;
import Game.Settings;
import Game.Game;
import java.lang.Math;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class MainGUI extends JApplet implements ActionListener {

    private JTabbedPane tabbedPane;

    /* Tabs */
    private JPanel mainTab, diagTab, logTab, reportTab;

    /* Widgets */
    private JPanel blueScorePane;
    private JPanel redScorePane;
    private JPanel flagScorePane;
    private JPanel blueFlagScorePane;
    private JPanel redFlagScorePane;
    private JPanel swingFlagScorePane;
    private JPanel controlPane;
    private JPanel timePane;
    private JPanel gameTimePane;
    private JPanel respawnTimePane;
    private JPanel countdownTimePane;
    private JPanel settingsPane;
    private JPanel bottomPane;
    private JPanel flagControlPane;
    private JPanel packetsSendPane;
    private JPanel packetsReceivedBluePane;
    private JPanel packetsReceivedRedPane;
    private JPanel packetsReceivedSwingPane;
    private JPanel packetsReceivedContainer;
    private JPanel reportPane;
    private JPanel bonusPane;
    private JPanel blueBonusPane;
    private JPanel redBonusPane;

    protected JLabel gameTime;
    protected JLabel respawnTime;
    protected JLabel countdownTime;
    protected JLabel blueScore;
    protected JLabel redScore;
    protected JLabel blueFlagScoreBlue;
    protected JLabel blueFlagScoreRed;
    protected JLabel redFlagScoreBlue;
    protected JLabel redFlagScoreRed;
    protected JLabel swingFlagScoreBlue;
    protected JLabel swingFlagScoreRed;
    protected JLabel packetsSend;
    protected JLabel packetsReceivedBlue;
    protected JLabel packetsReceivedRed;
    protected JLabel packetsReceivedSwing;
    private JLabel teamName;

    private JButton startButton, stopButton, resetButton, freezeButton, reverseButton, reportButton;

    private JComboBox flagAction;

    private JSpinner respawnTimeSpinner, gameTimeSpinner, blueBonusSpinner, redBonusSpinner;

    protected JTextArea loglines;

    private JTextField blueTeamName;
    private JTextField redTeamName;

    private JScrollPane loglinesScoller;

    private int gameTimeSetting, respawnTimeSetting;

    private Control control;

    protected Font ttfDigitalBig, ttfDigitalMedium, ttfDigitalSmall = null;

    public void init() {

	try {
	    /* Aangezien we in een JAR zitten, moeten we files anders laden */
	    Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/resource/fonts/SFDigitalReadout-Heavy.ttf"));
	    ttfDigitalBig = ttfBase.deriveFont(Font.TRUETYPE_FONT, 60);
	    ttfDigitalSmall = ttfBase.deriveFont(Font.TRUETYPE_FONT, 48);
	    ttfDigitalMedium = ttfBase.deriveFont(Font.TRUETYPE_FONT, 54);
	} catch (Exception e) {
	    Logger.msg("Error", "Error loading font: " + e.getMessage());
	}

	blueScorePane = new JPanel();
	blueScorePane.setBorder(BorderFactory.createTitledBorder("Blue Team"));

	redScorePane = new JPanel();
	redScorePane.setBorder(BorderFactory.createTitledBorder("Red Team"));

	flagScorePane = new JPanel();
	flagScorePane.setBorder(BorderFactory.createTitledBorder("Flag scores"));

	blueFlagScorePane = new JPanel();
	blueFlagScorePane.setBorder(BorderFactory.createTitledBorder("Blue"));

	redFlagScorePane = new JPanel();
	redFlagScorePane.setBorder(BorderFactory.createTitledBorder("Red"));

	swingFlagScorePane = new JPanel();
	swingFlagScorePane.setBorder(BorderFactory.createTitledBorder("Swing"));

	controlPane = new JPanel();
	controlPane.setBorder(BorderFactory.createTitledBorder("Game control"));

	flagControlPane = new JPanel();
	flagControlPane.setBorder(BorderFactory.createTitledBorder("Flag control"));

	timePane = new JPanel();
	timePane.setBorder(BorderFactory.createTitledBorder("Timers"));

	gameTimePane = new JPanel();
	gameTimePane.setBorder(BorderFactory.createTitledBorder("Game time"));

	respawnTimePane = new JPanel();
	respawnTimePane.setBorder(BorderFactory.createTitledBorder("Respawn"));

	countdownTimePane = new JPanel();
	countdownTimePane.setBorder(BorderFactory.createTitledBorder("Start"));

	settingsPane = new JPanel();
	settingsPane.setBorder(BorderFactory.createTitledBorder("Settings"));

	packetsSendPane = new JPanel();
	packetsSendPane.setBorder(BorderFactory.createTitledBorder("Packets send"));

	packetsReceivedBluePane = new JPanel();
	packetsReceivedBluePane.setBorder(BorderFactory.createTitledBorder("Blue flag"));

	packetsReceivedRedPane = new JPanel();
	packetsReceivedRedPane.setBorder(BorderFactory.createTitledBorder("Red flag"));

	packetsReceivedSwingPane = new JPanel();
	packetsReceivedSwingPane.setBorder(BorderFactory.createTitledBorder("Swing flag"));

	packetsReceivedContainer = new JPanel();
	packetsReceivedContainer.setBorder(BorderFactory.createTitledBorder("Packets received"));

	reportPane = new JPanel();
	reportPane.setBorder(BorderFactory.createTitledBorder("PDF Report"));

	bonusPane = new JPanel();
	bonusPane.setBorder(BorderFactory.createTitledBorder("Bonus / Penalty"));

	blueBonusPane = new JPanel();
	blueBonusPane.setBorder(BorderFactory.createTitledBorder("Blue team"));

	redBonusPane = new JPanel();
	redBonusPane.setBorder(BorderFactory.createTitledBorder("Red team"));

	bottomPane = new JPanel();

	/* Game times */
	gameTime = new JLabel(this.control.getRemainingRoundTime() + ":00");
	gameTime.setForeground(Color.GREEN);
	gameTime.setFont(ttfDigitalBig);

	respawnTime = new JLabel(this.control.getRemainingRespawnTime() + ":00");
	respawnTime.setForeground(Color.GREEN);
	respawnTime.setFont(ttfDigitalBig);

	countdownTime = new JLabel(this.control.getRemainingCountdownTime() + "");
	countdownTime.setForeground(Color.GREEN);
	countdownTime.setFont(ttfDigitalBig);

	/* Main team scores */
	blueScore = new JLabel("000:00");
	blueScore.setForeground(Color.BLUE);
	blueScore.setFont(ttfDigitalBig);

	redScore = new JLabel("000:00");
	redScore.setForeground(Color.RED);
	redScore.setFont(ttfDigitalBig);

	/* Packet loss */
	packetsSend = new JLabel("000");
	packetsSend.setForeground(Color.GREEN);
	packetsSend.setFont(ttfDigitalBig);

	packetsReceivedBlue = new JLabel("000");
	packetsReceivedBlue.setForeground(Color.BLUE);
	packetsReceivedBlue.setFont(ttfDigitalBig);

	packetsReceivedRed = new JLabel("000");
	packetsReceivedRed.setForeground(Color.RED);
	packetsReceivedRed.setFont(ttfDigitalBig);

	packetsReceivedSwing = new JLabel("000");
	packetsReceivedSwing.setForeground(Color.BLACK);
	packetsReceivedSwing.setFont(ttfDigitalBig);

	/* Spinners */
	gameTimeSpinner = new JSpinner(new SpinnerNumberModel(this.control.getRemainingRoundTime() / 60, 10, 90, 5));
	respawnTimeSpinner = new JSpinner(new SpinnerNumberModel(this.control.getRemainingRespawnTime() / 60, 1, 20, 1));
	blueBonusSpinner = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));
	redBonusSpinner = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));

	/* Per flag scores */
	/* blue flag */
	blueFlagScoreBlue = new JLabel("00:00");
	blueFlagScoreBlue.setForeground(Color.BLUE);
	blueFlagScoreBlue.setFont(ttfDigitalSmall);

	blueFlagScoreRed = new JLabel("00:00");
	blueFlagScoreRed.setForeground(Color.RED);
	blueFlagScoreRed.setFont(ttfDigitalSmall);

	/* Red flag */
	redFlagScoreBlue = new JLabel("00:00");
	redFlagScoreBlue.setForeground(Color.BLUE);
	redFlagScoreBlue.setFont(ttfDigitalSmall);

	redFlagScoreRed = new JLabel("00:00");
	redFlagScoreRed.setForeground(Color.RED);
	redFlagScoreRed.setFont(ttfDigitalSmall);

	/* swing flag */
	swingFlagScoreBlue = new JLabel("00:00");
	swingFlagScoreBlue.setForeground(Color.BLUE);
	swingFlagScoreBlue.setFont(ttfDigitalSmall);

	swingFlagScoreRed = new JLabel("00:00");
	swingFlagScoreRed.setForeground(Color.RED);
	swingFlagScoreRed.setFont(ttfDigitalSmall);

	flagAction = new JComboBox();
	flagAction.addItem("Blue");
	flagAction.addItem("Red");
	flagAction.addItem("Swing");

	/* Text fields */
	teamName = new JLabel("Name");
	blueTeamName = new JTextField("Blue team");
	blueTeamName.setColumns(10);
	redTeamName = new JTextField("Red team");
	redTeamName.setColumns(10);

	/* Buttons */
	startButton = new JButton("Start");
	startButton.setActionCommand("startGame");
	startButton.addActionListener(this);

	stopButton = new JButton("Stop");
	stopButton.setActionCommand("stopGame");
	stopButton.addActionListener(this);

	resetButton = new JButton("Reset");
	resetButton.setActionCommand("resetGame");
	resetButton.addActionListener(this);

	freezeButton = new JButton("Freeze");
	freezeButton.setActionCommand("freezeAction");
	freezeButton.addActionListener(this);

	reverseButton = new JButton("Reverse");
	reverseButton.setActionCommand("reverseAction");
	reverseButton.addActionListener(this);

	reportButton = new JButton("Generate");
	reportButton.setActionCommand("pdfReportAction");
	reportButton.addActionListener(this);

	/* Add the flag scores to the panes */
	blueFlagScorePane.add(blueFlagScoreBlue);
	blueFlagScorePane.add(blueFlagScoreRed);

	redFlagScorePane.add(redFlagScoreBlue);
	redFlagScorePane.add(redFlagScoreRed);

	swingFlagScorePane.add(swingFlagScoreBlue);
	swingFlagScorePane.add(swingFlagScoreRed);

	/* Add the content panes */
	flagScorePane.add(blueFlagScorePane);
	flagScorePane.add(redFlagScorePane);
	flagScorePane.add(swingFlagScorePane);

	/* The main scores */
	blueScorePane.add(blueScore);
	redScorePane.add(redScore);

	/* The game and respawn time */
	gameTimePane.add(gameTime);
	respawnTimePane.add(respawnTime);
	countdownTimePane.add(countdownTime);
	timePane.add(gameTimePane);
	timePane.add(respawnTimePane);
	timePane.add(countdownTimePane);

	/* Packet loss */
	packetsSendPane.add(packetsSend);
	packetsReceivedContainer.add(packetsReceivedBluePane);
	packetsReceivedContainer.add(packetsReceivedRedPane);
	packetsReceivedContainer.add(packetsReceivedSwingPane);
	packetsReceivedBluePane.add(packetsReceivedBlue);
	packetsReceivedRedPane.add(packetsReceivedRed);
	packetsReceivedSwingPane.add(packetsReceivedSwing);

	/* PDF Report */
	blueBonusPane.add(teamName);
	blueBonusPane.add(blueTeamName);
	blueBonusPane.add(new JLabel("Points"));
	blueBonusPane.add(blueBonusSpinner);

	redBonusPane.add(teamName);
	redBonusPane.add(redTeamName);
	redBonusPane.add(new JLabel("Points"));
	redBonusPane.add(redBonusSpinner);

	bonusPane.add(blueBonusPane);
	bonusPane.add(redBonusPane);
	reportPane.add(bonusPane, BorderLayout.NORTH);
	reportPane.add(new JLabel("When you press the generate button you will be asked to choose a directory where to save the report (PDF format)."), BorderLayout.CENTER);
	reportPane.add(reportButton, BorderLayout.SOUTH);

	/* The buttons */
	controlPane.add(startButton);
	controlPane.add(stopButton);
	controlPane.add(resetButton);

	/* Flag control */
	flagControlPane.add(flagAction);
	flagControlPane.add(freezeButton);
	flagControlPane.add(reverseButton);

	/* The spinners */
	settingsPane.add(new JLabel("Game"));
	settingsPane.add(gameTimeSpinner);
	settingsPane.add(new JLabel("Respawn"));
	settingsPane.add(respawnTimeSpinner);

	/* The bottom panel */
	bottomPane.add(controlPane);
	bottomPane.add(settingsPane);
	bottomPane.add(flagControlPane);

	mainTab = new JPanel();
	mainTab.setBackground(new Color(255,255,255));
	mainTab.setLayout(new BorderLayout());

	mainTab.add(timePane, BorderLayout.NORTH);
	mainTab.add(blueScorePane, BorderLayout.WEST);
	mainTab.add(redScorePane, BorderLayout.EAST);
	mainTab.add(flagScorePane, BorderLayout.CENTER);
	mainTab.add(bottomPane, BorderLayout.SOUTH);

	diagTab = new JPanel();
	diagTab.setBackground(new Color(255,255,255));
	diagTab.setLayout(new BorderLayout());
	diagTab.add(packetsSendPane, BorderLayout.NORTH);
	diagTab.add(packetsReceivedContainer, BorderLayout.CENTER);

	logTab = new JPanel();
	loglines = new JTextArea(30, 70);
	loglines.setEditable(false);
	loglinesScoller = new JScrollPane(loglines);
	logTab.add(loglinesScoller);

	reportTab = new JPanel();
	reportTab.setBackground(new Color(255,255,255));
	reportTab.setLayout(new BorderLayout());
	reportTab.add(reportPane);

	tabbedPane = new JTabbedPane();
	tabbedPane.add(mainTab, "Game control");
	tabbedPane.add(reportTab, "Game report");
	tabbedPane.add(diagTab, "Diagnostics");
	tabbedPane.add(logTab, "Log");

	this.add(tabbedPane);

    }

    public static void run(MainGUI applet, Control control) {

        applet.control = control;

        JFrame frame = new JFrame("PBControl");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(applet);
        frame.setSize(control.getWindowWidth(), control.getWindowHeigth());
        frame.setResizable(false);
        applet.init();
        applet.start();
        frame.setVisible(true);

        String[] logLinesBuffer = new String[255];

        while (true) {

            /* Tijden */
            applet.gameTime.setText(applet.secondsToHourMinuteText(control.getRemainingRoundTime()));

            applet.respawnTime.setText(((control.getRemainingRespawnTime() / 60) < 10 ? "0" : "") + (control.getRemainingRespawnTime() / 60) + ":" + ((control.getRemainingRespawnTime() % 60) < 10 ? "0" : "") + (control.getRemainingRespawnTime() % 60));

            applet.countdownTime.setText((control.getRemainingCountdownTime() < 10 ? "0" : "") + control.getRemainingCountdownTime());

            /* Scores */
            applet.blueScore.setText(applet.secondsToHourMinuteText(control.getBlueScore(), true));
            applet.redScore.setText(applet.secondsToHourMinuteText(control.getRedScore(), true));

            /* Blue base */
            applet.blueFlagScoreBlue.setText(applet.secondsToHourMinuteText(control.getBlueBaseScore()[1]));
            applet.blueFlagScoreRed.setText(applet.secondsToHourMinuteText(control.getBlueBaseScore()[2]));

	    if (control.getBlueBaseScore()[0] == 1) {
		applet.blueFlagScoreRed.setFont(applet.ttfDigitalSmall);
		applet.blueFlagScoreBlue.setFont(applet.ttfDigitalMedium);
	    } else if (control.getBlueBaseScore()[0] == 2) {
		applet.blueFlagScoreRed.setFont(applet.ttfDigitalMedium);
		applet.blueFlagScoreBlue.setFont(applet.ttfDigitalSmall);
	    } else {
		applet.blueFlagScoreRed.setFont(applet.ttfDigitalSmall);
		applet.blueFlagScoreBlue.setFont(applet.ttfDigitalSmall);
	    }

            /* Red base */
            applet.redFlagScoreBlue.setText(applet.secondsToHourMinuteText(control.getRedBaseScore()[1]));
            applet.redFlagScoreRed.setText(applet.secondsToHourMinuteText(control.getRedBaseScore()[2]));

	    if (control.getRedBaseScore()[0] == 1) {
		applet.redFlagScoreRed.setFont(applet.ttfDigitalSmall);
		applet.redFlagScoreBlue.setFont(applet.ttfDigitalMedium);
	    } else if (control.getRedBaseScore()[0] == 2) {
		applet.redFlagScoreRed.setFont(applet.ttfDigitalMedium);
		applet.redFlagScoreBlue.setFont(applet.ttfDigitalSmall);
	    } else {
		applet.redFlagScoreRed.setFont(applet.ttfDigitalSmall);
		applet.redFlagScoreBlue.setFont(applet.ttfDigitalSmall);
	    }

            /* Swing base */
            applet.swingFlagScoreBlue.setText(applet.secondsToHourMinuteText(control.getSwingBaseScore()[1]));
            applet.swingFlagScoreRed.setText(applet.secondsToHourMinuteText(control.getSwingBaseScore()[2]));

	    if (control.getSwingBaseScore()[0] == 1) {
		applet.swingFlagScoreRed.setFont(applet.ttfDigitalSmall);
		applet.swingFlagScoreBlue.setFont(applet.ttfDigitalMedium);
	    } else if (control.getSwingBaseScore()[0] == 2) {
		applet.swingFlagScoreRed.setFont(applet.ttfDigitalMedium);
		applet.swingFlagScoreBlue.setFont(applet.ttfDigitalSmall);
	    } else {
		applet.swingFlagScoreRed.setFont(applet.ttfDigitalSmall);
		applet.swingFlagScoreBlue.setFont(applet.ttfDigitalSmall);
	    }

            /* Log buffer */
            logLinesBuffer = Logger.getLines();
            for (int i = 0; i < logLinesBuffer.length; i++) {
                if (logLinesBuffer[i] == null) {
                    break;
                }
                applet.loglines.append(" " + logLinesBuffer[i] + "\n");
                applet.loglines.setCaretPosition(applet.loglines.getDocument().getLength());
            }
            Logger.clearLines();

            /* Packet loss */
            applet.packetsReceivedBlue.setText((control.getBlueBasePing() < 100 ? "0" : "") + (control.getBlueBasePing() < 10 ? "0" : "") + control.getBlueBasePing());
            applet.packetsReceivedRed.setText((control.getRedBasePing() < 100 ? "0" : "") + (control.getRedBasePing() < 10 ? "0" : "") + control.getRedBasePing());
            applet.packetsReceivedSwing.setText((control.getSwingBasePing() < 100 ? "0" : "") + (control.getSwingBasePing() < 10 ? "0" : "") + control.getSwingBasePing());
            applet.packetsSend.setText((control.getPingRequests() < 100 ? "0" : "") + (control.getPingRequests() < 10 ? "0" : "") + control.getPingRequests());

            applet.repaint();

            try {
                Thread.sleep(500);
            } catch (Exception e) {}

        }
    }

    public void actionPerformed(ActionEvent e) {
        if ("startGame".equals(e.getActionCommand())) {
            this.startGame();
        }

        if ("stopGame".equals(e.getActionCommand())) {
            this.stopGame();
        }

        if ("resetGame".equals(e.getActionCommand())) {
            this.roundReset();
        }

        /*
        Bij deze acties moeten we het vlagnummer sturen.
        De index begint echter bij 0 in de Comboxbox, daarom verhogen we de selectie index met 1
        Index 0 in de combobox is de blauwe vlag, terwijl die adres 0x01 heeft.
        */
        if ("freezeAction".equals(e.getActionCommand())) {
            this.control.freezeFlag(flagAction.getSelectedIndex()+1);
        }

        if ("reverseAction".equals(e.getActionCommand())) {
            this.control.reverseFlag(flagAction.getSelectedIndex()+1);
        }

        if ("pdfReportAction".equals(e.getActionCommand())) {
            final String DATE_FORMAT_NOW_UNDERSCORES = "dd-MM-yyyy_HH_mm";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW_UNDERSCORES);
            String filename = "pbreport_" + sdf.format(Calendar.getInstance().getTime()) + ".pdf";

            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setDialogTitle("Choose a directory to save the report");
            int returnVal = fc.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                PDFReport report = new PDFReport(file.getAbsolutePath() + "/" + filename);
                report.setRoundTime((Integer)this.gameTimeSpinner.getValue());
                report.setRespawnTime((Integer)this.respawnTimeSpinner.getValue());
                report.setBlueScore(this.control.getBlueScore());
                report.setRedScore(this.control.getRedScore());
                report.setBlueFlagScore(control.getBlueBaseScore());
                report.setRedFlagScore(control.getRedBaseScore());
                report.setSwingFlagScore(control.getSwingBaseScore());
                report.setBlueBonus((Integer)this.blueBonusSpinner.getValue());
                report.setRedBonus((Integer)this.redBonusSpinner.getValue());
                report.setBlueTeamName(this.blueTeamName.getText());
                report.setRedTeamName(this.redTeamName.getText());
                report.generateReport();
            }
        }

    }

    private void startGame() {
        if (!this.control.isActiveRound()) {
            this.resetSpinners();
            this.control.roundStart();
      }
    }

    private void stopGame() {
        this.control.roundEnd();
    }

    private void roundReset() {
        this.resetSpinners();
        this.control.roundReset();
    }

    private void resetSpinners() {
        int spinnerGameTime = (Integer)this.gameTimeSpinner.getValue();
        int spinnerRespawnTime = (Integer)this.respawnTimeSpinner.getValue();
        this.control.setRoundTime(spinnerGameTime * 60);
        this.control.setRespawnTime(spinnerRespawnTime * 60);
    }

    protected String secondsToHourMinuteText(int time) {
        return this.secondsToHourMinuteText(time, false);
    }

    protected String secondsToHourMinuteText(int time, Boolean trailingzero) {
        int minutes = time / 60;
        int seconds = time % 60;
        if (!trailingzero) {
            return ((minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds);
        } else {
            return ((minutes < 100 ? "0" : "") + (minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds);
        }
    }

}