package innerclasses;

public class Parcel6 {
    private void internalTracking(boolean b) {
        if (b) {
            class TrackSlip {
                private String id;

                TrackSlip(String s) {
                    id = s;
                }

                String getSlip() {
                    return id;
                }
            }

            TrackSlip ts = new TrackSlip("slip");
            String s = ts.getSlip();
        }
    }

    public void track() {
        internalTracking(true);
    }

    public static void main(String[] args) {
        Parcel6 p = new Parcel6();
        p.track();
    }
}
