package it.algos.vaad23.backend.enumeration;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 29-set-2020
 * Time: 14:34
 */
public enum AEContinente {

    europa("Europa", true),
    asia("Asia", true),
    africa("Africa", true),
    nordamerica("Nordamerica", true),
    sudamerica("Sudamerica", true),
    oceania("Oceania", true),
    antartide("Antartide", false),
    ;

    boolean abitato;

    private String nome;


    AEContinente(String nome, boolean abitato) {
        this.nome = nome;
        this.abitato = abitato;
    }

    public int getOrd() {
        return this.ordinal() + 1;
    }

    public String getNome() {
        return nome;
    }


    public boolean isAbitato() {
        return abitato;
    }


    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    @Override
    public String toString() {
        return getNome();
    }
}
