package it.algos.vaad23.backend.converter;

import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.data.converter.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project it.algos.vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 27-mag-2018
 * Time: 14:11
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ConverterPrefByte implements Converter<String, byte[]> {

    private AETypePref type;

    public ConverterPrefByte(AETypePref type) {
        this.type = type;
    }

    @Override
    public Result<byte[]> convertToModel(String stringValue, ValueContext valueContext) {
        if (type != null) {
            return Result.ok((byte[]) type.objectToBytes(stringValue));
        }
        else {
            return Result.ok((byte[]) null);
        }
    }

    @Override
    public String convertToPresentation(byte[] bytes, ValueContext valueContext) {
        String stringValue = VUOTA;
        Object genericValue;

        if (type != null && bytes != null) {
            genericValue = type.bytesToObject(bytes);

            if (genericValue instanceof String) {
                stringValue = (String) genericValue;
            }

            if (genericValue instanceof Integer) {
                stringValue = ((Integer) genericValue).toString();
            }

            if (genericValue instanceof Boolean) {
                stringValue = genericValue.toString();
            }
        }

        return stringValue;
    }

    public AETypePref getType() {
        return type;
    }

    public void setType(AETypePref type) {
        this.type = type;
    }

}// end of converter class
