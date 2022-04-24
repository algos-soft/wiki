package it.algos.vaad23.wizard.scripts;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.wizard.enumeration.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;


/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 13-apr-2020
 * Time: 05:31
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizElaboraNewProject extends WizElabora {

    public static final String SOURCE_PREFIX = "src/main/java/it/algos/";

    public static final String SOURCE_SUFFFIX = "/wizard/sources/";

    public static final String VAADIN_PROJECT = "vaadin23";

    public static final String VAADIN_MODULE = "vaad23";


    public void esegue(final String pathNewUpdateProject) {
        String message;
        progettoEsistente = fileService.isContieneProgettoValido(pathNewUpdateProject);
        srcVaadin23 = System.getProperty("user.dir") + SLASH;
        destNewProject = pathNewUpdateProject + SLASH;
        newUpdateProject = fileService.estraeDirectoryFinaleSenzaSlash(destNewProject).toLowerCase();

        super.esegue();

        for (AEWizProject wiz : AEWizProject.getAllNewProject()) {
            switch (wiz.getCopy().getType()) {
                case directory -> directory(wiz);
                case file -> file(wiz);
                case source -> source(wiz);
            }
        }

        super.eliminaSources();
    }

}
