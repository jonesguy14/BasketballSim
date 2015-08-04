/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.basketballsim;

import java.util.ArrayList;

/**
 *
 * @author Achi Jones
 */
public class Team {
    public Player[] playersArray;
    int wins;
    int losses;
    int games;
    int pointsFor;
    int pointsAga;
    String name;
    int[] startersIn;
    int[] benchIn;
    
    public Team( String name, Player[] players ) {
        playersArray = players;
        this.name = name;
        wins = 0;
        losses = 0;
        games = 0;
        startersIn = new int[5];
        benchIn = new int[5];
        for (int i = 0; i < 5; ++i) {
            startersIn[i] = 1;
            benchIn[i] = 0;
        }
    }
    
    public Team( String name ) {
        this.name = name;
        wins = 0;
        losses = 0;
        games = 0;
        playersArray = new Player[10];
        startersIn = new int[5];
        benchIn = new int[5];
        for (int i = 0; i < 5; ++i) {
            startersIn[i] = 1;
            benchIn[i] = 0;
        }
    }
    
    public int getWins82() {
        return (int)( wins * (float)82/games );
    }
    
    public int getLosses82() {
        return 82 - getWins82();
    }
    
    public void addPlayer( Player player ) {
        //add player (used by AI)
        if ( playersArray[ player.ratingsArray[0] - 1 ] == null ) {
            // no starter yet
            playersArray[ player.ratingsArray[0] - 1 ] = player;
        } else {
            // put in bench
            playersArray[ player.ratingsArray[0] - 1 + 5 ] = player;
        }

    }
    
    public Player getPG() {
        return playersArray[0];
    }
    
    public Player getSG() {
        return playersArray[1];
    }
    
    public Player getSF() {
        return playersArray[2];
    }
    
    public Player getPF() {
        return playersArray[3];
    }
    
    public Player getC() {
        return playersArray[4];
    }
    
    //getters for team stats per game
    public double getPPG() {
        double res = 0.0;
        for (int p = 0; p < 10; p++) {
            res += playersArray[p].getPPG();
        }
        return res;
    }
    public double getFGAPG() {
        double res = 0.0;
        for (int p = 0; p < 10; p++) {
            res += playersArray[p].getFGAPG();
        }
        return (double)((int)(res*10))/10;
    }
    public double get3GAPG() {
        double res = 0.0;
        for (int p = 0; p < 10; p++) {
            res += playersArray[p].get3GAPG();
        }
        return (double)((int)(res*10))/10;
    }
    public double getFGP() {
        double res = 0.0;
        for (int p = 0; p < 10; p++) {
            res += playersArray[p].getFGP() * (playersArray[p].getFGAPG() / getFGAPG());
        }
        return (double)((int)(res * 10))/10;
    }
    public double get3GP() {
        double res = 0.0;
        for (int p = 0; p < 10; p++) {
            res += playersArray[p].get3GP() * (playersArray[p].get3GAPG() / get3GAPG());
        }
        return (double)((int)(res * 10))/10;
    }
    public double getRPG() {
        double res = 0.0;
        for (int p = 0; p < 10; p++) {
            res += playersArray[p].getRPG();
        }
        return (double)((int)(res*10))/10;
    }
    public double getAPG() {
        double res = 0.0;
        for (int p = 0; p < 10; p++) {
            res += playersArray[p].getAPG();
        }
        return (double)((int)(res*10))/10;
    }
    public double getSPG() {
        double res = 0.0;
        for (int p = 0; p < 10; p++) {
            res += playersArray[p].getSPG();
        }
        return (double)((int)(res*10))/10;
    }
    public double getBPG() {
        double res = 0.0;
        for (int p = 0; p < 10; p++) {
            res += playersArray[p].getBPG();
        }
        return (double)((int)(res*10))/10;
    }
    public double getOFP() {
        int tot_ofa = 0;
        int tot_ofm = 0;
        for (int p = 0; p < 10; p++) {
            tot_ofa += playersArray[p].stats_tot[9];
            tot_ofm += playersArray[p].stats_tot[10];
        }
        System.out.println(name + " " + tot_ofm + "/" + tot_ofa);
        return (double)((int)((float)tot_ofm/tot_ofa * 1000))/10;
    }
    
    public double getPointDiff() {
        double pd = (double)(pointsFor - pointsAga)/games;
        return (double)((int)(pd*10))/10;
    }
    
    public String getPDStr() {
        double pd =  getPointDiff();
        if ( pd >= 0 ) {
            return "+" + pd;
        } else {
            return "" + pd;
        }
    }
    
    public void subPlayers( double time ) {
        // sub players based on game time
        time = time/60;
        //PG
        if ( startersIn[0] == 1 && benchIn[0] == 0 && time >= (double)getPG().getPlayingTime()/2 && time < 47 - (double)getPG().getPlayingTime()/2 ) {
            //sub out starting PG
            Player tmp = playersArray[0];
            playersArray[0] = playersArray[5];
            playersArray[5] = tmp;
            startersIn[0] = 0;
            benchIn[0] = 1;
            if ( "PLAYER TEAM".equals(name) && games == 0) System.out.println("Subbed out " + playersArray[5].name + " for " + getPG().name + " at time " + time);
        } else if ( startersIn[0] == 0 && benchIn[0] == 1 && time >= 48 - (double)getPG().getPlayingTime()/2 ) {
            //sub in starting PG
            Player tmp = playersArray[0];
            playersArray[0] = playersArray[5];
            playersArray[5] = tmp;
            startersIn[0] = 1;
            benchIn[0] = 0;
            if ( "PLAYER TEAM".equals(name) && games == 0) System.out.println("Subbed out " + playersArray[5].name + " for " + getPG().name + " at time " + time);
        }
        //SG
        if ( startersIn[1] == 1 && benchIn[1] == 0 && time >= (double)getSG().getPlayingTime()/2 && time < 47 - (double)getSG().getPlayingTime()/2 ) {
            //sub out starting SG
            Player tmp = playersArray[1];
            playersArray[1] = playersArray[6];
            playersArray[6] = tmp;
            startersIn[1] = 0;
            benchIn[1] = 1;
        } else if ( startersIn[1] == 0 && benchIn[1] == 1 && time >= 48 - (double)getSG().getPlayingTime()/2 ) {
            //sub in starting SG
            Player tmp = playersArray[1];
            playersArray[1] = playersArray[6];
            playersArray[6] = tmp;
            startersIn[1] = 1;
            benchIn[1] = 0;
        }
        //SF
        if ( startersIn[2] == 1 && benchIn[2] == 0 && time >= (double)getSF().getPlayingTime()/2 && time < 47 - (double)getSF().getPlayingTime()/2 ) {
            //sub out starting SF
            Player tmp = playersArray[2];
            playersArray[2] = playersArray[7];
            playersArray[7] = tmp;
            startersIn[2] = 0;
            benchIn[2] = 1;
        } else if ( startersIn[2] == 0 && benchIn[2] == 1 && time >= 48 - (double)getSF().getPlayingTime()/2 ) {
            //sub in starting SF
            Player tmp = playersArray[2];
            playersArray[2] = playersArray[7];
            playersArray[7] = tmp;
            startersIn[2] = 1;
            benchIn[2] = 0;
        }
        //PF
        if ( startersIn[3] == 1 && benchIn[3] == 0 && time >= (double)getPF().getPlayingTime()/2 && time < 47 - (double)getPF().getPlayingTime()/2 ) {
            //sub out starting PF
            Player tmp = playersArray[3];
            playersArray[3] = playersArray[8];
            playersArray[8] = tmp;
            startersIn[3] = 0;
            benchIn[3] = 1;
        } else if ( startersIn[3] == 0 && benchIn[3] == 1 && time >= 48 - (double)getPF().getPlayingTime()/2 ) {
            //sub in starting PF
            Player tmp = playersArray[3];
            playersArray[3] = playersArray[8];
            playersArray[8] = tmp;
            startersIn[3] = 1;
            benchIn[3] = 0;
        }
        //C
        if ( startersIn[4] == 1 && benchIn[4] == 0 && time >= (double)getC().getPlayingTime()/2 && time < 47 - (double)getC().getPlayingTime()/2 ) {
            //sub out starting C
            Player tmp = playersArray[4];
            playersArray[4] = playersArray[9];
            playersArray[9] = tmp;
            startersIn[4] = 0;
            benchIn[4] = 1;
        } else if ( startersIn[4] == 0 && benchIn[4] == 1 && time >= 48 - (double)getPF().getPlayingTime()/2 ) {
            //sub in starting C
            Player tmp = playersArray[4];
            playersArray[4] = playersArray[9];
            playersArray[9] = tmp;
            startersIn[4] = 1;
            benchIn[4] = 0;
        }
    }
    
    public void selectPlayer(ArrayList<Player> players) {
        //assumes arraylist is sorted by overall
        for (int p = 0; p < players.size(); ++p) {
            if ( playersArray[0] != null && playersArray[1] != null && playersArray[2] != null && playersArray[3] != null && playersArray[4] != null ) {
                // starters all selected, need bench
                if ( playersArray[ players.get(p).ratingsArray[0] - 1 + 5 ] == null ) {
                    //dont have bench guy in this position yet
                    Player selectedPlayer = players.get(p);
                    addPlayer(selectedPlayer);
                    players.remove(selectedPlayer);
                    System.out.println(name + " selected " + selectedPlayer.name + " for bench.");
                    return;
                }
            } else {
                if ( playersArray[ players.get(p).ratingsArray[0] - 1 ] == null ) {
                    //dont have starter in this position yet
                    Player selectedPlayer = players.get(p);
                    addPlayer(selectedPlayer);
                    players.remove(selectedPlayer);
                    System.out.println(name + " selected " + selectedPlayer.name);
                    return;
                }
            }
        }
        System.out.println(name + " DIDNT PICK ENOUGH PEOPLE!");
    }
    
    
    
    
}
