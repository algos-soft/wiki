package it.algos.vaad23.backend.wrapper;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad23.backend.enumeration.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 25-mar-2022
 * Time: 13:41
 * Wrapper di informazioni (Fluent API) per costruire un elemento Span <br>
 * Può contenere:
 * -weight (AETypeWeight) grassetto
 * -color (AETypeColor) colore
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WrapSpan {

    private String message;

    private AEFontWeight weight;

    private AETypeColor color;

    private AEFontHeight fontHeight;

    private AELineHeight lineHeight;

    private AEFontStyle style;

    public WrapSpan() {
    }

    public WrapSpan(String message) {
        this.message = message;
    }

    public WrapSpan message(String message) {
        this.message = message;
        return this;
    }

    public WrapSpan weight(AEFontWeight weight) {
        this.weight = weight;
        return this;
    }

    public WrapSpan color(AETypeColor color) {
        this.color = color;
        return this;
    }

    public WrapSpan lineHeight(AELineHeight lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    public WrapSpan fontHeight(AEFontHeight fontHeight) {
        this.fontHeight = fontHeight;
        return this;
    }

    public WrapSpan style(AEFontStyle style) {
        this.style = style;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AEFontWeight getWeight() {
        return weight;
    }

    public AETypeColor getColor() {
        return color;
    }

    public AEFontHeight getFontHeight() {
        return fontHeight;
    }

    public AELineHeight getLineHeight() {
        return lineHeight;
    }

    public AEFontStyle getStyle() {
        return style;
    }

}
