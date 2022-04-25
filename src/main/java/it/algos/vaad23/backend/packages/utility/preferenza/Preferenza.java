package it.algos.vaad23.backend.packages.utility.preferenza;

import it.algos.vaad23.backend.annotation.*;
import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.backend.enumeration.*;
import org.springframework.data.mongodb.core.index.*;

import javax.validation.constraints.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 26-mar-2022
 * Time: 14:02
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
public class Preferenza extends AEntity {

    /**
     * codice di riferimento (obbligatorio, unico)
     */
    @NotEmpty()
    //    @Size(min = 3)
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, search = true)
    public String code;


    /**
     * tipo di dato memorizzato (obbligatorio)
     */
    @NotNull
    //    @AIField(type = AETypeField.enumeration, enumClazz = AETypePref.class, usaComboBox = true, required = true, focus = true, widthEM = 12)
    //    @AIColumn(widthEM = 6, sortable = true)
    public AETypePref type;

    /**
     * valore della preferenza (obbligatorio)
     */
    @NotNull
    //    @AIField(type = AETypeField.preferenza, required = true, caption = "Valore", widthEM = 12)
    //    @AIColumn(widthEM = 10)
    public byte[] value;


    /**
     * generale (facoltativo, default true) se usata da vaadflow
     * specifica se usata da progetto derivato (vaadwam, vaadwiki)
     */
    //    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolField.checkBox, caption = "Preferenza standard")
    //    @AIColumn(headerIcon = VaadinIcon.HOME)
    public boolean vaad23;


    /**
     * usaCompany (facoltativo, default false) usa un prefisso col codice della company
     */
    //    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolField.checkBox, caption = "Specifica per ogni company")
    //    @AIColumn(headerIcon = VaadinIcon.FACTORY)
    public boolean usaCompany;


    /**
     * necessita di riavvio per essere utilizzata (facoltativo, default false)
     * alla partenza del programma viene acquisita nelle costanti di FlowCost
     * per evitare inutili accessi al mongoDB
     */
    //    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolField.checkBox, caption = "Occorre riavviare")
    //    @AIColumn(headerIcon = VaadinIcon.REFRESH)
    public boolean needRiavvio;

    /**
     * visibile e modificabile da un admin (facoltativo, default false)
     * per creare una lista di preferenze nella scheda utente dell'admin oppure
     * nella scheda della company
     */
    //    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolField.checkBox, caption = "Visibile solo agli admin")
    //    @AIColumn(headerIcon = VaadinIcon.USER)
    public boolean visibileAdmin;

    /**
     * descrizione (obbligatoria)
     */
    @NotEmpty()
    //    @Size(min = 5)
    //    @AIField(type = AETypeField.text, widthEM = 24)
    //    @AIColumn(widthEM = 24, flexGrow = true)
    public String descrizione;

    /**
     * descrizione estesa (facoltativa)
     */
    //    @Size(min = 5)
    //    @AIField(type = AETypeField.text, widthEM = 24)
    //    @AIColumn(widthEM = 24, flexGrow = true)
    public String descrizioneEstesa;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getCode();
    }


    public Object getValore() {
        return type != null ? type.bytesToObject(value) : null;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizioneEstesa() {
        return descrizioneEstesa;
    }

    public void setDescrizioneEstesa(String descrizioneEstesa) {
        this.descrizioneEstesa = descrizioneEstesa;
    }

    public AETypePref getType() {
        return type;
    }

    public void setType(AETypePref type) {
        this.type = type;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public boolean isVaad23() {
        return vaad23;
    }

    public void setVaad23(boolean vaad23) {
        this.vaad23 = vaad23;
    }

    public boolean isUsaCompany() {
        return usaCompany;
    }

    public void setUsaCompany(boolean usaCompany) {
        this.usaCompany = usaCompany;
    }

    public boolean isNeedRiavvio() {
        return needRiavvio;
    }

    public void setNeedRiavvio(boolean needRiavvio) {
        this.needRiavvio = needRiavvio;
    }

    public boolean isVisibileAdmin() {
        return visibileAdmin;
    }

    public void setVisibileAdmin(boolean visibileAdmin) {
        this.visibileAdmin = visibileAdmin;
    }


}// end of crud entity class