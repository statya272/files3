import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        String zipAddress = "D:\\Games\\savegames\\zip_save.zip";
        String saveDir = "D:\\Games\\savegames";
        String saveFile = "D:\\Games\\savegames\\save1.dat";

        openZip(zipAddress, saveDir);

        System.out.println(openProgress(saveFile));
    }

    public static void openZip(String zipAddress, String unzipDir) {
        try (FileInputStream fis = new FileInputStream(zipAddress);
             ZipInputStream zis = new ZipInputStream(fis)) {
            ZipEntry entry;
            String name;

            while ((entry = zis.getNextEntry()) != null) {
                name = unzipDir + "\\" + entry.getName();
                FileOutputStream fos = new FileOutputStream(name);
                for (int c = zis.read(); c != -1; c = zis.read()) {
                    fos.write(c);
                }
                fos.flush();
                zis.closeEntry();
                fos.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static GameProgress openProgress(String save) {
        try (FileInputStream fis = new FileInputStream(save);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (GameProgress) ois.readObject();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}