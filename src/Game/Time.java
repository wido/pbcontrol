package Game;

public class Time extends Thread {

    private int roundTime;
    private int respawnTime;
    private int countdownTime;
    private int initialRespawnTime;
    private Boolean timerActive = true;

    public Time(int roundTime, int respawnTime, int countdownTime) {
        this.roundTime = roundTime;
        this.respawnTime = respawnTime;
        this.initialRespawnTime = respawnTime;
        this.countdownTime = countdownTime;
    }

    public synchronized int getRemainingRoundTime() {
        return this.roundTime;
    }

    public synchronized int getRemainingRespawnTime() {
        return this.respawnTime;
    }

    public synchronized int getRemainingCountdownTime() {
        return this.countdownTime;
    }

    public void run() {

        while ((this.roundTime > 0) & (this.timerActive == true)) {

            if (this.countdownTime > 0) {
                this.countdownTime--;
            }

            try {
                this.sleep(1000);
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {}

            if (this.countdownTime == 0) {
                this.roundTime--;
                /* Het kan zijn dat er niet een gelijk aantal respawns in een game past,
                   bijv 30 minuten game met 7 minuten respawn.
                   We houden daar rekening mee en laten de respawntijd niet in het negatieve lopen
                 */
                if (this.respawnTime > 0) {
                    this.respawnTime--;
                } else if ((this.respawnTime == 0) & (this.roundTime > this.initialRespawnTime)) {
                    this.respawnTime = (this.initialRespawnTime - 1);
                }
            }
        }
    }

    public void end() {
        this.timerActive = false;
    }

}