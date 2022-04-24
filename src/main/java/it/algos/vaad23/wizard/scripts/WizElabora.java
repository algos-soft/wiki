package it.algos.vaad23.wizard.scripts;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.interfaces.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.backend.wrapper.*;
import it.algos.vaad23.wizard.enumeration.*;
import static it.algos.vaad23.wizard.scripts.WizElaboraNewProject.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 09-apr-2022
 * Time: 07:26
 */
public abstract class WizElabora {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public LogService logger;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public FileService fileService;

    protected String srcVaadin23;

    protected String destNewProject;

    protected String newUpdateProject;

    boolean progettoEsistente;

    public void esegue() {
        String message;
        logger.info(new WrapLog().type(AETypeLog.spazio));

        if (progettoEsistente) {
            message = String.format("Aggiornato il progetto esistente: '%s'", newUpdateProject);
        }
        else {
            message = String.format("Creato il nuovo project: '%s'", newUpdateProject);
        }
        logger.info(new WrapLog().message(message).type(AETypeLog.wizard));

        AEToken.reset();
        AEToken.setCrono();
        AEToken.targetProject.set(newUpdateProject);
        AEToken.targetProjectUpper.set(textService.primaMaiuscola(newUpdateProject));
        AEToken.targetProjectAllUpper.set(newUpdateProject.toUpperCase());
        AEToken.firstProject.set(newUpdateProject.substring(0, 1).toUpperCase());
    }

    public void directory(final AEWizProject wiz) {
        String message;
        AIResult result;
        String srcPath = srcVaadin23 + wiz.getCopyDest() + SLASH;
        String destPath = destNewProject + wiz.getCopyDest() + SLASH;
        String dir = fileService.estraeDirectoryFinaleSenzaSlash(destPath).toLowerCase();
        String tag = progettoEsistente ? "Update" : "New";

        result = fileService.copyDirectory(wiz.getCopy(), srcPath, destPath);
        if (result.isValido()) {
            message = String.format("%s: %s (%s)", tag, textService.primaMinuscola(result.getMessage()), wiz.getCopy());
            logger.info(new WrapLog().message(message).type(AETypeLog.wizard));
        }
        else {
            message = String.format("%s: la directory %s non ha funzionato", tag, dir);
            logger.warn(new WrapLog().message(message).type(AETypeLog.wizard));
        }
    }

    public void file(final AEWizProject wiz) {
    }


    public void source(final AEWizProject wiz) {
        String message;
        AIResult result;
        String dest = wiz.getCopyDest();
        String nomeFile = wiz.getFileSource();
        String sorcePath = srcVaadin23 + SOURCE_PREFIX + VAADIN_MODULE + SOURCE_SUFFFIX + nomeFile;
        sorcePath += sorcePath.endsWith(TXT_SUFFIX) ? VUOTA : TXT_SUFFIX;
        String sourceText = fileService.leggeFile(sorcePath);
        sourceText = AEToken.replaceAll(sourceText);
        String destPath = destNewProject + dest;
        destPath = AEToken.replaceAll(destPath);
        String tag = progettoEsistente ? "Update" : "New";

        result = fileService.scriveFile(wiz.getCopy(), destPath, sourceText);
        if (result.isValido()) {
            message = String.format("%s: %s (%s)", tag, textService.primaMinuscola(result.getMessage()), wiz.getCopy());
            logger.info(new WrapLog().message(message).type(AETypeLog.wizard));
        }
        else {
            message = String.format("%s: il file %s non ha funzionato", tag, nomeFile);
            logger.warn(new WrapLog().message(message).type(AETypeLog.wizard));
        }
    }

    public void eliminaSources() {
        String message;

        //--elimina la directory 'sources' che deve restare unicamente nel progetto 'vaadin23' e non nei derivati
        if (fileService.deleteDirectory(destNewProject + SOURCE_PREFIX + VAADIN_MODULE + SOURCE_SUFFFIX)) {
            message = String.format("Delete: cancellata la directory 'sources' dal progetto %s", newUpdateProject);
            logger.info(new WrapLog().message(message).type(AETypeLog.wizard));
        }
        else {
            message = String.format("Non sono riuscito a cancellare la directory 'sources' dal progetto %s", newUpdateProject);
            logger.warn(new WrapLog().message(message).type(AETypeLog.wizard));
        }
    }

}
