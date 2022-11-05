package entity;

public class Sectors {
    String sectorType;
    double sectorPrice;

    public Sectors(String sectorType, double sectorPrice) {
        this.sectorType = sectorType;
        this.sectorPrice = sectorPrice;
    }

    public String getSectorType() {
        return sectorType;
    }

    public void setSectorType(String sectorType) {
        this.sectorType = sectorType;
    }

    public double getSectorPrice() {
        return sectorPrice;
    }

    public void setSectorPrice(double sectorPrice) {
        this.sectorPrice = sectorPrice;
    }
}
