package it.algos.vaad23.backend.service;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;


/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 12-mar-2022
 * Time: 17:08
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(ClassService.class); <br>
 * 3) @Autowired public ClassService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ClassService extends AbstractService {

    /**
     * Controlla che esiste una classe xxxLogicForm associata alla Entity inviata  <br>
     *
     * @param dovrebbeEssereUnaEntityClazz the entity class
     *
     * @return classe xxxLogicForm associata alla Entity
     */
    public boolean isLogicFormClassFromEntityClazz(final Class dovrebbeEssereUnaEntityClazz) {
        return getLogicFormClassFromEntityClazz(dovrebbeEssereUnaEntityClazz) != null;
    }


    /**
     * Classe xxxLogicForm associata alla Entity inviata  <br>
     *
     * @param dovrebbeEssereUnaEntityClazz the entity class
     *
     * @return classe xxxLogicForm associata alla Entity
     */
    public Class getLogicFormClassFromEntityClazz(final Class dovrebbeEssereUnaEntityClazz) {
        return getLogicFormClassFromEntityClazz(dovrebbeEssereUnaEntityClazz, false);
    }


    /**
     * Classe xxxLogicForm associata alla Entity inviata  <br>
     *
     * @param dovrebbeEssereUnaEntityClazz the entity class
     * @param printLog                     flag per mostrare l'eccezione (se non trova la classe specifica)
     *
     * @return classe xxxLogicForm associata alla Entity
     */
    public Class getLogicFormClassFromEntityClazz(final Class dovrebbeEssereUnaEntityClazz, boolean printLog) {
        Class listClazz = null;
        String canonicalNameEntity;
        String canonicalNameLogicForm;
        String message;
        String packageName;
        String simpleNameEntity;
        String simpleNameLogicForm = VUOTA;

        //        if (dovrebbeEssereUnaEntityClazz == null) {
        //            message = String.format("Manca la EntityClazz");
        //            logger.error(message, this.getClass(), "getLogicFormClassFromEntityClazz");
        //            return null;
        //        }

        //        canonicalNameEntity = dovrebbeEssereUnaEntityClazz.getCanonicalName();
        //        simpleNameEntity = fileService.estraeClasseFinale(canonicalNameEntity);
        //        packageName = text.levaCoda(simpleNameEntity, SUFFIX_ENTITY).toLowerCase();

        //        if (!annotation.isEntityClass(dovrebbeEssereUnaEntityClazz)) {
        //            message = String.format("La clazz ricevuta %s NON è una EntityClazz", simpleNameEntity);
        //            logger.info(message, this.getClass(), "getLogicFormClassFromEntityClazz");
        //            return null;
        //        }

        //        //--provo a creare la classe specifica xxxLogicForm (classe, non istanza)
        //        try {
        //            canonicalNameLogicForm = text.levaCoda(canonicalNameEntity, SUFFIX_ENTITY) + SUFFIX_LOGIC_FORM;
        //            simpleNameLogicForm = fileService.estraeClasseFinale(canonicalNameLogicForm);
        //
        //            listClazz = Class.forName(canonicalNameLogicForm);
        //        } catch (Exception unErrore) {
        //            if (printLog) {
        //                message = String.format("Nel package %s non esiste la classe %s", packageName, simpleNameLogicForm);
        //                logger.info(message, this.getClass(), "getLogicFormClassFromEntityClazz");
        //            }
        //        }

        return listClazz;
    }

    public String getProjectName() {
        String projectName;
        String pathCurrent = System.getProperty("user.dir") + SLASH;
        projectName = fileService.estraeDirectoryFinale(pathCurrent);

        if (projectName.endsWith(SLASH)) {
            projectName = textService.levaCoda(projectName, SLASH);
        }

        return projectName;
    }

}