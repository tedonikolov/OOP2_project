package entity;

public class Sectors {
    private int idSector;
    private String sectorType;

    public Sectors(int idSector, String sectorType) {
        this.idSector = idSector;
        this.sectorType = sectorType;
    }

    public int getIdSector() {
        return idSector;
    }

    public void setIdSector(int idSector) {
        this.idSector = idSector;
    }

    public String getSectorType() {
        return sectorType;
    }

    public void setSectorType(String sectorType) {
        this.sectorType = sectorType;
    }

}
