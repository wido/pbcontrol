package Game;

public class Score {

    private int[] blueBaseScore = new int[] {0,0,0};
    private int[] redBaseScore = new int[] {0,0,0};
    private int[] swingBaseScore = new int[] {0,0,0};

    private int blueBasePing = 0;
    private int redBasePing = 0;
    private int swingBasePing = 0;

    private int pingRequests = 0;

    public Score() { }

    /*
      Hier zetten we de scores van de 3 masten.

      De scores worden in een Array (int) opgeslagen met een lengte van 3:
      0: de kleur van de vlag, 0 is neutraal, 1 is blauw en 2 is rood
      1: de blauwe score
      2: de rode score

     */
    public synchronized void setBlueBaseScore(int color, int blue, int red) {
        this.blueBaseScore[0] = color;
        if (blue >= this.blueBaseScore[1]) {
            this.blueBaseScore[1] = blue;
        }
        if (red >= this.blueBaseScore[2]) {
            this.blueBaseScore[2] = red;
        }
        this.blueBasePing++;
    }

    public synchronized void setRedBaseScore(int color, int blue, int red) {
        this.redBaseScore[0] = color;
        if (blue >= this.redBaseScore[1]) {
            this.redBaseScore[1] = blue;
        }
        if (red >= this.redBaseScore[2]) {
            this.redBaseScore[2] = red;
        }
        this.redBasePing++;
    }

    public synchronized void setSwingBaseScore(int color, int blue, int red) {
        this.swingBaseScore[0] = color;
        if (blue >= this.swingBaseScore[1]) {
            this.swingBaseScore[1] = blue;
        }
        if (red >= this.swingBaseScore[2]) {
            this.swingBaseScore[2] = red;
        }
        this.swingBasePing++;
    }

    /* Getters */
    public synchronized int getBlueScore() {
        return (this.redBaseScore[1] + this.blueBaseScore[1] + this.swingBaseScore[1]);
    }

    public synchronized int getRedScore() {
        return (this.redBaseScore[2] + this.blueBaseScore[2] + this.swingBaseScore[2]);
    }

    public synchronized int[] getBlueBaseScore() {
        return this.blueBaseScore;
    }

    public synchronized int[] getRedBaseScore() {
        return this.redBaseScore;
    }

    public synchronized int[] getSwingBaseScore() {
        return this.swingBaseScore;
    }

    public synchronized int getBlueBasePing() {
        return this.blueBasePing;
    }

    public synchronized int getRedBasePing() {
        return this.redBasePing;
    }

    public synchronized int getSwingBasePing() {
        return this.swingBasePing;
    }

    public synchronized int getPingRequests() {
        return this.pingRequests;
    }

    public synchronized void pingRequest() {
        this.pingRequests++;
    }

}