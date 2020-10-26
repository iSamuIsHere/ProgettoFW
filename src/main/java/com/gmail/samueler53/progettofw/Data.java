package com.gmail.samueler53.progettofw;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Data implements Serializable {
    private static transient final long serialVersionUID = 1681012206529286330L;
    private final HashMap<UUID,Boolean> previouslyPlayersCurse;
    private int restart = 0;

    //getter cursedplayers
    public HashMap<UUID, Boolean> getPreviouslyPlayersCurse() {
        return previouslyPlayersCurse;
    }

    //getter restart
    public int getRestart() {
        return restart;
    }


    //saving
    public Data(HashMap<UUID,Boolean> previouslyOnlinePlayers,int restart) {
        this.previouslyPlayersCurse = previouslyOnlinePlayers;
        this.restart = restart + 1;
    }

    //loading
    public Data(Data loadedData) {
        this.previouslyPlayersCurse = loadedData.previouslyPlayersCurse;
        this.restart = loadedData.restart;
    }



    public boolean saveData(String filePath) {
        try {
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(filePath)));
            out.writeObject(this);
            out.close();
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public static Data loadData(String filePath) {
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(filePath)));
            Data data = (Data) in.readObject();
            in.close();
            return data;
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


}
