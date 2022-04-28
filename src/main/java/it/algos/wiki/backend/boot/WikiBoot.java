package it.algos.wiki.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad23.backend.boot.*;
import it.algos.vaad23.backend.interfaces.*;
import it.algos.vaad23.backend.packages.geografia.continente.*;
import static it.algos.wiki.backend.boot.WikiCost.*;
import it.algos.wiki.backend.packages.attivita.*;
import it.algos.wiki.backend.packages.bio.*;
import it.algos.wiki.backend.packages.doppionome.*;
import it.algos.wiki.backend.packages.genere.*;
import it.algos.wiki.backend.packages.nazionalita.*;
import it.algos.wiki.backend.packages.professione.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project Wiki
 * Created by Algos
 * User: gac
 * Date: dom, 24 apr 22
 * <p>
 * Questa classe astratta riceve un @EventListener implementato nella superclasse <br>
 * 1) regola alcuni parametri standard del database MongoDB <br>
 * 2) regola le variabili generali dell'applicazione <br>
 * 3) crea i dati di alcune collections sul DB mongo <br>
 * 4) crea le preferenze standard e specifiche dell'applicazione <br>
 * 5) aggiunge le @Route (view) standard e specifiche <br>
 * 6) lancia gli schedulers in background <br>
 * 7) costruisce una versione demo <br>
 * 8) controlla l' esistenza di utenti abilitati all' accesso <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WikiBoot extends VaadBoot {

    /**
     * Regola le variabili generali dell' applicazione con il loro valore iniziale di default <br>
     * Le variabili (static) sono uniche per tutta l' applicazione <br>
     * Il loro valore può essere modificato SOLO in questa classe o in una sua sottoclasse <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixVariabili() {
        super.fixVariabili();

        /**
         * Nome identificativo minuscolo del progetto corrente <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.projectCurrent = "wiki";

        /**
         * Nome identificativo maiuscolo dell' applicazione <br>
         * Usato (eventualmente) nella barra di menu in testa pagina <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.projectNameUpper = "Wiki";

        /**
           * Classe da usare per gestire le versioni <br>
           * Di default FlowVers oppure possibile sottoclasse del progetto <br>
           * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
           */
        VaadVar.versionClazz = WikiVers.class;

        /**
         * Versione dell' applicazione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.projectVersion = Double.parseDouble(Objects.requireNonNull(environment.getProperty("algos.wiki.version")));

        /**
         * Data di rilascio della versione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.projectDate = Objects.requireNonNull(environment.getProperty("algos.wiki.version.date"));
    }

    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    @Qualifier(TAG_WIKI_VERSION)
    public void setVersInstance(final AIVers versInstance) {
        this.versInstance = versInstance;
    }


    /**
     * Aggiunge al menu le @Route (view) standard e specifiche <br>
     * Questa classe viene invocata PRIMA della chiamata del browser <br>
     * <p>
     * Nella sottoclasse che invoca questo metodo, aggiunge le @Route (view) specifiche dell' applicazione <br>
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in VaadVar <br>
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixMenuRoutes() {
        super.fixMenuRoutes();
        VaadVar.menuRouteList.remove(ContinenteView.class);

        VaadVar.menuRouteList.add(GenereView.class);
        VaadVar.menuRouteList.add(AttivitaView.class);
        VaadVar.menuRouteList.add(NazionalitaView.class);
        VaadVar.menuRouteList.add(ProfessioneView.class);
        VaadVar.menuRouteList.add(DoppionomeView.class);
        VaadVar.menuRouteList.add(BioView.class);
    }

}
