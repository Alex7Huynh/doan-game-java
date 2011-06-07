/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

/**
 *
 * @author TienPhan
 */
import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class Sound {

   public Sound() {
   }
/**
 * Hàm dùng để play một file wav cho game
 * @param fileNameSound
 */
   public void playFileSound(String fileNameSound) {
      int EXTERNAL_BUFFER_SIZE = 524288;
      File soundFile = new File(fileNameSound);
      if (!soundFile.exists()) {
         return;
      }
      AudioInputStream audioSoundInputStream = null;
      try {
         audioSoundInputStream = AudioSystem.getAudioInputStream(soundFile);
      } catch (Exception e) {
         e.printStackTrace();
         return;
      }
      AudioFormat formatAudio = audioSoundInputStream.getFormat();
      SourceDataLine datalineSourceSound = null;
      DataLine.Info infoSound = new DataLine.Info(SourceDataLine.class, formatAudio);
      try {
         datalineSourceSound = (SourceDataLine) AudioSystem.getLine(infoSound);
         datalineSourceSound.open(formatAudio);
      } catch (Exception e) {
         e.printStackTrace();
         return;
      }
      datalineSourceSound.start();
      int nBytesRead = 0;
      byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
      try {
         while (nBytesRead != -1) {
            nBytesRead = audioSoundInputStream.read(abData, 0, abData.length);
            if (nBytesRead >= 0) {
               datalineSourceSound.write(abData, 0, nBytesRead);
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
         return;
      } finally {
         datalineSourceSound.drain();
         datalineSourceSound.close();
      }
   }
}
