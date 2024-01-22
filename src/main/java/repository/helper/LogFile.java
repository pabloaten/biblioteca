package repository.helper;

import views.helper.HistoricoHelper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Para registrar todas las operaciones realizadas en la base de datos
 * en ficheros logs locales
 * @author AGE
 * @version 2
 */
public class LogFile {
    private static String file="ficheros/historial"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("_yyyyMMdd"))+".log";

    public static void createDirectory(){
        File directorio = new File("ficheros");
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
    }

    /**
     * Graba en el fichero log para el día actual el mensaje recibido
     * el mensaje tambien es grabado en la tabla historico de la BD
     * @param msgLog texto a guarda en el fichero log
     * @throws IOException en el caso de que no pueda accederse adecuadamente al fichero
     */
    public static void saveLOG(String msgLog) throws Exception {
        createDirectory();
        saveLOGsinBD(msgLog);
        HistoricoHelper.guardarMensaje(msgLog);
    }
    /**
     * Graba en el fichero log para el día actual el mensaje recibido
     * @param msgLog texto a guarda en el fichero log
     * @throws IOException en el caso de que no pueda accederse adecuadamente al fichero
     */
    public static void saveLOGsinBD(String msgLog) throws IOException {
        Path path = Paths.get(file);
        if (path.toFile().exists())
            Files.writeString(path,msgLog+"\n",StandardCharsets.UTF_8,StandardOpenOption.APPEND);
        else Files.writeString(path,msgLog+"\n",StandardCharsets.UTF_8,StandardOpenOption.CREATE);
    }
}
