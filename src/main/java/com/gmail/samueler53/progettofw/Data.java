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
    private final HashMap<UUID, Boolean> previouslyPlayersCurse = new HashMap<>();
    private int restart = 0;
    private transient String fileName;

    public Data(String fileName) {
        this.fileName = fileName;
    }

    public HashMap<UUID, Boolean> getPreviouslyPlayersCurse() {
        return previouslyPlayersCurse;
    }

    public void setPlayerCurse(UUID uuid, boolean flag) {
        getPreviouslyPlayersCurse().put(uuid, flag);
    }

    public int getRestart() {
        return restart;
    }

    public boolean saveData() {
        try {
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(fileName)));
            out.writeObject(this);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Data loadData(String filePath) {
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(filePath)));
            Data data = (Data) in.readObject();
            in.close();
            data.setFileName(filePath);
            data.restart++;
            return data;
        } catch (ClassNotFoundException | IOException e) {
            return new Data(filePath);
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
