package it.algos.vaad23.ui.dialog;

import com.vaadin.flow.component.notification.*;
import it.algos.vaad23.backend.enumeration.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mer, 30-mar-2022
 * Time: 08:24
 */
public class Avviso {

    public static Notification show(String text) {
        int durata = Pref.durataAvviso.getInt();
        return Notification.show(text, durata > 0 ? durata : 2000, Notification.Position.BOTTOM_START);
    }

    public static Notification show1000(String text) {
        return Notification.show(text, 1000, Notification.Position.BOTTOM_START);
    }

    public static Notification show2000(String text) {
        return Notification.show(text, 2000, Notification.Position.BOTTOM_START);
    }

    public static Notification show3000(String text) {
        return Notification.show(text, 3000, Notification.Position.BOTTOM_START);
    }

}
