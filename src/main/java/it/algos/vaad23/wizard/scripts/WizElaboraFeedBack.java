package it.algos.vaad23.wizard.scripts;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.exception.*;
import it.algos.vaad23.backend.interfaces.*;
import it.algos.vaad23.backend.wrapper.*;
import static it.algos.vaad23.wizard.scripts.WizCost.*;
import static it.algos.vaad23.wizard.scripts.WizElaboraNewProject.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project sette
 * Created by Algos
 * User: gac
 * Date: lun, 11-apr-2022
 * Time: 17:18
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizElaboraFeedBack extends WizElabora {

    public void esegue() {
        String message;
        boolean esisteSrc;
        boolean esisteDest;
        AIResult result;
        String srcWizardProject = System.getProperty("user.dir");
        String currentProject = fileService.estraeClasseFinaleSenzaJava(srcWizardProject).toLowerCase();
        String destBaseVaadin23 = textService.levaCoda(srcWizardProject, currentProject);

        destBaseVaadin23 += VAADIN_PROJECT + SLASH;

        String srcWizard = String.format("%s%s%s%s%s%s", srcWizardProject, SLASH, SOURCE_PREFIX, VAADIN_MODULE, SLASH, DIR_WIZARD);
        String destWizard = String.format("%s%s%s%s%s", destBaseVaadin23, SOURCE_PREFIX, VAADIN_MODULE, SLASH, DIR_WIZARD);

        esisteSrc = fileService.isEsisteDirectory(srcWizard);
        esisteDest = fileService.isEsisteDirectory(destWizard);

        if (esisteSrc && esisteDest) {
            result = fileService.copyDirectory(AECopy.dirAddingOnly, srcWizard, destWizard);
            if (result.isValido()) {
                message = "La directory 'wizard' è stata aggiornata su 'vaadin23'";
                logger.info(new WrapLog().type(AETypeLog.wizard).message(message));
            }
            else {
                message = "La directory 'wizard' non è stata aggiornata";
                logger.warn(new WrapLog().type(AETypeLog.wizard).exception(new AlgosException(message)));
            }
        }
        else {
            if (!esisteSrc) {
                message = String.format("Il path sorgente %s è errato", srcWizard);
                logger.warn(new WrapLog().type(AETypeLog.wizard).message(message));
            }
            if (!esisteDest) {
                message = String.format("Il path destinazione %s è errato", destWizard);
                logger.warn(new WrapLog().type(AETypeLog.wizard).message(message));
            }
        }
    }

}
