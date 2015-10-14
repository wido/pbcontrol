package Game;

import SerialPort.Port;

public class Game extends Thread {

    private int roundtime;
    private int respawntime;
    private int countdowntime;
    private int remainingRoundTime;
    private int remainingRespawnTime;
    private int remainingCountdownTime;
    private Score score;
    private Time time;
    private Port port;
    public boolean gameActive = false;

    public Game(int roundtime, int respawntime, int countdowntime, Port port) {

        this.roundtime = roundtime;
        this.respawntime = respawntime;
        this.countdowntime = countdowntime;
        this.remainingRoundTime = this.roundtime;

        this.time = new Time(this.roundtime, this.respawntime, this.countdowntime);
        this.score = new Score();
        this.port = port;

    }

    /* De thread methode die de hele game draait */
    public void run() {

        int[][] scores;
        int pollBlocker = 0;

        /* We starten de game ook, de vlaggen moeten dit weten */
        this.port.roundStart();
        this.port.setScoreObject(this.score);

        /* We starten de timers om af te tellen, dit gaat in een aparte thread */
        this.time.start();

        /* De game timers moeten iets voor lopen */
        try {
            this.sleep(10);
        } catch (Exception e) {}

        /* Zolang de game bezig is draait deze while */
        while (this.gameActive) {

            remainingCountdownTime = time.getRemainingCountdownTime();
            remainingRoundTime = time.getRemainingRoundTime();
            remainingRespawnTime = time.getRemainingRespawnTime();

            /* Zodra de tijd om is, stoppen we de endless loop */
            if (remainingRoundTime == 0) {
                this.gameActive = false;
            } else {
                /* Hier bepalen we hoe veel we slapen tussen elke update */
                try {
                    this.sleep(800);
                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {}
            }

            /* Pas als de countdown voorbij is gaan we vlaggen pollen */
            if (remainingCountdownTime == 0) {
                /*
                Poll de vlaggen voor hun data
                Hierbij moeten we voorkomen dat we de vlaggen overspoelen met data,
                daarom niet elke 800ms pollen
                 */
                if (pollBlocker == 0) {
                    this.port.poll();
                    this.score.pingRequest();
                    pollBlocker = 3;
                }
                pollBlocker--;
            }
        }

        /*
            We stoppen de timers, halen nog ����n maal de vlaggen scores
            op en stoppen daarna de vlaggen
         */
        this.time.end();
        this.port.poll();
        this.port.roundEnd();
    }

    /* Externe threads zoals de GUI moeten de score ook op kunnen vragen */
    public synchronized Score getScore() {
        return this.score;
    }

    /*
    Er kan een timeReverse worden ingezet door een team, de GUI geeft dat hier door
    0: blue base
    1: red base
    2: swing base
     */
    public void reverseFlag(int flag) {
        this.port.reverseFlag(flag);
    }

    public void freezeFlag(int flag) {
        this.port.freezeFlag(flag);
    }

    /*
    Reset alle vlaggen naar 0 toe, dit gebeurd ook al als de game start.
    Het kan wenselijk zijn dit tussendoor te doen, zodat er visueel kan worden gecontroleerd of alles werkt
     */
    public void reset() {
        this.port.reset();
        this.port.setGameSettings(this.roundtime, this.respawntime, this.countdowntime);
    }

    public void roundEnd() {
        this.gameActive = false;
    }

    public void roundStart() {
        this.gameActive = true;
        this.start();
    }

    public int getRemainingRoundTime() {
        return this.time.getRemainingRoundTime();
    }

    public int getRemainingRespawnTime() {
        return this.time.getRemainingRespawnTime();
    }

    public int getRemainingCountdownTime() {
        return this.time.getRemainingCountdownTime();
    }

}
