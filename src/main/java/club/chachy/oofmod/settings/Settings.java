package club.chachy.oofmod.settings;

import cc.hyperium.config.ConfigOpt;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Settings {
    public static Settings INSTANCE = new Settings();
    private File soundsFolder = new File("config/oofmod/sounds");
    @ConfigOpt
    private String selectedSoundName = "oof";
    @ConfigOpt
    private File selectedSound = new File(soundsFolder.getPath() + "/" + selectedSoundName + ".wav");
    @ConfigOpt
    private float volume = 1.0f;
    @ConfigOpt
    private boolean enabled = true;

    public Settings() {
        if (!soundsFolder.exists()) {
            soundsFolder.mkdirs();
        }
        downloadDefaultSound();
    }

    public ArrayList<File> getSounds() {
        ArrayList<File> result = new ArrayList<>();
        for (File file : soundsFolder.listFiles()) {
            if (file.getName().endsWith(".wav")) {
                result.add(file);
            }
        }
        return result;
    }

    public void setSelectedSoundName(String soundName) {
        selectedSoundName = soundName;
        selectedSound = new File(soundsFolder.getPath() + "/" + selectedSoundName);
    }


    public File getSelectedSound() {
        return this.selectedSound;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public float getVolume() {
        return this.volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public String getSelectedSoundName() {
        return selectedSoundName;
    }

    private void downloadDefaultSound() {
        File defaultSound = new File("config/oofmod/sounds/oof.wav");
        if (defaultSound.exists()) {
            return;
        }
        URL url;
        URLConnection con;
        DataInputStream dis;
        FileOutputStream fos;
        byte[] fileData;
        try {
            url = new URL("https://oofmodsound.powns.dev/oof.wav"); //File Location goes here
            con = url.openConnection(); // open the url connection.
            dis = new DataInputStream(con.getInputStream());
            fileData = new byte[con.getContentLength()];
            for (int q = 0; q < fileData.length; q++) {
                fileData[q] = dis.readByte();
            }
            dis.close(); // close the data input stream
            fos = new FileOutputStream(new File(soundsFolder.getPath() + "/oof.wav")); //FILE Save Location goes here
            fos.write(fileData);  // write out the file we want to save.
            fos.close(); // close the output stream writer
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
