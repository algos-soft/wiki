package it.algos.vaad23.backend.service;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.server.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.exception.*;
import static it.algos.vaad23.backend.service.FileService.*;
import it.algos.vaad23.backend.wrapper.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 24-set-2020
 * Time: 21:06
 * <p>
 * Static files: {project directory}/src/main/resources/META-INF/resources/
 * CSS files e JavaScript: {project directory}/frontend/styles/
 * Risorse esterne al JAR: {project directory}/config/
 *
 * @see(https://vaadin.com/docs/flow/importing-dependencies/tutorial-ways-of-importing.html)
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ResourceService extends AbstractService {


    /**
     * versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Legge un file di risorse da {project directory}/config/ <br>
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     *
     * @return testo completo grezzo del file
     */
    public String leggeConfig(final String simpleNameFileToBeRead) {
        String tag = "config";
        return fileService.leggeFile(tag + File.separator + simpleNameFileToBeRead);
    }


    /**
     * Legge una lista di righe di risorse da {project directory}/config/ <br>
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     *
     * @return lista di righe grezze
     */
    public List<String> leggeListaConfig(final String simpleNameFileToBeRead) {
        return leggeListaConfig(simpleNameFileToBeRead, true);
    }


    /**
     * Legge una lista di righe di risorse da {project directory}/config/ <br>
     * La prima riga contiene i titoli
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     * @param compresiTitoli         parte dalla prima riga, altrimenti dalla seconda
     *
     * @return lista di righe grezze
     */
    public List<String> leggeListaConfig(final String simpleNameFileToBeRead, final boolean compresiTitoli) {
        String rawText = leggeConfig(simpleNameFileToBeRead);
        return leggeLista(rawText, compresiTitoli);
    }

    /**
     * Legge una lista di righe di risorse <br>
     * La prima riga contiene i titoli
     *
     * @param rawText        testo grezzo del file
     * @param compresiTitoli parte dalla prima riga, altrimenti dalla seconda
     *
     * @return lista di righe grezze
     */
    private List<String> leggeLista(final String rawText, final boolean compresiTitoli) {
        List<String> listaRighe = null;
        String[] righe;

        if (textService.isValid(rawText)) {
            listaRighe = new ArrayList<>();
            righe = rawText.split(CAPO);
            if (righe != null && righe.length > 0) {
                for (String riga : righe) {
                    riga = textService.estrae(riga, DOPPIE_GRAFFE_INI, DOPPIE_GRAFFE_END);
                    if (textService.isValid(riga)) {
                        listaRighe.add(riga);
                    }
                }
            }
        }

        if (listaRighe != null) {
            if (!compresiTitoli) {
                listaRighe = listaRighe.subList(1, listaRighe.size());
            }
        }

        return listaRighe;
    }


    /**
     * Legge una mappa di risorse da {project directory}/config/ <br>
     * La prima riga contiene i titoli
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     *
     * @return mappa dei titoli più le righe grezze
     */
    public Map<String, List<String>> leggeMappaConfig(final String simpleNameFileToBeRead) {
        return leggeMappaConfig(simpleNameFileToBeRead, true);
    }

    /**
     * Legge una mappa di risorse da {project directory}/config/ <br>
     * La mappa NON contiene i titoli
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     * @param compresiTitoli         parte dalla prima riga, altrimenti dalla seconda
     *
     * @return mappa dei titoli più le righe grezze
     */
    public Map<String, List<String>> leggeMappaConfig(final String simpleNameFileToBeRead, final boolean compresiTitoli) {
        String rawText = leggeConfig(simpleNameFileToBeRead);
        return leggeMappa(rawText, compresiTitoli);
    }


    /**
     * Legge una mappa di risorse da {project directory}/config/ <br>
     * La prima riga contiene i titoli
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     *
     * @return mappa dei titoli più le righe grezze
     */
    public Map<String, List<String>> leggeMappaServer(final String simpleNameFileToBeRead) {
        return leggeMappaServer(simpleNameFileToBeRead, true);
    }

    /**
     * Legge una mappa di risorse da {project directory}/config/ <br>
     * La mappa NON contiene i titoli
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     * @param compresiTitoli         parte dalla prima riga, altrimenti dalla seconda
     *
     * @return mappa dei titoli più le righe grezze
     */
    public Map<String, List<String>> leggeMappaServer(final String simpleNameFileToBeRead, final boolean compresiTitoli) {
        String rawText = leggeServer(simpleNameFileToBeRead);
        return leggeMappa(rawText, compresiTitoli);
    }

    /**
     * Legge una mappa di risorse <br>
     * La prima riga contiene i titoli
     *
     * @param rawText        testo grezzo del file
     * @param compresiTitoli parte dalla prima riga, altrimenti dalla seconda
     *
     * @return mappa delle righe grezze con eventualmente i titoli
     */
    private Map<String, List<String>> leggeMappa(final String rawText, final boolean compresiTitoli) {
        Map<String, List<String>> mappa = null;
        List<String> listaParti;
        String[] righe;
        String[] parti;

        if (textService.isValid(rawText)) {
            righe = rawText.split(CAPO);
            if (righe != null && righe.length > 0) {
                mappa = new LinkedHashMap<>();
                for (String riga : righe) {
                    listaParti = new ArrayList<>();
                    parti = riga.split(VIRGOLA);

                    if (parti != null && parti.length > 1) {
                        for (int k = 1; k < parti.length; k++) {
                            listaParti.add(parti[k]);
                        }
                    }
                    mappa.put(parti[0], listaParti);
                }
            }
        }

        if (mappa != null) {
            if (!compresiTitoli) {
                if (mappa.containsKey("id")) {
                    mappa.remove("id");
                }
                else {
                    logger.error(new WrapLog().exception(new AlgosException("Manca la riga chiave dei titoli")).usaDb());
                }
            }
        }

        return mappa;
    }

    /**
     * Legge un file di risorse da {project directory}/frontend/ <br>
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     *
     * @return testo completo grezzo del file
     */
    public String leggeFrontend(final String simpleNameFileToBeRead) {
        String tag = "frontend";
        return fileService.leggeFile(tag + File.separator + simpleNameFileToBeRead);
    }


    /**
     * Legge un file di risorse da {project directory}/src/main/resources/META-INF/resources/ <br>
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     *
     * @return testo completo grezzo del file
     */
    public String leggeMetaInf(final String simpleNameFileToBeRead) {
        String path = simpleNameFileToBeRead;
        String tag = "src/main/resources/META-INF/resources";

        if (simpleNameFileToBeRead != null && !simpleNameFileToBeRead.startsWith(tag)) {
            path = tag + File.separator + simpleNameFileToBeRead;
        }

        return fileService.leggeFile(path);
    }


    /**
     * Legge un file di risorse <br>
     *
     * @param nameFileToBeRead nome del file
     *
     * @return testo grezzo del file
     */
    public String leggeRisorsa(final String nameFileToBeRead) {
        //        File filePath = new File("config" + File.separator + nameFileToBeRead);
        File filePath = new File("META-INF.resources" + File.separator + nameFileToBeRead);
        return fileService.leggeFile(filePath.getAbsolutePath());
    }

    /**
     * Legge un file dal server <br>
     *
     * @param nameFileToBeRead nome del file
     *
     * @return testo grezzo del file
     */
    public String leggeServer(final String nameFileToBeRead) {
        return webService.leggeWebTxt(WebService.URL_BASE_VAADIN23 + nameFileToBeRead);
    }

    /**
     * Legge una lista di righe di risorse dal server <br>
     * La prima riga contiene i titoli
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     *
     * @return lista di righe grezze
     */
    public List<String> leggeListaServer(final String simpleNameFileToBeRead) {
        return leggeListaServer(simpleNameFileToBeRead, true);
    }

    /**
     * Legge una lista di righe di risorse dal server <br>
     * La prima riga contiene i titoli
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     * @param compresiTitoli         parte dalla prima riga, altrimenti dalla seconda
     *
     * @return lista di righe grezze
     */
    public List<String> leggeListaServer(final String simpleNameFileToBeRead, final boolean compresiTitoli) {
        String rawText = leggeServer(simpleNameFileToBeRead);
        return leggeLista(rawText, compresiTitoli);
    }

    /**
     * Costruisce un file di risorse, partendo dal nome semplice <br>
     * La risorsa DEVE essere nel path 'src/main/resources/META-INF/resources/' <br>
     *
     * @param simpleNameFileToBeRead nome del file all'interno della directory 'resources'
     *
     * @return bytes
     */
    public File getFile(final String simpleNameFileToBeRead) {
        File resourceFile = null;
        String pathResourceFileName = VUOTA;

        if (textService.isEmpty(simpleNameFileToBeRead)) {
            return null;
        }

        if (simpleNameFileToBeRead.startsWith(PATH_RISORSE)) {
            pathResourceFileName = simpleNameFileToBeRead;
        }
        else {
            pathResourceFileName = PATH_RISORSE + simpleNameFileToBeRead;
        }

        resourceFile = new File(pathResourceFileName);

        return resourceFile;
    }


    /**
     * Legge i bytes di un file di risorse <br>
     * La risorsa DEVE essere nel path 'src/main/resources/META-INF/resources/' <br>
     *
     * @param simpleResourceFileName nome del file all'interno della directory 'resources'
     *
     * @return bytes
     */
    public byte[] getBytes(final String simpleResourceFileName) {
        byte[] bytes = null;
        File resourceFile = getFile(simpleResourceFileName);

        if (resourceFile != null && resourceFile.exists() && resourceFile.isFile()) {
            try {
                bytes = FileUtils.readFileToByteArray(resourceFile);
            } catch (Exception unErrore) {
                logger.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb().type(AETypeLog.resources));
            }
        }
        else {
            logger.error(new WrapLog().exception(new AlgosException(NON_ESISTE_FILE)).usaDb().type(AETypeLog.resources));
            return bytes;
        }

        return bytes;
    }


    /**
     * Costruisce una Image partendo dai bytes <br>
     *
     * @param bytes dell'immagine
     *
     * @return image
     */
    public Image getImageFromBytes(final byte[] bytes) {
        Image image = null;
        StreamResource resource;

        if (bytes != null) {
            try {
                resource = new StreamResource("manca.jpg", () -> new ByteArrayInputStream(bytes));
                image = new Image(resource, "manca");
            } catch (Exception unErrore) {
                logger.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb().type(AETypeLog.resources));
            }
        }

        return image;
    }


    /**
     * Costruisce una Image partendo da un file di risorse <br>
     * Legge i bytes di un file di risorse <br>
     * Legge le risorse di un file <br>
     *
     * @param simpleResourceFileName nome del file all'interno della directory 'resources'
     *
     * @return image
     */
    public Image getImageFromFile(final String simpleResourceFileName) {
        Image image = null;
        byte[] bytes = getBytes(simpleResourceFileName);

        if (bytes != null) {
            image = getImageFromBytes(bytes);
        }

        return image;
    }


    /**
     * Costruisce una Image partendo dal mongoDB <br>
     * Legge i bytes decodificando il valore memorizzato <br>
     *
     * @param mongoValue valore codificato nel mongoDB
     *
     * @return image
     */
    public Image getImageFromMongo(final String mongoValue) {
        Image image = null;
        byte[] bytes = Base64.decodeBase64(mongoValue);

        if (bytes != null) {
            image = getImageFromBytes(bytes);
        }

        return image;
    }


    public Image getImagePng(final String simpleResourceFileName) {
        return getImageFromFile(simpleResourceFileName + PUNTO + "png");
    }


    public Image getBandieraFromFile(final String simpleResourceFileName) {
        Image image = getImagePng("bandiere/" + simpleResourceFileName.toLowerCase());

        if (image != null) {
            image.setWidth("30px");
            image.setHeight("21px");
        }

        return image;
    }


    public Image getBandieraFromMongo(final String mongoValue) {
        Image image = getImageFromMongo(mongoValue);

        if (image != null) {
            image.setWidth("30px");
            image.setHeight("21px");
        }

        return image;
    }


    /**
     * Legge una stringa codificata dal file di risorse <br>
     *
     * @param simpleResourceFileName nome del file all'interno della directory 'resources'
     *
     * @return valore codificato
     */
    public String getSrc(final String simpleResourceFileName) {
        String bytesCodificati = VUOTA;
        byte[] bytes = getBytes(simpleResourceFileName);

        if (bytes != null) {
            bytesCodificati = Base64.encodeBase64String(bytes);
        }

        return bytesCodificati;
    }


    /**
     * Legge una stringa codificata dal file di risorse <br>
     *
     * @param simpleResourceFileNameWithoutSuffix nome del file all'interno della directory 'bandiere'
     *
     * @return valore codificato
     */
    public String getSrcBandieraPng(final String simpleResourceFileNameWithoutSuffix) {
        return getSrc("bandiere/" + simpleResourceFileNameWithoutSuffix.toLowerCase() + PUNTO + "png");
    }


    /**
     * Legge una mappa CSV da un file <br>
     * Prima lista (prima riga): titoli
     * Liste successive (righe successive): valori
     *
     * @param pathFileToBeRead nome completo del file
     *
     * @return lista di mappe di valori
     */
    public List<LinkedHashMap<String, String>> leggeMappaCSV(final String pathFileToBeRead) {
        return fileService.leggeMappaCSV(pathFileToBeRead);
    }

}