package it.algos.vaad23.wizard.scripts;

import com.vaadin.flow.component.checkbox.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.wizard.enumeration.*;
import static it.algos.vaad23.wizard.scripts.WizElaboraNewProject.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project vaadbio
 * Created by Algos
 * User: gac
 * Date: mer, 13-apr-2022
 * Time: 06:49
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizElaboraUpdateProject extends WizElabora {

    private String updateProject;

    public WizElaboraUpdateProject(String updateProject) {
        super();
        this.updateProject = updateProject;
    }// end of constructor

    public void esegue(final LinkedHashMap<String, Checkbox> mappaCheckbox) {
        super.progettoEsistente = true;
        AEWizProject wiz;
        destNewProject = System.getProperty("user.dir");
        newUpdateProject = fileService.estraeClasseFinaleSenzaJava(destNewProject).toLowerCase();
        srcVaadin23 = textService.levaCoda(destNewProject, newUpdateProject);
        srcVaadin23 += VAADIN_PROJECT + SLASH;
        destNewProject += SLASH;

        super.esegue();

        for (String key : mappaCheckbox.keySet()) {
            if (mappaCheckbox.get(key).getValue()) {
                wiz = AEWizProject.valueOf(key);
                if (wiz != null) {
                    switch (wiz.getCopy().getType()) {
                        case directory -> directory(wiz);
                        case file -> file(wiz);
                        case source -> source(wiz);
                    }
                }
            }
        }

        super.eliminaSources();
    }

}
