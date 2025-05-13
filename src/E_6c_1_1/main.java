package E_6c_1_1;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class main {

    private static String prefix = "";

    public static void printOrdner(File file) {
        //recursive
        if(!file.isDirectory()) {
            System.out.println(getFullFile(file));
            return;
        }
        File[] files = file.listFiles();
        if(files == null) {
            return;
        }
        for(File f : files) {
            if(f.isDirectory()) {
                System.out.println(prefix + "+" + f.getName());
                String oldPrefix = prefix;
                prefix += "\t";
                printOrdner(f);
                prefix = oldPrefix;
            } else {
                System.out.println(prefix + getFullFile(f));
            }
        }
    }

    private static String getFullFile(File file) {
        StringBuilder sb = new StringBuilder();
        sb.append(file.getName());
        sb.append(" - ");
        sb.append(new SimpleDateFormat("dd.MM.yyyy").format(new Date(file.lastModified())));
        sb.append(" - ");
        sb.append(file.length());
        sb.append(" Byte");
        return sb.toString();
    }

    public static void main(String[] args) {
        String path = "src/";
        File file = new File(path);
        printOrdner(file);
    }
}
