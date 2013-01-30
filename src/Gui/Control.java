package Gui;

import Game.*;
import SerialPort.Port;

public class Control {

    private Port port;
    private Settings settings;
    private Game game = null;
    private Score score;

    private int remainingRoundTime;
    private int remainingRespawnTime;
    private int remainingCountdownTime;

    private int roundTime;
    private int respawnTime;
    private int countdownTime;

    private int blueScore = 0;
    private int redScore = 0;

    private int[] blueBaseScore = new int[] {0,0,0};
    private int[] redBaseScore = new int[] {0,0,0};
    private int[] swingBaseScore = new int[] {0,0,0};

    public Control(Settings settings, Port serialPort) {
        this.port = serialPort;
        this.port.open();
        this.settings = settings;
        this.resetScores();
    }

    private void resetScores() {
        this.roundTime = this.remainingRoundTime = this.settings.getRoundTime();
        this.respawnTime = this.remainingRespawnTime = this.settings.getRespawnTime();
        this.countdownTime = this.remainingCountdownTime = this.settings.getCountdownTime();
        this.blueScore = 0;
        this.redScore = 0;
        this.blueBaseScore = new int[] {0,0,0};
        this.redBaseScore = new int[] {0,0,0};
        this.swingBaseScore = new int[] {0,0,0};
    }

    protected boolean isActiveRound() {
        if (this.game != null) {
            if (!this.game.gameActive) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /* Round control */
    protected void roundStart() {
        if (!this.isActiveRound()) {
            this.game = new Game(this.roundTime, this.respawnTime, this.countdownTime, this.port);
            this.game.roundStart();
            this.score = this.game.getScore();
        }
    }

    protected void roundEnd() {
        if (this.isActiveRound()) {
            this.game.roundEnd();
            this.game = null;
        }
    }

    protected void roundReset() {
        if (!this.isActiveRound()) {
            this.resetScores();
            this.game = new Game(this.roundTime, this.respawnTime, this.countdownTime, this.port);
            this.game.reset();
            this.game = null;
        }
    }

    protected void freezeFlag(int flag) {
        if (this.isActiveRound()) {
            this.game.freezeFlag(flag);
        }
    }

    protected void reverseFlag(int flag) {
        if (this.isActiveRound()) {
            this.game.reverseFlag(flag);
        }
    }

    /* Tijden */
    protected void setRoundTime(int roundTime) {
        this.roundTime = this.remainingRoundTime = roundTime;
        this.settings.setRoundTime(roundTime / 60);
        this.settings.save();
    }

    protected void setRespawnTime(int respawnTime) {
        this.respawnTime = this.remainingRespawnTime = respawnTime;
        this.settings.setRespawnTime(respawnTime / 60);
        this.settings.save();
    }

    protected int getRemainingRoundTime() {
        if (this.isActiveRound()) {
            this.remainingRoundTime = this.game.getRemainingRoundTime();
        }
        return this.remainingRoundTime;
    }

    protected int getRemainingRespawnTime() {
        if (this.isActiveRound()) {
            this.remainingRespawnTime = this.game.getRemainingRespawnTime();
        }
        return this.remainingRespawnTime;
    }

    protected int getRemainingCountdownTime() {
        if (this.isActiveRound()) {
            this.remainingCountdownTime = this.game.getRemainingCountdownTime();
        }
        return this.remainingCountdownTime;
    }

    /* Scores */
    public int getBlueScore() {
        if (this.isActiveRound()) {
            this.blueScore = this.score.getBlueScore();
        }
        return this.blueScore;
    }

    public int getRedScore() {
        if (this.isActiveRound()) {
            this.redScore = this.score.getRedScore();
        }
        return this.redScore;
    }

    public int[] getBlueBaseScore() {
        if (this.isActiveRound()) {
            this.blueBaseScore = this.score.getBlueBaseScore();
        }
        return this.blueBaseScore;
    }

    public int[] getRedBaseScore() {
        if (this.isActiveRound()) {
            this.redBaseScore = this.score.getRedBaseScore();
        }
        return this.redBaseScore;
    }

    public int[] getSwingBaseScore() {
        if (this.isActiveRound()) {
            this.swingBaseScore = this.score.getSwingBaseScore();
        }
        return this.swingBaseScore;
    }

    public int getBlueBasePing() {
        if (this.isActiveRound()) {
            return this.score.getBlueBasePing();
        }
        return 0;
    }

    public int getRedBasePing() {
        if (this.isActiveRound()) {
            return this.score.getRedBasePing();
        }
        return 0;
    }

    public int getSwingBasePing() {
        if (this.isActiveRound()) {
            return this.score.getSwingBasePing();
        }
        return 0;
    }

    public int getPingRequests() {
        if (this.isActiveRound()) {
            return this.score.getPingRequests();
        }
        return 0;
    }

    /* Window sizes */
    public int getWindowWidth() {
        return this.settings.getWindowWidth();
    }

    public int getWindowHeigth() {
        return this.settings.getWindowHeigth();
    }

}