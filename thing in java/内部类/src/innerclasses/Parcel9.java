package innerclasses;

public class Parcel9 {
    public Destination destination(final String dest) {
        return new Destination() {
            private String label = dest;

            @Override
            public String readLabel() {
                return label;
            }
        };
    }
    public static void main(String[] args) {

    }
}
