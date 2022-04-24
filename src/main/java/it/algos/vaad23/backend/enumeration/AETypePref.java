package it.algos.vaad23.backend.enumeration;


import com.google.common.primitives.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.interfaces.*;

import java.nio.*;
import java.nio.charset.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

/**
 * Created by gac on 30 lug 2016. <br>
 * Enum dei tipi di preferenza supportati. <br>
 * Codifica e decodifica specifiche per ogni tipologia. <br>
 * Usato sempre il charset di caratteri UTF-8 <br>
 */
public enum AETypePref implements AIPref {
    string("string", "Blue") {
        @Override
        public byte[] objectToBytes(Object obj) {
            byte[] bytes = new byte[0];
            if (obj instanceof String stringa) {
                //                String stringa = (String) obj;
                bytes = stringa.getBytes(Charset.forName("UTF-8"));
            }
            return bytes;
        }

        @Override
        public String bytesToObject(byte[] bytes) {
            String obj = "";
            if (bytes != null) {
                obj = new String(bytes, Charset.forName("UTF-8"));
            }
            return obj;
        }

        @Override
        public String bytesToString(byte[] bytes) {
            return bytesToObject(bytes);
        }
    },// end of single enumeration

    bool("bool", "black") {
        @Override
        public byte[] objectToBytes(Object obj) {
            byte[] bytes = new byte[0];
            if (obj instanceof Boolean bool) {
                bytes = new byte[]{(byte) (bool ? 1 : 0)};
            }
            if (obj instanceof String stringa) {
                boolean bool = false;
                if (stringa.equals("Vero") || stringa.equals("vero") || stringa.equals("True") | stringa.equals("true")) {
                    bool = true;
                }
                bytes = new byte[]{(byte) (bool ? 1 : 0)};
            }
            return bytes;
        }

        @Override
        @SuppressWarnings("all")
        public Boolean bytesToObject(byte[] bytes) {
            Object obj = null;
            if (bytes != null && bytes.length > 0) {
                byte b = bytes[0];
                obj = new Boolean(b == (byte) 0b00000001);
            }
            else {
                obj = new Boolean(false);
            }
            return (Boolean) obj;
        }

        @Override
        public String bytesToString(byte[] bytes) {
            return bytesToObject(bytes) ? VERO : FALSO;
        }
    },// end of single enumeration

    integer("int", "Green") {
        @Override
        public byte[] objectToBytes(Object obj) {
            byte[] bytes = new byte[0];
            if (obj instanceof Integer) {
                int num = (Integer) obj;
                bytes = intToByteArray(num);
            }
            if (obj instanceof String) {
                bytes = intToByteArray(new Integer((String) obj));
            }

            return bytes;
        }


        @Override
        public Integer bytesToObject(byte[] bytes) {
            return byteArrayToInt(bytes);
        }


        @Override
        public String bytesToString(byte[] bytes) {
            return bytesToObject(bytes) + VUOTA;
        }
    },// end of single enumeration

    lungo("long", "black") {
        @Override
        public byte[] objectToBytes(Object obj) {
            byte[] bytes = new byte[0];
            if (obj instanceof Long) {
                long num = (Long) obj;
                bytes = longToByteArray(num);
            }
            if (obj instanceof String) {
                bytes = longToByteArray(new Long((String) obj));
            }

            return bytes;
        }

        @Override
        public Long bytesToObject(byte[] bytes) {
            return byteArrayToLong(bytes);
        }

        @Override
        public String bytesToString(byte[] bytes) {
            return bytesToObject(bytes) + VUOTA;
        }
    },// end of single enumeration

    localdate("data", "Olive") {
        @Override
        public byte[] objectToBytes(Object obj) {
            byte[] bytes = new byte[0];
            LocalDate data;
            long giorni;

            if (obj instanceof LocalDate) {
                data = (LocalDate) obj;
                giorni = data.toEpochDay();
                bytes = Longs.toByteArray(giorni);
            }
            return bytes;
        }


        @Override
        public LocalDate bytesToObject(byte[] bytes) {
            LocalDate data = null;
            long giorni = 0;

            if (bytes != null && bytes.length > 0) {
                giorni = Longs.fromByteArray(bytes);
                data = LocalDate.ofEpochDay(giorni);
            }

            return data;
        }

        @Override
        public String bytesToString(byte[] bytes) {
            return bytesToObject(bytes).format(DateTimeFormatter.ofPattern("d MMM yyyy"));
        }
    },// end of single enumeration

    localdatetime("datatime", "Fuchsia") {
        @Override
        public byte[] objectToBytes(Object obj) {
            byte[] bytes = new byte[0];
            LocalDateTime data;
            long millis;

            if (obj instanceof LocalDateTime) {
                data = (LocalDateTime) obj;
                millis = data.toEpochSecond(ZoneOffset.UTC);
                //                long millis = LibDate.getLongSecs((LocalDateTime) obj);
                //                long millis = ((LocalDateTime) obj).;
                bytes = Longs.toByteArray(millis);
            }
            return bytes;
        }


        @Override
        public LocalDateTime bytesToObject(byte[] bytes) {
            LocalDateTime data = null;
            long millis = 0;

            //            return bytes.length > 0 ? LibDate.dateToLocalDateTime(new Date(Longs.fromByteArray(bytes))) : null;
            if (bytes != null && bytes.length > 0) {
                millis = Longs.fromByteArray(bytes);
                data = bytes.length > 0 ? LocalDateTime.ofEpochSecond(millis, 0, ZoneOffset.UTC) : null;
            }

            return data;
        }

        @Override
        public String bytesToString(byte[] bytes) {
            return bytesToObject(bytes).format(DateTimeFormatter.ofPattern("d-M-yy H:mm"));
        }
    },// end of single enumeration

    localtime("time", "Aqua") {
        @Override
        public byte[] objectToBytes(Object obj) {
            byte[] bytes = new byte[0];
            if (obj instanceof LocalTime) {
                LocalTime time = (LocalTime) obj;
                long millis = time.toNanoOfDay();
                bytes = Longs.toByteArray(millis);
            }
            return bytes;
        }

        @Override
        public LocalTime bytesToObject(byte[] bytes) {
            LocalTime time = null;
            long millis = 0;

            if (bytes != null && bytes.length > 0) {
                millis = Longs.fromByteArray(bytes);
                time = bytes.length > 0 ? LocalTime.ofNanoOfDay(millis) : null;
            }

            return time;
        }

        @Override
        public String bytesToString(byte[] bytes) {
            return bytesToObject(bytes).format(DateTimeFormatter.ofPattern("H:mm"));
        }
    },// end of single enumeration

    email("email", "black") {
        @Override
        public byte[] objectToBytes(Object obj) {
            byte[] bytes = new byte[0];
            if (obj instanceof String) {
                String stringa = (String) obj;
                bytes = stringa.getBytes(Charset.forName("UTF-8"));
            }
            return bytes;
        }


        @Override
        public String bytesToObject(byte[] bytes) {
            String obj = "";
            if (bytes != null) {
                obj = new String(bytes, Charset.forName("UTF-8"));
            }
            return obj;
        }

        @Override
        public String bytesToString(byte[] bytes) {
            return bytesToObject(bytes);
        }
    },// end of single enumeration

    enumeration("enum", "black") {
        @Override
        public byte[] objectToBytes(Object obj) {
            byte[] bytes = new byte[0];
            if (obj instanceof String) {
                String stringa = (String) obj;
                bytes = stringa.getBytes(Charset.forName("UTF-8"));
            }
            return bytes;
        }


        @Override
        public String bytesToObject(byte[] bytes) {
            String obj = "";
            if (bytes != null) {
                obj = new String(bytes, Charset.forName("UTF-8"));
            }
            return obj;
        }

        @Override
        public String bytesToString(byte[] bytes) {
            return bytesToObject(bytes).substring(bytesToObject(bytes).indexOf(PUNTO_VIRGOLA) + 1);
        }
    },// end of single enumeration

    image("image", "black") {
        //@todo RIMETTERE
        //        @Override
        //        public byte[] objectToBytes(Object obj) {
        //            byte[] bytes = new byte[0];
        //            if (obj instanceof Image) {
        //                Image image = (Image) obj;
        //                bytes = image.getBytes(Charset.forName("UTF-8"));
        //            }// end of if cycle
        //            return bytes;
        //        }// end of method

        //        @Override
        //        public Object bytesToObject(byte[] bytes) {
        //            Image img = null;
        //            if (bytes.length > 0) {
        //                img = LibImage.getImage(bytes);
        //            }
        //            return img;
        //        }// end of method
    },// end of single enumeration

    icona("icona", "black") {
        @Override
        public byte[] objectToBytes(Object obj) {
            byte[] bytes = new byte[0];
            if (obj instanceof String) {
                String stringa = (String) obj;
                bytes = stringa.getBytes(Charset.forName("UTF-8"));
            }
            return bytes;
        }


        @Override
        public String bytesToObject(byte[] bytes) {
            String obj = VUOTA;
            if (bytes != null) {
                obj = new String(bytes, Charset.forName("UTF-8"));
            }
            return obj.toUpperCase();
        }
    },// end of single enumeration

    ;

    //    resource("resource", EAFieldType.resource) {
    //        @todo RIMETTERE
    //
    //                @Override
    //        public Object bytesToObject(byte[] bytes) {
    //            Resource res = null;
    //            Image img = null;
    //            if (bytes.length > 0) {
    //                img = LibImage.getImage(bytes);
    //            }// end of if cycle
    //            if (img != null) {
    //                res = img.getSource();
    //            }// end of if cycle
    //            return res;
    //        }// end of method
    //    },// end of single enumeration

    //    decimal("decimale", AFieldType.lungo) {
    //        @Override
    //        public byte[] objectToBytes(Object obj) {
    //            byte[] bytes = new byte[0];
    //            if (obj instanceof BigDecimal) {
    //                BigDecimal bd = (BigDecimal) obj;
    //                bytes = LibByte.bigDecimalToByteArray(bd);
    //            }// end of if cycle
    //            return bytes;
    //        }// end of method
    //
    //        @Override
    //        public Object bytesToObject(byte[] bytes) {
    //            return LibByte.byteArrayToBigDecimal(bytes);
    //        }// end of method
    //    },// end of single enumeration

    //    bytes("blog", EAFieldType.json);

    //    private static ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
    private String nome;

    private String color;


    AETypePref(final String nome, final String color) {
        this.setNome(nome);
        this.color = color;
    }// fine del costruttore


    public static String[] getValues() {
        String[] valori;
        AETypePref[] types = values();
        valori = new String[values().length];

        for (int k = 0; k < types.length; k++) {
            valori[k] = types[k].toString();
        }// end of for cycle

        return valori;
    }// end of static method


    public static byte[] intToByteArray(int x) {
        return new byte[]{(byte) ((x >> 24) & 0xFF), (byte) ((x >> 16) & 0xFF), (byte) ((x >> 8) & 0xFF), (byte) (x & 0xFF)};
    }// end of static method


    public static int byteArrayToInt(byte[] bytes) {
        int num = 0;
        if ((bytes != null) && (bytes.length > 0)) {
            num = bytes[3] & 0xFF | (bytes[2] & 0xFF) << 8 | (bytes[1] & 0xFF) << 16 | (bytes[0] & 0xFF) << 24;
        }
        return num;
    }// end of static method


    public static byte[] longToByteArray(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(0, x);
        return buffer.array();
    }// end of static method


    public static long byteArrayToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();
        return buffer.getLong();
    }// end of static method

    public static List<AETypePref> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    /**
     * Converte un valore Object in ByteArray per questa preferenza.
     * Sovrascritto
     *
     * @param obj il valore Object
     *
     * @return il valore convertito in byte[]
     */
    public byte[] objectToBytes(Object obj) {
        return null;
    }// end of method

    /**
     * Converte un byte[] in Object del tipo adatto per questa preferenza.
     * Sovrascritto
     *
     * @param bytes il valore come byte[]
     *
     * @return il valore convertito nell'oggetto del tipo adeguato
     */
    public Object bytesToObject(byte[] bytes) {
        return null;
    }// end of method

    /**
     * Converte un byte[] in una stringa visibile nella UI.
     * Sovrascritto
     *
     * @param bytes il valore come byte[]
     *
     * @return il valore convertito in stringa
     */
    public String bytesToString(byte[] bytes) {
        return bytesToObject(bytes).toString();
    }

    /**
     * Writes a value in the storage for this type of preference
     * Sovrascritto
     *
     * @param value the value
     */
    public void put(Object value) {
    }// end of method

    /**
     * Retrieves the value of this preference's type
     * Sovrascritto
     */
    public Object get() {
        return null;
    }// end of method

    public String getNome() {
        return nome;
    }// end of getter method

    public void setNome(String nome) {
        this.nome = nome;
    }//end of setter method

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
    }// end of method

    public String getColor() {
        return color;
    }

    /**
     * Stringa di valori (text) da usare per memorizzare la preferenza <br>
     * La stringa è composta da tutti i valori separati da virgola <br>
     * Poi, separato da punto e virgola viene il valore selezionato di default <br>
     *
     * @return stringa di valori e valore di default
     */
    @Override
    public String getPref() {
        StringBuffer buffer = new StringBuffer();

        getAllEnums().forEach(enumeration -> buffer.append(enumeration.name() + VIRGOLA));

        buffer.delete(buffer.length() - 1, buffer.length());
        buffer.append(PUNTO_VIRGOLA);
        buffer.append(name());

        return buffer.toString();
    }
}// end of enumeration class
