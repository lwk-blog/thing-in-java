package innerclasses;

class Parcel4 {
    private class PContents implements Contents {
        private int i = 11;
        @Override
        public int value() {
            return i;
        }
    }

    protected class PDestination implements Destination {
        private String label;
        PDestination (String whereTo) {
            label = whereTo;
        }

        @Override
        public String readLabel() {
            return label;
        }
    }

    public Destination destination(String whereTo) {
        return new PDestination(whereTo);
    }

    public Contents contents() {
        return new PContents();
    }
}
public class TestParcel {
    public static void main(String[] args) {
        Parcel4 p = new Parcel4();
        Destination d = p.destination("Shenzhen");
        Contents c = p.contents();
        Parcel4.PDestination dd = p.new PDestination("");
    }
}
